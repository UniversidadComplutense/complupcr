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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.TestSecurityContextHolder;
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
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
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
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.config.security.UserDetailsTestConfig;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.MuestraRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;
import es.ucm.pcr.servicios.ServicioLaboratorioVisavetUCMImpl.AnalisisExcelMuestras;
import es.ucm.pcr.utilidades.Utilidades;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.mock.web.MockMultipartFile;



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
public class CicloMuestrasServiciosConExcelTests {

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
	ServicioLaboratorioVisavetUCM servicioVisavet;

	@Autowired
	DocumentoServicio documentoServicio;
	
	@Autowired
	MuestraRepositorio muestraRepositorio;

	static Integer placaVisavetId = 0;
	static Integer placaPCRId = 0;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;


	private MultipartFile getMultipart(String filepath, String nombre, String contentType) {

		byte[] content = null;
		try {
			Path path = Paths.get(filepath);
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile result = new MockMultipartFile(nombre,
				nombre, contentType, content);
		return result;
	}


	/**
	 * Testeo de usuario del centro de salud. Creamos lote asignado a un labo
	 * visavet, creamos 2 muestras, enviamos el lote al laboratorio
	 */
	@Test
	@Order(1)
	@WithUserDetails("centrosalud@ucm.es")
	public void centroSalud() {
		try {
			List<Usuario> usuarios = usuarioRepositorio.findAll();
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


		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para centro de salud");
		}
	}

	/**
	 * Test para la recepción en laboratorio visavet. Se recepciona el lote del test
	 * anterior.
	 */
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

			ElementoDocumentacionBean edb=new ElementoDocumentacionBean();
			edb.setColumnaCliente("Cliente");
			edb.setColumnaRemitente("Remitente");
			edb.setColumnaPlaca("Nº placa");
			edb.setColumnaLote("Nº entrada");
			edb.setColumnaRef("Ref. muestra");
			edb.setColumna("Ref. externa");
			edb.setColumnaTipoMuestra("Tipo muestra");
			edb.setFile(getMultipart("src/test/resources/demodata/muestraA.xls",
					"muestraA.xls","application/vnd.ms-excel"));
			try {
				AnalisisExcelMuestras resultado=servicioVisavet.verificarExcel(edb,10);			
				//servicioVisavet.procesarExcel(edb); 
			} catch (Exception malaimportacion) {
				// No se había definido la hoja
				edb.setHoja("yoquese");
				try {					
					AnalisisExcelMuestras resultado=servicioVisavet.verificarExcel(edb,10);
				} catch (Exception ex2) {
					// la hoja era incorrecta y el mensaje muestra las hojas disponibles
					System.out.println("El mensaje es "+ex2.getMessage());
					edb.setHoja("Registro total de muestras");
					AnalisisExcelMuestras resultado=servicioVisavet.verificarExcel(edb,10);
					System.out.println(resultado.getFilasIncompletas());
					assertTrue(resultado.getMuestraYRefVisavet().size()==27, "debería haber 27 muestras correctamente importadas y hay "+
							resultado.getMuestraYRefVisavet().size());
					
					resultado=servicioVisavet.verificarExcel(edb,96);
					assertTrue(resultado.getMuestraYRefVisavet().size()==79, "debería haber 79 muestras correctamente importadas y hay "+
							resultado.getMuestraYRefVisavet().size());
					
					assertTrue(resultado.getLotesMuestras().size()==3, "debería haber 3 lotes y hay "+
							resultado.getLotesMuestras().size());
					assertTrue(new HashSet<String>(resultado.getMuestraCliente().values()).size()==2, "debería haber 2 clientes y hay "+
							new HashSet<String>(resultado.getMuestraCliente().values()).size());
					assertTrue(new HashSet<String>(resultado.getLoteRemitente().values()).size()==1, "debería haber 1 remitente y hay "+
							new HashSet<String>(resultado.getLoteRemitente().values()).size());
					assertTrue(resultado.getFilasIncompletas().size()==21, "debería haber 21 filas mal importadas y hay "+
							resultado.getFilasIncompletas().size());
					System.out.println("Filas incompletas identificadas en excel:");
					for (String fila:resultado.getFilasIncompletas()) {
						System.out.println(fila);
					}
					// incorpora todas las entradas como si hubieran sido enviadas por
					// un centro de salud = remitente de cada placa
					servicioVisavet.procesarExcel(edb,labo.getId(),96);	

					BusquedaPlacasVisavetBean busqueda=new BusquedaPlacasVisavetBean();
					busqueda.setIdLaboratorioVisavet(labo.getId());
					Page<BeanPlacaVisavetUCM> resultadoPlacas = servicioVisavet.buscarPlacas(busqueda, 
							PageRequest.of(0, Utilidades.NUMERO_PAGINACION));					
					assertTrue(resultadoPlacas.getContent().size()==3, "Debería haber 3 placas y hay "+resultadoPlacas.getContent().size());
					
					BusquedaLotesBean busquedaLotes=new BusquedaLotesBean();
					busquedaLotes.setIdLaboratorio(""+labo.getId());
					Page<LoteBeanPlacaVisavet> resultadoLotes = servicioVisavet.buscarLotes(busquedaLotes,  
							PageRequest.of(0, Utilidades.NUMERO_PAGINACION));					
					assertTrue(resultadoLotes.getContent().size()==3, "Debería haber 3 lotes y hay "+resultadoLotes.getContent().size());
					
					busquedaLotes.setNumLote("20200323-4");
					resultadoLotes = servicioVisavet.buscarLotes(busquedaLotes,  
							PageRequest.of(0, Utilidades.NUMERO_PAGINACION));	
					assertTrue(resultadoLotes.getContent().size()==1, "Debería haber 1 lote con id 20200323-4 y hay "+resultadoLotes.getContent().size());
					
					assertTrue(resultadoLotes.getContent().get(0).getListaMuestras().size()==18, 
							"Debería haber 18 muestras en lote con id 20200323-4 y hay "+resultadoLotes.getContent().get(0).getListaMuestras().size());
					
					busquedaLotes.setNumLote("20200327-1");
					resultadoLotes = servicioVisavet.buscarLotes(busquedaLotes,  
							PageRequest.of(0, Utilidades.NUMERO_PAGINACION));	
					assertTrue(resultadoLotes.getContent().size()==1, "Debería haber 1 lote con id 20200327-1 y hay "+resultadoLotes.getContent().size());
					
					Optional<Muestra> muestra = muestraRepositorio.findByEtiqueta("20081900");
					MuestraBusquedaBean params=new MuestraBusquedaBean();		
					List<MuestraListadoBean> muestras = muestraServicio.findMuestraByParam(params);
					

					verificaMuestra(muestras, "20081900", "V20/00067");
					verificaMuestra(muestras, "204360", "V20/00004");
					verificaMuestra(muestras, "206116", "V20/00025");
										
					
					// no se duplican entradas 
					resultado=servicioVisavet.verificarExcel(edb,96);
					assertTrue(resultado.getMuestraYRefVisavet().size()==0, "debería haber 0 muestras correctas  y hay "+
							resultado.getMuestraYRefVisavet().size());
					
					assertTrue(resultado.getFilasIncompletas().size()==98, "debería haber 98 muestras incorrectas y hay "+
							resultado.getFilasIncompletas().size());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba para recepción Visavet");
		}
	}


	private void verificaMuestra(List<MuestraListadoBean> muestras, String verificaEtiquetaMuestra,
			String verificaRefMuestra) {
		MuestraBusquedaBean params;
		params=new MuestraBusquedaBean();
		params.setEtiquetaMuestra(verificaEtiquetaMuestra);
		muestras = muestraServicio.findMuestraByParam(params);
		assertTrue(muestras.size()==1, 
				"Debería haber 1 muestra de etiqueta "+verificaEtiquetaMuestra+" y hay "+muestras.size());
		assertTrue(muestras.get(0).getRefInternaMuestra().equals(verificaRefMuestra), 
				"Debería haber 1 muestra de etiqueta "+verificaEtiquetaMuestra+" y ref interna "+
		verificaRefMuestra+" y el valor de la referencia es "+muestras.get(0).getRefInternaMuestra());
		params=new MuestraBusquedaBean();
		params.setRefInternaMuestra(verificaRefMuestra);
		muestras = muestraServicio.findMuestraByParam(params);
		
		assertTrue(muestras.size()==1, 
				"Debería haber 1 muestra de etiqueta "+verificaEtiquetaMuestra+" y hay "+
		muestras.size());
		assertTrue(muestras.get(0).getRefInternaMuestra().equals(verificaRefMuestra), 
				"Debería haber 1 muestra de etiqueta "+verificaEtiquetaMuestra+" y ref interna "+
		verificaRefMuestra+" y el valor de la referencia es "+muestras.get(0).getRefInternaMuestra());
	}

}
