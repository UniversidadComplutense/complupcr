//package es.ucm.pcr.servicios;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.Optional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import es.ucm.pcr.modelo.orm.Centro;
//
//@RunWith(SpringRunner.class)
//@ActiveProfiles(profiles = "test")
//@SpringBootTest
//public class CrudServiciosTests {
//	
//	@Autowired
//	CentroServicio centroServicio;
//	
//	@Test
//	public void getCentro() throws Exception {
//		Optional<Centro> centroop = centroServicio.buscarCentroPorId(1);
//		Centro centro = centroop.get();
//		assertEquals("El código de centro coincide","BOT",centro.getCodCentro());
//		
//	}
//	
//	
//}
