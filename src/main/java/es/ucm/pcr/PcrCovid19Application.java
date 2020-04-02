package es.ucm.pcr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PcrCovid19Application {

	public static void main(String[] args) {
		SpringApplication.run(PcrCovid19Application.class, args);
//		
//		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
//		System.out.println(enc.encode("mypassword"));
	}
	
	

}
