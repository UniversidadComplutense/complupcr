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

import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Equipo;
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
	
	@Autowired
	EquipoServicio equipoServicio;
	
	static Integer idLAbCentro;

	
	@Test
	@Order(1)
	public void inicializacionBasicaCentrosSalud() {
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
			
			Optional<Centro> oCentroPru = centroServicio.findByCodCentro("DEMO");
			assertEquals("centroDemo@ucm.es", oCentroPru.get().getEmail());
			
		} catch (

		Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de centros de salud.");
		}
	}

	@Test
	@Order(2)
	public void inicializacionBasicaLaboratoriosVisavet() {
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

			Optional<LaboratorioVisavet> opLab = laboratorioVisavetServicio.findByNombre("Visavet");
			assertEquals(100, opLab.get().getCapacidad());

		} catch (

		Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de laboratorios visavet.");
		}
	}

	@Test
	@Order(3)
	public void inicializacionBasicaLaboratoriosCentro() {
		try {
			// Damos de alta laboratorios Visavet
			LaboratorioCentro labCentro = new LaboratorioCentro();
			labCentro.setNombre("VisavetPCR");
			laboratorioCentroServicio.save(labCentro);
			labCentro = new LaboratorioCentro();
			labCentro.setNombre("Biología PCR");
			LaboratorioCentro labCentro2 =  laboratorioCentroServicio.save(labCentro);
			
			//Pasamos el id para sucesivos tests
			GestorCrudServiciosTests.idLAbCentro = labCentro2.getId();
			
			LaboratorioCentro labCentro3 = laboratorioCentroServicio.findById(GestorCrudServiciosTests.idLAbCentro).get();
			assertEquals("Biología PCR", labCentro3.getNombre(),"El nombre del laboratorio debería ser Biología PCR y no es así.");

		} catch (

		Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de laboratorios centro.");
		}
	}

	@Test
	@Order(4)
	public void inicializacionBasicaUsuarios() {
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

		} catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de Usuarios.");
		}
	}	
	@Test
	@Order(5)
	public void inicializacionBasicaEquiposPCR() {
		try {
			//Recuperamos el laboratorio centro
			LaboratorioCentro labCentro = laboratorioCentroServicio.findById(GestorCrudServiciosTests.idLAbCentro).get();
			
			//Damos de alta un equipo
			Equipo equipo = new Equipo();
			equipo.setLaboratorioCentro(labCentro);
			equipo.setNombre("Equipo PCR 1");
			equipo.setCapacidad(100);
			equipo = equipoServicio.save(equipo);
			
			//Comprobamos
			Equipo equipo2 = equipoServicio.findById(equipo.getId()).get();
			assertEquals("Equipo PCR 1", equipo2.getNombre(), "El equipo debería llamarse 'Equipo PCR 1' y no es así.");
			
			List<Equipo> equipos = equipoServicio.findByLaboratorioCentro(labCentro);
			assertEquals(1, equipos.size(), "Debería haber un equipo y hay " + equipos.size());
			assertEquals("Equipo PCR 1", equipos.get(0).getNombre(), "El equipo debería llamarse 'Equipo PCR 1' y no es así.");
		} catch (

		Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica de laboratorios centro.");
		}
	}
}