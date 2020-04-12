package es.ucm.pcr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
//@ActiveProfiles(profiles = "integracion")
@SpringBootTest
public class PcrCovid19ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
