package es.ucm.pcr.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
import org.springframework.test.context.ActiveProfiles;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.beans.BeanUsuarioGestion;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;

@ActiveProfiles(profiles = "test")
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GestorCrudServiciosTests {

	@Autowired
	CentroServicio centroServicio;

	@Autowired
	LaboratorioVisavetServicio laboratorioVisavetServicio;

	@Autowired
	LaboratorioCentroServicio laboratorioCentroServicio;

	@Autowired
	LoteServicio loteServicio;

	@Autowired
	RolServicio rolServicio;

	@Autowired
	UsuarioServicio usuarioServicio;

	@Test
	@Order(2)
	public void inicializacionBasicaRoles() throws Exception {
		try {
			// Los estado de lote, placa, muestra, placalaboratorio y placa visavet se
			// precargan desde src/test/resources/data.sql

			// Damos de alta los roles
			Rol rol = new Rol();
			rol.setNombre("ADMIN");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("GESTOR");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("CENTROSALUD");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("RECEPCIONLABORATORIO");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("TECNICOLABORATORIO");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("RESPONSANBLEPCR");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("JEFESERVICIO");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("ANALISTALABORATORIO");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("AUDITOR");
			rolServicio.save(rol);
			rol = new Rol();
			rol.setNombre("VOLUNTARIO");
			rolServicio.save(rol);

			rol = new Rol();
			rol.setNombre("PRUEBAPARABORRAR");
			rolServicio.save(rol);
			rolServicio.deleteById(rol.getId());

			List<BeanRol> listaRoles = rolServicio.generarListaRoles();
			assertEquals(listaRoles.size(), 10, "Debería haber 10 roles y no es así.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de roles.");
		}
	}

	@Test
	@Order(3)
	public void inicializacionBasicaCentrosSalud() throws Exception {
		try {
			// Damos de alta centros
			Centro centro = new Centro();
			centro.setCodCentro("BOT");
			centro.setNombre("Jardín Botánico");
			centro.setTelefono("123454321");
			centro.setEmail("botanicPrueba@ucm.es");
			centro.setDireccion("Av. Complutense S/N");
			centroServicio.save(centro);
			centro = new Centro();
			centro.setCodCentro("saludgetafe");
			centro.setNombre("Salud Getafe");
			centro.setTelefono("91111111");
			centro.setEmail("saludgetafe@mad.org");
			centro.setDireccion("unaporejemplo");
			centroServicio.save(centro);
			centro = new Centro();
			centro.setCodCentro("DEMO");
			centro.setNombre("Centro de demostración");
			centro.setTelefono("111222333");
			centro.setEmail("centroDemo@ucm.es");
			centro.setDireccion("Dirección demo");
			centroServicio.save(centro);

			centro = new Centro();
			centro.setCodCentro("BORRAR");
			centro.setNombre("Centro para borrar");
			centro.setTelefono("111111111");
			centro.setEmail("borrar@ucm.es");
			centro.setDireccion("Hay que borrarlo");
			centro = centroServicio.save(centro);

			centroServicio.deleteById(centro.getId());

			List<BeanCentro> listaCentros = centroServicio.listaCentrosOrdenada();
			assertEquals(listaCentros.size(), 3, "Debería haber 3 centros de salud y no es así.");
		} catch (

		Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de centros de salud.");
		}
	}

	@Test
	@Order(4)
	public void inicializacionBasicaLaboratoriosVisavet() throws Exception {
		try {
			// Damos de alta laboratorios Visavet
			LaboratorioVisavet labVisa = new LaboratorioVisavet();
			labVisa.setNombre("Visavet");
			labVisa.setCapacidad(100);
			laboratorioVisavetServicio.save(labVisa);
			labVisa = new LaboratorioVisavet();
			labVisa.setNombre("Biología Recepción");
			labVisa.setCapacidad(50);
			laboratorioVisavetServicio.save(labVisa);

			labVisa = new LaboratorioVisavet();
			labVisa.setNombre("Borrar");
			labVisa.setCapacidad(20);
			labVisa = laboratorioVisavetServicio.save(labVisa);

			laboratorioVisavetServicio.deleteById(labVisa.getId());

			List<BeanLaboratorioVisavet> listaLabsVisavet = laboratorioVisavetServicio
					.listaLaboratoriosVisavetOrdenada();
			assertEquals(listaLabsVisavet.size(), 2, "Debería haber 2 laboratorios visavet y no es así.");
		} catch (

		Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de laboratorios visavet.");
		}
	}

	@Test
	@Order(5)
	public void inicializacionBasicaLaboratoriosCentro() throws Exception {
		try {
			// Damos de alta laboratorios Visavet
			LaboratorioCentro labCentro = new LaboratorioCentro();
			labCentro.setNombre("VisavetPCR");
			laboratorioCentroServicio.save(labCentro);
			labCentro = new LaboratorioCentro();
			labCentro.setNombre("Biología PCR");
			laboratorioCentroServicio.save(labCentro);

			labCentro = new LaboratorioCentro();
			labCentro.setNombre("Borrar");
			labCentro = laboratorioCentroServicio.save(labCentro);

			laboratorioCentroServicio.deleteById(labCentro.getId());

			List<BeanLaboratorioVisavet> listaLabsVisavet = laboratorioVisavetServicio
					.listaLaboratoriosVisavetOrdenada();
			assertEquals(listaLabsVisavet.size(), 2, "Debería haber 2 laboratorios centro y no es así.");
		} catch (

		Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de laboratorios centro.");
		}
	}

	@Test
	@Order(6)
	public void inicializacionBasicaUsuarios() throws Exception {
		try {
			// Damos de alta usuario admin de centro de salud BOT con rol ADMIN
			Usuario usuario = new Usuario();
			usuario.setNombre("NombreAdmin1");
			usuario.setApellido1("Apell1Admin1");
			usuario.setEmail("admin1@ucm.es");
			usuario.setPassword("PWD");
			usuario.setHabilitado("A");
			Optional<Rol> rol = rolServicio.findByNombre("ADMIN");
			if (rol.isEmpty()) {
				fail("No se encuentra el rol ADMIN");
			}
			Set<Rol> roles = new HashSet<Rol>();
			roles.add(rol.get());
			usuario.setRols(roles);
			Optional<Centro> centro = centroServicio.findByCodCentro("BOT");
			if (centro.isEmpty()) {
				fail("No se encuentra el centro BOT");
			}
			usuario.setCentro(centro.get());
			usuarioServicio.save(usuario);
			
			//Comprobamos usuario admin1
			Usuario usu1 = usuarioServicio.findByEmail("admin1@ucm.es");
			Set<Rol> rolesUsu1 = usuarioServicio.getRoles(usu1);
			assertEquals(1, rolesUsu1.size(),"El usuario admin1 debería tener 1 rol y tiene " + rolesUsu1.size());
			for (Rol rolUsu1 : rolesUsu1) {
				assertEquals("ADMIN", rolUsu1.getNombre(),"El usuario admin 1 debería tener el rol ADMIN y tiene " + rolUsu1.getNombre());
			}
			
			Optional<Centro> centroUsu1 = usuarioServicio.getCentro(usu1);
			assertEquals("BOT",centroUsu1.get().getCodCentro(),"El usuario admin1 debería estar asignado al centro BOT y no es asi");

			// Damos de alta usuario gestor de laboratorio Visavet con rol GESTOR
			usuario = new Usuario();
			usuario.setNombre("NombreGestor1");
			usuario.setApellido1("Apell1Gestor1");
			usuario.setEmail("gestor1@ucm.es");
			usuario.setPassword("PWD");
			usuario.setHabilitado("A");
			rol = rolServicio.findByNombre("GESTOR");
			if (rol.isEmpty()) {
				fail("No se encuentra el rol ADMIN");
			}
			roles = new HashSet<Rol>();
			roles.add(rol.get());
			usuario.setRols(roles);
			Optional<LaboratorioVisavet> labVisavet = laboratorioVisavetServicio.findByNombre("Visavet");
			if (labVisavet.isEmpty()) {
				fail("No se encuentra el centro BOT");
			}
			usuario.setIdLaboratorioVisavet(labVisavet.get().getId());
			usuarioServicio.save(usuario);
			
			//Comprobamos usuario gestor 1
			Usuario usug1 = usuarioServicio.findByEmail("gestor1@ucm.es");
			Set<Rol> rolesUsug1 = usuarioServicio.getRoles(usug1);
			assertEquals(1, rolesUsug1.size(),"El usuario gestor1 debería tener 1 rol y tiene " + rolesUsu1.size());
			for (Rol rolUsu1 : rolesUsug1) {
				assertEquals("GESTOR", rolUsu1.getNombre(),"El usuario gestor1 debería tener el rol GESTOR y tiene " + rolUsu1.getNombre());
			}
			
			Optional<LaboratorioVisavet> labUsug1 = usuarioServicio.getLaboratorioVisavet(usug1);
			assertEquals("Visavet",labUsug1.get().getNombre(),"El usuario admin1 debería estar asignado al laboratorio visavet \"Visavet\" y no es asi");

			List<BeanUsuarioGestion> listaUsus = usuarioServicio.listaUsuariosOrdenada();
			assertEquals(2, listaUsus.size(),"Debería haber 2 usuario y hay " + listaUsus.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de Usuarios.");
		}
	}

//			//Damos de alta laboratorios Visavet. Son laboratorios recepctores de Lotes
//			LaboratorioVisavet lv=new LaboratorioVisavet();
//			lv.setNombre("centroreceptor");
//			lv.setCapacidad(200);
//			lv = laboratorioVisavetRepositorio.save(lv);
//			
//			Optional<LaboratorioVisavet> lvCheck = laboratorioVisavetServicio.buscarLaboratorioVisavetPorId(lv.getId());
//			assertTrue(lvCheck.isPresent(),"No se graban bien los laboratorios Visavet (Receptores)");
//			

//			LaboratorioCentro lc1=new LaboratorioCentro();
//			lc1.setNombre("biológicas");
//			lc1 = laboratorioCentroServicio.guardarLaboratorioCentro(lc1);
//
//			LaboratorioCentro lc2=new LaboratorioCentro();
//			lc2.setNombre("visavet");
//			lc2 = laboratorioCentroServicio.guardarLaboratorioCentro(lc2);
//
//			Optional<LaboratorioCentro> lcCheck = laboratorioCentroServicio.buscarLaboratorioCentroPorId(lc1.getId());
//			assertTrue(lcCheck.isPresent(),"No se graban bien los laboratorios de centros.");
//
//			Equipo equipo1=new Equipo();
//			equipo1.setNombre("maquina1");
//			equipo1.setCapacidad("300");
//			equipo1=equipoRepositorio.save(equipo1);
//
//			Equipo equipo2=new Equipo();
//			equipo2.setNombre("maquina2");
//			equipo2.setCapacidad("300");// un string??
//			equipo2=equipoRepositorio.save(equipo1);
//
//			HashSet<Equipo> equipos1=new HashSet<Equipo>(); 
//			equipos1.add(equipo1);
//
//
//			HashSet<Equipo> equipos2=new HashSet<Equipo>(); 
//			equipos2.add(equipo2);
//
//			lc2.setEquipos(equipos2);
//			laboratorioCentroServicio.guardarLaboratorioCentro(lc2);
//
//			lc1.setEquipos(equipos1);
//			lc1 = laboratorioCentroServicio.guardarLaboratorioCentro(lc1);
//			
//			LaboratorioCentro labc1 = laboratorioCentroServicio.buscarLaboratorioCentroPorId(lc1.getId()).get();
//			Set<Equipo> seqLabc1 = labc1.getEquipos();
//			Equipo eqLabc1 = null;
//			for (Equipo eq : seqLabc1) {
//				if (eq.getId() == equipo1.getId()) {
//					eqLabc1 = eq;
//				}
//			}
//			assertTrue(eqLabc1 != null,"No se graban bien los equipos en los laboratorios de centros.");

	// prepara un lote en el centro de salud
//			LoteCentroBean lote1=new LoteCentroBean();
//			lote1.setCapacidad(96);
//			lote1.setNumLote("lote1");// en el Lote se llama distinto
//			lote1.setIdCentro(centro.getId());
//
//			lote1=loteServicio.guardar(lote1);
//
//
//			LoteCentroBean l=loteServicio.findById(lote1.getId());
//
//			assertTrue(l.getIdCentro()!=null,"El centro del lote no debería ser nulo");
//
//			assertTrue(estadoLoteRepositorio.count()==1,"debería haber un estado de lote y hay "+estadoLoteRepositorio.count());
//
//			assertTrue(l.get().getEstadoLote().size()==1,"Debería tener 1 estado asociado y hay "+
//					l.get().getEstadoLote().size());
//
//			assertTrue(loteRepositorio.count()==1,"Debería haber un lote y hay "+loteRepositorio.count());
//
//			centroRepositorio.findById(lote1.getIdCentro());
//			assertTrue(loteRepositorio.count()==1,"Debería haber un lote y hay "+loteRepositorio.count());
//
//			Iterable<Lote> lotes = loteRepositorio.findAll();
//			int lotesCount=0;			
//			for (Lote l1: lotes) lotesCount++;
//
//			assertTrue(lotesCount==1,"Debería haber un lote y hay "+lotesCount);
//
//			List<Lote> resultadoBusqueda = loteRepositorio.findByParams(new LoteBusquedaBean());
//
//			assertTrue(resultadoBusqueda.size()==1,"Debería haber un lote y hay "+resultadoBusqueda.size());
//
//			List<LoteListadoBean> lotesEncontrados = loteServicio.findLoteByParam(new LoteBusquedaBean());
//
//
//
//			assertTrue(lotesEncontrados.size()==1,"Debería haber un lote y hay "+lotesEncontrados.size());
//
//			Page<LoteBeanPlacaRecepcion> lotesEncontradosServicio = servicioLaboratorioUni.buscarLotes(new BusquedaLotesBean(), 
//					PageRequest.of(0, 10, LaboratorioRecepcionUCMController.ORDENACION));
//
//			assertTrue(lotesEncontradosServicio.getTotalElements()==1,"Debería haber un lote y hay "+lotesEncontradosServicio.getTotalElements());
//
//			// define persona centro de salud
//			BeanUsuarioGestion sanitario1 = new BeanUsuarioGestion();
//			sanitario1.setApellido1("Sanitario1A");		
//			sanitario1.setNombre("Sanitario1N");
//			sanitario1.setEmail("Sanitario1@Sanitario1");
//			sanitario1.setPassword("Sanitario1A");
//			sanitario1.setRols(roles);
//			sanitario1.setHabilitado("si");
//			sanitario1.setCentroSeleccionado(centro.getId());			
//			sanitario1.setTipoCentroSeleccionado(TipoCentro.SALUD);
//			usuarioServicio.guardar(usuarioServicio.mapeoBeanEntidadUsuarioAlta(sanitario1,new int[] {}));
//
//			BeanUsuarioGestion sanitario2 = new BeanUsuarioGestion();
//			sanitario2.setApellido1("Sanitario2A");		
//			sanitario2.setNombre("Sanitario2N");
//			sanitario2.setEmail("Sanitario2@Sanitario2");
//			sanitario2.setPassword("Sanitario2A");
//			sanitario2.setCentroSeleccionado(centro.getId());
//			sanitario2.setRols(roles);
//			sanitario2.setHabilitado("si");		
//			sanitario2.setTipoCentroSeleccionado(TipoCentro.SALUD);
//			usuarioServicio.guardar(usuarioServicio.mapeoBeanEntidadUsuarioAlta(sanitario2,new int[] {}));
//
//
//			List<Centro> listaCentros = centroRepositorio.findAll();
//			assertTrue(listaCentros.size()==1,"Debería haber dos centros de salud y hay "+listaCentros.size());// TODO: revisar para 1 centro
//
//			
}