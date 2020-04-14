package es.ucm.pcr.servicios;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.modelo.orm.Centro;

@ActiveProfiles(profiles = "test")
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CentroSaludServiciosTests {
	
	@Autowired
	CentroServicio centroServicio;
	
	@Autowired
	MuestraServicio muestraServicio;
	
	@Test
	@Order(10)
	public void altaMuestras() {
		try {
			//Damos de alta centro de salud
			Centro centro = new Centro();
			centro.setCodCentro("TEST1");
			centro.setNombre("Centro de test1");
			centro.setTelefono("123456789");
			centro.setDireccion("Dirección test1");
			centro = centroServicio.save(centro);
			
			//Damos de alta muestras
			MuestraCentroBean mcb = new MuestraCentroBean();
			mcb.setCentro(centro.getId());
			mcb.setEtiqueta("muestra1");
			//TODO Continuar
			
			
		}catch (Exception e) {
			e.printStackTrace();
			fail("Falló la prueba de alta de muestras");
		}
	}

}
