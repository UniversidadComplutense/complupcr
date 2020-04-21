package es.ucm.pcr.servicios;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanPlacaVisavetUCM;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.BusquedaPlacasVisavetBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LotePlacaVisavetBean;
import es.ucm.pcr.beans.MenuBean;
import es.ucm.pcr.beans.MuestraBeanLaboratorioVisavet;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.beans.PaginadorBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.config.security.UserDetailsTestConfig;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.utilidades.Utilidades;

/**
 * Para estos tests, asumimos que los usuarios y roles están ya en la BD. Se
 * cargan el el script /src/test/resources/data.sql
 * 
 * @author pmarrasant
 *
 */
@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = UserDetailsTestConfig.class)
@AutoConfigureMockMvc
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

	@Autowired
	LaboratorioCentroServicio laboratorioCentroServicio;

	@Autowired
	private DocumentoServicio documentoServicio;
	
	@Autowired
	MockMvc mockMvc;

	static Integer placaVisavetId = 0;

	@Test
	@Order(1)
	@WithUserDetails("centrosalud@ucm.es")
	public void centroSalud() {
		try {
			// Primero testeamos temas de sesión
			Usuario user = sesionServicio.getUsuario();
			assertEquals("centrosalud@ucm.es", user.getEmail(), "No se recupera bien el usuario de la sesión.");
			String email = sesionServicio.getEmail();
			assertEquals("centrosalud@ucm.es", email, "No se recupera bien el email de la sesión.");
			List<String> roles = sesionServicio.getRoles();
			assertTrue(roles.contains("ROLE_CENTROSALUD"), "El usuario debe tener el rol CENTROSALUD.");
			assertTrue(sesionServicio.tieneRol("CENTROSALUD"), "El usuario debe tener el rol CENTROSALUD.");
			List<MenuBean> menu = sesionServicio.getMenu();
			assertTrue(menu != null && menu.size() > 0, "El menú del usuario está vacío.");

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
			// Primero testeamos temas de sesión
			Usuario user = sesionServicio.getUsuario();
			assertEquals("recepcionvisavet@ucm.es", user.getEmail(), "No se recupera bien el usuario de la sesión.");
			String email = sesionServicio.getEmail();
			assertEquals("recepcionvisavet@ucm.es", email, "No se recupera bien el email de la sesión.");
			List<String> roles = sesionServicio.getRoles();
			assertTrue(roles.contains("ROLE_RECEPCIONLABORATORIO"),
					"El usuario debe tener el rol RECEPCIONLABORATORIO.");
			assertTrue(sesionServicio.tieneRol("RECEPCIONLABORATORIO"),
					"El usuario debe tener el rol RECEPCIONLABORATORIO.");
			List<MenuBean> menu = sesionServicio.getMenu();
			assertTrue(menu != null && menu.size() > 0, "El menú del usuario está vacío.");

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
			paginaLotes = servicioLaboratorioVisavetUCM.buscarLotes(busquedaLotes,
					PageRequest.of(0, 10, Sort.by(Direction.ASC, "fechaEnvio")));

			// Vamos a comprobar que está el lote de la prueba anterior (lote1)
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

			// Y ahora lo recpcionamos
			LoteCentroBean beanLote = loteServicio.findById(miLbpv.getId());
			assertEquals("lote1", beanLote.getNumLote(), "El lote que se encuentra al recepcionar no es el lote1");

			beanLote.setFechaRecibido(new Date());
			BeanEstado estado = new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoLote);
			estado.setEstado(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS);
			loteServicio.actualizarEstadoLote(beanLote, estado);

			// Vamos a comprobar que se ha recepcionado
			LoteCentroBean beanLote2 = loteServicio.findById(miLbpv.getId());
			assertEquals(estado.getEstado().getCodNum(), beanLote2.getEstado().getEstado().getCodNum(),
					"El lote 1 no está en estado recepcionado.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para recepción Visavet");
		}
	}

	@Test
	@Order(3)
	@WithUserDetails("tecnicovisavet@ucm.es")
	public void tecnicoVisavetLotes() {
		try {
			// Primero testeamos temas de sesión
			Usuario user = sesionServicio.getUsuario();
			assertEquals("tecnicovisavet@ucm.es", user.getEmail(), "No se recupera bien el usuario de la sesión.");
			String email = sesionServicio.getEmail();
			assertEquals("tecnicovisavet@ucm.es", email, "No se recupera bien el email de la sesión.");
			List<String> roles = sesionServicio.getRoles();
			assertTrue(roles.contains("ROLE_TECNICOLABORATORIO"), "El usuario debe tener el rol TECNICOLABORATORIO.");
			assertTrue(sesionServicio.tieneRol("TECNICOLABORATORIO"),
					"El usuario debe tener el rol TECNICOLABORATORIO.");
			List<MenuBean> menu = sesionServicio.getMenu();
			assertTrue(menu != null && menu.size() > 0, "El menú del usuario está vacío.");

			// Recuperamos el laboratorioVisavet del usuario de la sesión
			LaboratorioVisavet labo = sesionServicio.getLaboratorioVisavet();
			assertEquals("DemoVisavet1", labo.getNombre(),
					"Hay problemas con la asignación de usuario al laboratorio visavet.");

			// Los test anteriores han creado el lote1 con 2 muestras en el centro de salud
			// TEST1,
			// lo han enviado al laboratorio visavet DemoVisavet1 y ha sido recepcionado en
			// el mismo.
			// Ahora toca localizarlo y procesarlo.
			BusquedaLotesBean busquedaLotes = new BusquedaLotesBean();
			busquedaLotes.setCodNumEstadoSeleccionado(4);
			Page<LoteBeanPlacaVisavet> paginaLotes = null;
			busquedaLotes.setMostrarProcesar(false);

			busquedaLotes.setListaBeanEstado(BeanEstado.estadosLoteLaboratorioVisavet());
			busquedaLotes.setListaCentros(centroServicio.listaCentrosOrdenada());
			busquedaLotes.setRolURL("T");
			paginaLotes = servicioLaboratorioVisavetUCM.buscarLotes(busquedaLotes,
					PageRequest.of(0, 10, Sort.by(Direction.ASC, "fechaEnvio")));

			// Vamos a comprobar que está el lote de la prueba anterior (lote1)
			boolean encontrado = false;
			LoteBeanPlacaVisavet miLbpv = null;
			for (LoteBeanPlacaVisavet lbpv : paginaLotes.getContent()) {
				if (lbpv.getNumLote().equals("lote1")) {
					encontrado = true;
					miLbpv = lbpv;
					break;
				}
			}
			assertTrue(encontrado, "No se encuentra el lote lote1 como recpcionado en el laboratorio.");

			// Volvemos a localizarlo, como lo hace el controlador buacarPlacasGet
			// (/laboratorioIni/ProcesarLotes GET)
			LotePlacaVisavetBean lotePlacaVisavetBean = new LotePlacaVisavetBean();
			// obtenemos los lotes con sus muestras

			String[] idsLotes = { miLbpv.getId().toString() };
			List<LoteBeanPlacaVisavet> listaLotes = new ArrayList<LoteBeanPlacaVisavet>();
			Integer numeroMuestras = 0;
			// LoteBeanPlacaVisavet lotePlaca;
			for (int i = 0; i < idsLotes.length; i++) {
				// cuando ya este el servicio BeanLote
				// lote=servicioLotes.obtenerLote(idsLotes[i]);
				// para probar
				if (idsLotes[i] != "") {
					LoteBeanPlacaVisavet lotePlaca = loteServicio.findByIdByPlacas(Integer.parseInt(idsLotes[i]));
					numeroMuestras += lotePlaca.getListaMuestras().size();
					listaLotes.add(lotePlaca);
				}
				// lotePlacaVisavetBean.setTotalMuestras(getBean(i).getListaMuestras().size()+lotePlacaVisavetBean.getTotalMuestras());
			}
			lotePlacaVisavetBean.setTotalMuestras(numeroMuestras);
			assertEquals(2, numeroMuestras, "Se esperaba que hubiera 2 muestras en el lote y hay " + numeroMuestras);
			// para probar
			List<Integer> tamanoLista = new ArrayList<Integer>();
			tamanoLista.add(20);
			tamanoLista.add(96);

			lotePlacaVisavetBean.setListaTamanosDisponibles(tamanoLista);

			BeanPlacaVisavetUCM placaVisavet = new BeanPlacaVisavetUCM();
			placaVisavet.setListaLotes(listaLotes);
			lotePlacaVisavetBean.setPlaca(placaVisavet);
			lotePlacaVisavetBean.setListaLotesDisponibles(listaLotes);
//			session.setAttribute("lotePlacaVisavetBean", lotePlacaVisavetBean);

			// Creamos una placa Visavet y le asignamos el lote.
			BeanPlacaVisavetUCM placaVisavet2 = new BeanPlacaVisavetUCM();
			BeanEstado estado = new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			estado.setEstado(Estado.PLACA_INICIADA);

			placaVisavet2.setEstado(estado);
			placaVisavet2 = servicioLaboratorioVisavetUCM.guardar(placaVisavet2);

			Integer idPlaca = placaVisavet2.getId();

			List<LoteBeanPlacaVisavet> listaLotesDisponibles = new ArrayList<LoteBeanPlacaVisavet>();
			for (LoteBeanPlacaVisavet lote : lotePlacaVisavetBean.getListaLotesDisponibles()) {

				lote.setIdPlacaVisavet(idPlaca);
				BeanEstado estado2 = new BeanEstado();
				estado2.setTipoEstado(TipoEstado.EstadoLote);
				estado2.setEstado(Estado.LOTE_PROCESADO_CENTRO_ANALISIS);
				lote.setEstado(estado2);
				listaLotesDisponibles.add(lote);

			}
			lotePlacaVisavetBean.setListaLotesDisponibles(listaLotesDisponibles);
			BeanPlacaVisavetUCM placaVisavet3 = new BeanPlacaVisavetUCM();
			placaVisavet3.setId(idPlaca);
			placaVisavet3.setListaLotes(lotePlacaVisavetBean.getListaLotesDisponibles());
			BeanEstado estado3 = new BeanEstado();
			estado3.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			estado3.setEstado(Estado.PLACAVISAVET_INICIADA);
			placaVisavet3.setEstado(estado);
			placaVisavet3.setFechaCreacion(new Date());
			placaVisavet3 = servicioLaboratorioVisavetUCM.guardarConLote(placaVisavet3);
			lotePlacaVisavetBean.setPlaca(placaVisavet3);

			// Y ahora asignamos la referencias internas y procesamos.
			BeanEstado estado4 = new BeanEstado();
			estado4.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			estado4.setEstado(Estado.PLACAVISAVET_FINALIZADA);

			BeanPlacaVisavetUCM placa = lotePlacaVisavetBean.getPlaca();

			// Asiggnamos las referencias internas en el bean
			for (MuestraBeanLaboratorioVisavet mu : placa.getListaLotes().get(0).getListaMuestras()) {
				if (mu.getEtiqueta().equals("Visavet1")) {
					mu.setReferenciaInterna("Interna1");
				}
				if (mu.getEtiqueta().equals("Visavet2")) {
					mu.setReferenciaInterna("Interna2");
				}
			}

			for (LoteBeanPlacaVisavet lote : lotePlacaVisavetBean.getPlaca().getListaLotes()) {
				placa.setId(lote.getIdPlacaVisavet());
				placa.setEstado(estado4);
				placa = servicioLaboratorioVisavetUCM.guardar(placa);

				// obtenemos las muestras y guardamos la referencia
				for (MuestraBeanLaboratorioVisavet m : lote.getListaMuestras()) {

					m = muestraServicio.guardarReferencia(m);
				}

			}

			// Vale ya hemos grabado la placa, el lote y las muestras. Comprobemos.
			Integer idPlaca2 = placa.getId();
			BeanPlacaVisavetUCM placa2 = servicioLaboratorioVisavetUCM.buscarPlacaById(idPlaca2);
			BeanEstado estadoPlaca = new BeanEstado();
			estadoPlaca.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			estadoPlaca.setEstado(Estado.PLACAVISAVET_FINALIZADA);
			assertEquals(estadoPlaca.getEstado().getCodNum(), placa2.getEstado().getEstado().getCodNum(),
					"La placa no está en estado Finalizada.");
			BeanEstado estadoLote = new BeanEstado();
			estadoLote.setTipoEstado(TipoEstado.EstadoLote);
			estadoLote.setEstado(Estado.LOTE_PROCESADO_CENTRO_ANALISIS);
			assertEquals(1, placa2.getListaLotes().size(),
					"Debería haber un lote en la placa y hay " + placa2.getListaLotes().size());
			LoteBeanPlacaVisavet lbpv = placa.getListaLotes().get(0);
			assertEquals(estadoLote.getEstado().getCodNum(), lbpv.getEstado().getEstado().getCodNum(),
					"El lote no está en estado procesado.");
			assertEquals(2, placa2.getListaMuestras().size(),
					"Debería haber 2 muestras en la placa y hay " + placa2.getListaMuestras().size());
			Integer comprobaciones = 0;
			for (MuestraBeanLaboratorioVisavet mu : placa2.getListaMuestras()) {
				if (mu.getEtiqueta().equals("Visavet1")) {
					comprobaciones++;
					assertEquals("Interna1", mu.getReferenciaInterna(),
							"La muestra Visavet1 no tiene bien asignada la referencia interna.");
				}
				if (mu.getEtiqueta().equals("Visavet2")) {
					comprobaciones++;
					assertEquals("Interna2", mu.getReferenciaInterna(),
							"La muestra Visavet2 no tiene bien asignada la referencia interna.");
				}
			}
			assertEquals(2, comprobaciones,
					"Ha habido algún problema al localizar las muestras para comprobar su referencia interna.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para técnico Visavet Lotes");
		}
	}

	@Test
	@Order(4)
	@WithUserDetails("tecnicovisavet@ucm.es")
	public void tecnicoVisavetPlacas() {
		try {
			// En primer lugar vamos a buscar nuestra placa finalizada del test anterior
			BeanEstado estadoPlaca = new BeanEstado();
			estadoPlaca.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			estadoPlaca.setEstado(Estado.PLACAVISAVET_FINALIZADA);
			BusquedaPlacasVisavetBean busquedaPlacasVisavetBean = new BusquedaPlacasVisavetBean();
			busquedaPlacasVisavetBean.setCodNumEstadoSeleccionado(estadoPlaca.getEstado().getCodNum());

			Page<BeanPlacaVisavetUCM> pagina = null;
			busquedaPlacasVisavetBean.setListaBeanEstado(BeanEstado.estadosPlacaVisavet());
			busquedaPlacasVisavetBean
					.setListaLaboratorioCentro(laboratorioCentroServicio.listaLaboratoriosCentroOrdenada());
			busquedaPlacasVisavetBean.setIdLaboratorioVisavet(sesionServicio.getLaboratorioVisavet().getId());
			pagina = servicioLaboratorioVisavetUCM.buscarPlacas(busquedaPlacasVisavetBean,
					PageRequest.of(0, Utilidades.NUMERO_PAGINACION, Sort.by(Direction.ASC, "fechaCreacion")));
			PaginadorBean paginadorBean = new PaginadorBean(pagina.getTotalPages(), 1, 10L,
					"/laboratorioUni/buscarPlacas");
			List<BeanPlacaVisavetUCM> placas = pagina.getContent();
			assertEquals(1, placas.size(), "Debería haber una placa finalizada para asignar a laboratorio centro.");
			BeanPlacaVisavetUCM placa = placas.get(0);

			// Después le vamos a adjuntar un documento
			ElementoDocumentacionBean elementoDoc = documentoServicio.obtenerDocumentosPlacaVisavet(placa.getId());
			assertEquals(0, elementoDoc.getDocumentos().size(), "La placa Visavet debería tener 0 documentos.");
			elementoDoc.setTipoElemento(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_VISAVET);
			elementoDoc.setId(placa.getId());
			File file = new File("src/test/resources/logo_ucm.png");
			FileInputStream input = new FileInputStream(file);
			MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "image/png",
					IOUtils.toByteArray(input));
			elementoDoc.setFile(multipartFile);
			documentoServicio.guardar(elementoDoc);

			ElementoDocumentacionBean elementoDoc2 = documentoServicio.obtenerDocumentosPlacaVisavet(placa.getId());
			assertEquals(1, elementoDoc2.getDocumentos().size(),
					"La  placa sólo debería tener un documento exactamente.");
			assertEquals("logo_ucm.png", elementoDoc2.getDocumentos().get(0).getNombreDocumento(),
					"No se ha guardado bien el documento.");

			// Después le vamos a asignar un laboratorio de centro
			LaboratorioCentro laboCentro = laboratorioCentroServicio.findById(1).get();
			BeanPlacaVisavetUCM placa2 = servicioLaboratorioVisavetUCM.buscarPlacaById(placa.getId());
			BeanEstado estado = new BeanEstado();
			estado.setEstado(Estado.PLACAVISAVET_ASIGNADA);
			estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			placa2.setEstado(estado);
			placa2.setFechaAsignadaLaboratorio(new Date());

			BeanPlacaVisavetUCM placab = servicioLaboratorioVisavetUCM.guardarPlacaConLaboratorio(placa2,
					laboCentro.getId());

			// Y finalmente la vamos a enviar
			BeanEstado estadoEnv = new BeanEstado();
			estadoEnv.setEstado(Estado.PLACAVISAVET_ENVIADA);
			estadoEnv.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			placab.setEstado(estadoEnv);
			placab.setFechaEnviadaLaboratorio(new Date());
			BeanPlacaVisavetUCM placac = servicioLaboratorioVisavetUCM.guardar(placab);
			
			//Comprobamos
			assertEquals(estadoEnv.getEstado().getCodNum(), placac.getEstado().getEstado().getCodNum(), "La placa no está enviada");
			assertEquals(1,placac.getIdLaboratorioCentro(), "La placa no está asignada al laboratorio centro correcto.");
			ElementoDocumentacionBean eleDoc2 = documentoServicio.obtenerDocumentosPlacaVisavet(placac.getId());
			assertEquals(1, eleDoc2.getDocumentos().size(),"Debería haber un documento exactamente asociado a la placa.");
			
			//Los pasamos al test siguiente.
			CicloMuestrasServiciosTests.placaVisavetId = placac.getId();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para técnico Visavet Placas");
		}
	}
	
	@Test
	@Order(5)
	@WithUserDetails("responsablepcr@ucm.es")
	public void responsablePCRRecepcionPlacas() {
		try {
			//Entramos en la pantalla de recepción de placas Visavet en Centro PCR
			//Tenemos que tener el id de la placa y las 2 referencias internas de nuestras
			//muestras en el contenido de la respuesta
			this.mockMvc.perform(get("http://localhost/laboratorioCentro/recepcionPlacas/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("<td><span>"+CicloMuestrasServiciosTests.placaVisavetId+"</span></td>")))
				.andExpect(content().string(containsString("Interna1")))
				.andExpect(content().string(containsString("Interna2")));
			//Ahora vamos a recepcionarla
			this.mockMvc.perform(get("http://localhost/laboratorioCentro/recepcionPlacas/recepcionar?id="+CicloMuestrasServiciosTests.placaVisavetId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("<input type=\"text\" class=\"form-control form-control-sm\" value=\""+CicloMuestrasServiciosTests.placaVisavetId+"\"/>")));
			MockHttpServletRequestBuilder recepcionar = post("http://localhost/laboratorioCentro/recepcionPlacas/recepcionar")
						.param("id",CicloMuestrasServiciosTests.placaVisavetId.toString());
			this.mockMvc.perform(recepcionar)
				.andDo(print())
				.andExpect(status().isOk())  // TODO: MAL. No se aplica patrón PCR en este controlador POST. Corregir controlador y test
				.andExpect(content().string(containsString("<span>La placa "+CicloMuestrasServiciosTests.placaVisavetId+" se ha recepcionado correctamente.</span>")));
			
			//Vamos a recuperar la placa para comprobar que está recepcionada
			PlacaLaboratorioVisavetBean placa = laboratorioVisavetServicio.buscarPlaca(CicloMuestrasServiciosTests.placaVisavetId);
			assertTrue(placa.getFechaRecepcion() != null, "La placa no tiene fecha de recepción en laboratorio centro y debería.");
			BeanEstado estadoPlaca = new BeanEstado();
			estadoPlaca.setEstado(Estado.PLACAVISAVET_RECIBIDA);
			estadoPlaca.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			assertEquals(estadoPlaca.getEstado().getCodNum(), placa.getBeanEstado().getEstado().getCodNum(),"La placa no está en estado recido y debería.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para responsable PCR recepción placas");
		}
	}
}
