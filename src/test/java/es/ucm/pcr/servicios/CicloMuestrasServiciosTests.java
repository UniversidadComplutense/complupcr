package es.ucm.pcr.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.config.security.UserDetailsTestConfig;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;

/**
 * Para estos tests, asumimos que los usuarios y roles están ya en la BD. Se
 * cargan el el script /src/test/resources/dats.sql
 * 
 * @author pmarrasant
 *
 */
@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = UserDetailsTestConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class CicloMuestrasServiciosTests {

	@Autowired
	CentroServicio centroServicio;

	@Autowired
	MuestraServicio muestraServicio;

	@Autowired
	LoteServicio loteServicio;

	@Autowired
	ServicioLaboratorioVisavetUCM servicioLaboratorioVisavetUCM;
	
	@Autowired
	LaboratorioVisavetServicio laboratorioVisavetServicio;

	@Autowired
	RolServicio rolServicio;

	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	SesionServicio sesionServicio;

	@Test
	@Order(1)
	@WithUserDetails("centrosalud@ucm.es")
	public void centroSalud() {
		try {

			// Recuperamos le centro de salud del usuario de la sesión
			Centro centro = sesionServicio.getCentro();
			assertEquals("TEST1", centro.getCodCentro(),
					"Hay problemas con la asignación de usuario al centro de salud.");

			// Damos de alta muestras
			MuestraCentroBean mcb = new MuestraCentroBean();
			mcb.setCentro(centro.getId());
			mcb.setCorreo("prueba1@ucm.es");
			mcb.setEtiqueta("Visavet1");
			mcb.setFechaEntrada(new Date());
			mcb.setNhc("nhc1");
			mcb.setNombre("Prueba1Nombre");
			mcb.setPrimerApellido("Prueba1PrimerApellido");
			mcb.setTelefono("123456789");
			mcb.setTipoMuestra("N");
			mcb = muestraServicio.guardar(mcb);

			mcb = new MuestraCentroBean();
			mcb.setCentro(centro.getId());
			mcb.setCorreo("prueba2@ucm.es");
			mcb.setEtiqueta("Visavet2");
			mcb.setFechaEntrada(new Date());
			mcb.setNhc("nhc2");
			mcb.setNombre("Prueba2Nombre");
			mcb.setPrimerApellido("Prueba2PrimerApellido");
			mcb.setTelefono("123456788");
			mcb.setTipoMuestra("N");
			mcb = muestraServicio.guardar(mcb);

			// Recuperamos la muestra 1
			MuestraBusquedaBean mbb = new MuestraBusquedaBean();
			mbb.setEtiquetaMuestra("Visavet1");
			List<MuestraListadoBean> lista1 = muestraServicio.findMuestraByParam(mbb);
			MuestraListadoBean mu1 = lista1.get(0);
			assertEquals("prueba1@ucm.es", mu1.getEmailPaciente());

			// Recuperamos la muestra 2
			MuestraBusquedaBean mbb2 = new MuestraBusquedaBean();
			mbb2.setEtiquetaMuestra("Visavet2");
			List<MuestraListadoBean> lista2 = muestraServicio.findMuestraByParam(mbb2);
			MuestraListadoBean mu2 = lista2.get(0);
			assertEquals("prueba2@ucm.es", mu2.getEmailPaciente());

			// Creamos un lote
			LoteCentroBean lcb = new LoteCentroBean();
			BeanEstado estadoLote = new BeanEstado();
			estadoLote.setTipoEstado(TipoEstado.EstadoLote);
			estadoLote.setEstado(Estado.LOTE_INICIADO);
			lcb.setEstado(estadoLote);
			lcb.setCapacidad(5);
			lcb.setIdCentro(centro.getId());
			lcb.setNumeroMuestras(0);
			lcb.setNumLote("lote1");
			lcb.setTieneMuestras(false);
			lcb = loteServicio.guardar(lcb);

			// Asignamos las muestras al lote.
			MuestraBusquedaBean mbbCentro = new MuestraBusquedaBean();
			mbbCentro.setIdCentro(centro.getId());
			List<MuestraListadoBean> mlbCentro = muestraServicio.findMuestraByParam(mbbCentro);
			// Debe haber 2
			assertEquals(2, mlbCentro.size(), "Debería haber 2 muestras en el centro y hay " + mlbCentro.size());
			for (MuestraListadoBean mlb : mlbCentro) {
				MuestraCentroBean mu = muestraServicio.findById(mlb.getId());
				mu.setIdLote(lcb.getId());
				mu = muestraServicio.guardar(mu);
			}

			// Repetimos la búsqueda y comprobamos el lote
			mlbCentro = muestraServicio.findMuestraByParam(mbbCentro);
			for (MuestraListadoBean mlb : mlbCentro) {
				MuestraCentroBean mu = muestraServicio.findById(mlb.getId());
				assertEquals(lcb.getId(), mu.getIdLote(), "La muestra " + mu.getId() + " no está asignada al lote.");
			}

			// Vamos a asignarlo al laboratorio Visavet, que ya está en la BD
			LaboratorioVisavet labVisa = laboratorioVisavetServicio.findByNombre("DemoVisavet1").get();

			Integer idLote = lcb.getId();
			lcb = new LoteCentroBean();
			lcb = loteServicio.findById(idLote);
			lcb.setIdLaboratorio(labVisa.getId());
			lcb = loteServicio.guardar(lcb);

			// Comprobamos que ha cambiado el estado
			assertEquals(lcb.getEstado().getEstado().getCodNum(), Estado.LOTE_ASIGNADO_CENTRO_ANALISIS.getCodNum(),
					"El lote debería estar asignado a centro análisis y no es así");

			// Enviamos el lote
			BeanEstado beanEstado = new BeanEstado();
			beanEstado.asignarTipoEstadoYCodNum(TipoEstado.EstadoLote, Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum());
			loteServicio.actualizarEstadoLote(lcb, beanEstado);

			// Recuperamos el lote para comprobar el estado enviado
			Integer id = lcb.getId();
			lcb = loteServicio.findById(id);
			assertEquals(lcb.getEstado().getEstado().getCodNum(), Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para centro de salud");
		}
	}

	@Test
	@Order(2)
	@WithUserDetails("recepcionvisavet@ucm.es")
	public void recepcionVisavet() {
		try {

			// Recuperamos el laboratorioVisavet del usuario de la sesión
			LaboratorioVisavet labo = sesionServicio.getLaboratorioVisavet();
			assertEquals("DemoVisavet1", labo.getNombre(),
					"Hay problemas con la asignación de usuario al laboratorio visavet.");

			// Buscamos los lotes asignados al laboratorio pendientes de recepcionar.
			BusquedaLotesBean busquedaLotes = new BusquedaLotesBean();
			busquedaLotes.setCodNumEstadoSeleccionado(3);
			Page<LoteBeanPlacaVisavet> paginaLotes = null;
			busquedaLotes.setMostrarProcesar(false);

			busquedaLotes.setListaBeanEstado(BeanEstado.estadosLoteLaboratorioVisavet());
			busquedaLotes.setListaCentros(centroServicio.listaCentrosOrdenada());
			busquedaLotes.setRolURL("R");
			paginaLotes = servicioLaboratorioVisavetUCM.buscarLotes(busquedaLotes, PageRequest.of(0,
					10, Sort.by(Direction.ASC, "fechaEnvio")));
			
			//Vamos a comprobar que está el lote de la prueba anterior (lote1)
			boolean encontrado = false;
			LoteBeanPlacaVisavet miLbpv = null;
			for (LoteBeanPlacaVisavet lbpv : paginaLotes.getContent()) {
				if (lbpv.getNumLote().equals("lote1")) {
					encontrado = true;
					miLbpv = lbpv;
					break;
				}
			}
			assertTrue(encontrado, "No se encuentra el lote lote1 como enviado al laboratorio.");
			
			//Y ahora lo recpcionamos
			LoteCentroBean beanLote = loteServicio.findById(miLbpv.getId());
			assertEquals("lote1", beanLote.getNumLote(),"El lote que se encuentra al recepcionar no es el lote1");
			
			beanLote.setFechaRecibido(new Date());
			BeanEstado estado= new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoLote);
			estado.setEstado(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS);
			loteServicio.actualizarEstadoLote(beanLote, estado);
			
			//Vamos a comprobar que se ha recepcionado
			LoteCentroBean beanLote2 = loteServicio.findById(miLbpv.getId());
			assertEquals(estado.getEstado().getCodNum(), beanLote2.getEstado().getEstado().getCodNum(),"El lote 1 no está en estado recepcionado.");			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para recepción Visavet");
		}
	}

	@Test
	@Order(3)
	@WithUserDetails("tecnicovisavet@ucm.es")
	public void tecnicoVisavet() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para técnico Visavet");
		}
	}

}
