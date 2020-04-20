package es.ucm.pcr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import es.ucm.pcr.controladores.InicioControlador;

@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class PcrCovid19ApplicationTests {

	@Autowired
	InicioControlador inicioControlador;

	@Autowired
	MockMvc mockMvc;

	// Comprobamos que la aplicación arranca
	@Test
	@Order(1)
	public void contextLoads() {
		assertThat(inicioControlador).isNotNull();
	}

	// Comprobamos que acceder sin sesión redirige a pantalla de login (acceso)
	// y que el login se hace bien
	@Test
	@Order(2)
	public void paginaInicioLogin() {
		try {
			// Intento acceder a raiz --> Me debe redirigir a acceso
			this.mockMvc.perform(get("http://localhost/"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/acceso"));
			//Accedo a acceso --> Debe llegar y mostrar
			this.mockMvc.perform(get("http://localhost/acceso"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Acceso COVID-19")));
			//Login incorrecto --> Debe devolver una redierección a acceso con error
			MockHttpServletRequestBuilder loginMal = post("http://localhost/acceso")
					.param("email", "centrosalud@ucm.es")
					.param("password","password mala");
			this.mockMvc.perform(loginMal)
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/acceso?error"));
			//Login correcto --> Debe hacer una redirección a la página raiz
			MockHttpServletRequestBuilder loginBien = post("http://localhost/acceso")
					.param("email", "centrosalud@ucm.es")
					.param("password","mypassword");
			this.mockMvc.perform(loginBien)
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error al acceder sin sesión.");
		}
		// .andExpect(content().string(containsString("Acceso COVID-19")));
	}

	// Comprobamos que un usuario con sesión puede acceder a la página de inicio
	@Test
	@Order(3)
	@WithUserDetails("centrosalud@ucm.es")
	public void paginaInicio() {
		try {
			this.mockMvc.perform(get("http://localhost/"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/inicio"));
			this.mockMvc.perform(get("http://localhost/inicio"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("PCR-Covid19")));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error al acceder con sesión.");
		}
	}
}
