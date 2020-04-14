package es.ucm.pcr;


import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles(profiles = "test")
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PcrCovid19ApplicationTests {

	//Comprobamos que la aplicación arranca
	@Test
	@Order(1)
	public void contextLoads() {
	}

}
