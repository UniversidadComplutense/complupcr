package es.ucm.pcr.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.config.UserDetailsTestConfig;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;

@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = UserDetailsTestConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class CentroSaludServiciosTests {

	@Autowired
	CentroServicio centroServicio;

	@Autowired
	MuestraServicio muestraServicio;

	@Autowired
	LoteServicio loteServicio;

	@Autowired
	LaboratorioVisavetServicio laboratorioVisavetServicio;
	
	@Autowired
	RolServicio rolServicio;

	@Autowired
	UsuarioServicio usuarioServicio;
	
	@Test
	@Order(10)
	@WithUserDetails("centrosalud@ucm.es")
	public void altaMuestras() {
		try {
			// Damos de alta centro de salud
			Centro centro = new Centro();
			centro.setCodCentro("TEST1");
			centro.setNombre("Centro de test1");
			centro.setTelefono("123456789");
			centro.setDireccion("Dirección test1");
			centro = centroServicio.save(centro);

			// Damos de alta el usuario del centro asociado
			Optional<Rol> orolUsu = rolServicio.findByNombre("CENTROSALUD");
			Rol rolUsu = null;
			if (orolUsu.isEmpty()) {
				rolUsu = new Rol();
				rolUsu.setNombre("CENTROSALUD");
				rolUsu = rolServicio.save(rolUsu);
			} else {
				rolUsu = orolUsu.get();
			}
			
			Usuario usuario = new Usuario();
			usuario.setNombre("Centro");
			usuario.setApellido1("Salud");
			usuario.setEmail("centrosalud@ucm.es");
			usuario.setPassword("PWD");
			usuario.setHabilitado("A");
			
			Set<Rol> roles = new HashSet<Rol>();
			roles.add(rolUsu);
			usuario.setRols(roles);
			usuario.setCentro(centro);
			usuarioServicio.save(usuario);
			
			
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
			lcb.setCapacidad(1);
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

			// Vamos a asignarlo a un laboratorio Visavet.
			// Para ello primero lo creamos
			LaboratorioVisavet labVisa = new LaboratorioVisavet();
			labVisa.setNombre("Visavet1");
			labVisa.setCapacidad(100);
			labVisa = laboratorioVisavetServicio.save(labVisa);

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
			fail("Falló la prueba de alta de muestras");
		}
	}

}
