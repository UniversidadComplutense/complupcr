package es.ucm.pcr.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Equipo;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.repositorio.EquipoRepositorio;
import es.ucm.pcr.repositorio.EstadoLoteRepositorio;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;
import es.ucm.pcr.repositorio.RolRepositorio;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
//@TestMethodOrder(OrderAnnotation.class)
public class CrudServiciosTests {
	
	@Autowired
	CentroServicio centroServicio;
	
	@Autowired
	RolRepositorio rolRepositorio;
	
	@Autowired
	LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;
	
	@Autowired
	LaboratorioVisavetServicio laboratorioVisavetServicio;
	
	@Autowired
	LaboratorioCentroServicio laboratorioCentroServicio;
	
	@Autowired
	EquipoRepositorio equipoRepositorio;
	
	@Autowired
	EstadoLoteRepositorio estadoLoteRepositorio;
	
	@Autowired
	LoteServicio loteServicio;
	
	@Autowired
	RolServicio rolServicio;

	
	@Test
//	@Order(1)
	public void inicializacionBasica() throws Exception {
		try {
			//Los estado de lote, placa, muestra, placalaboratorio y placa visavet se precargan desde src/test/resources/data.sql
			
			//Damos de alta los roles
			Rol rol = new Rol();
			rol.setNombre("ADMIN");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("GESTOR");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("CENTROSALUD");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("RECEPCIONLABORATORIO");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("TECNICOLABORATORIO");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("RESPONSANBLEPCR");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("JEFESERVICIO");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("ANALISTALABORATORIO");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("AUDITOR");
			rolRepositorio.save(rol);
			rol = new Rol();
			rol.setNombre("VOLUNTARIO");
			rolRepositorio.save(rol);
			
			List<BeanRol> listaRoles = rolServicio.generarListaRoles();
			assertEquals(listaRoles.size(),10,"Debería haber 10 roles y no es así.");
			
			//Damos de alta centros
			Centro centro = new Centro();
			centro.setCodCentro("BOT");
			centro.setNombre("Jardín Botánico");
			centro.setTelefono("123454321");
			centro.setEmail("botanicPrueba@ucm.es");
			centro.setDireccion("Av. Complutense S/N");
			centroServicio.guardarCentro(centro);
			centro = new Centro();
			centro.setCodCentro("saludgetafe");
			centro.setNombre("Salud Getafe");
			centro.setTelefono("91111111");
			centro.setEmail("saludgetafe@mad.org");
			centro.setDireccion("unaporejemplo");
			centroServicio.guardarCentro(centro);
			centro = new Centro();
			centro.setCodCentro("DEMO");
			centro.setNombre("Centro de demostración");
			centro.setTelefono("111222333");
			centro.setEmail("centroDemo@ucm.es");
			centro.setDireccion("Dirección demo");
			centroServicio.guardarCentro(centro);
			
			List<BeanCentro> listaCentros = centroServicio.listaCentrosOrdenada();
			assertEquals(listaCentros.size(),3,"Debería haber 3 centros y no es así.");
			
			//Damos de alta laboratorios Visavet. Son laboratorios recepctores de Lotes
			LaboratorioVisavet lv=new LaboratorioVisavet();
			lv.setNombre("centroreceptor");
			lv.setCapacidad(200);
			lv = laboratorioVisavetRepositorio.save(lv);
			
			Optional<LaboratorioVisavet> lvCheck = laboratorioVisavetServicio.buscarLaboratorioVisavetPorId(lv.getId());
			assertTrue(lvCheck.isPresent(),"No se graban bien los laboratorios Visavet (Receptores)");
			
			

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
			LoteCentroBean lote1=new LoteCentroBean();
			lote1.setCapacidad(96);
			lote1.setNumLote("lote1");// en el Lote se llama distinto
			lote1.setIdCentro(centro.getId());

			lote1=loteServicio.guardar(lote1);


			LoteCentroBean l=loteServicio.findById(lote1.getId());

			assertTrue(l.getIdCentro()!=null,"El centro del lote no debería ser nulo");

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
			
		}catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de inicialización básica.");
		}
		
	}
	
	
}
