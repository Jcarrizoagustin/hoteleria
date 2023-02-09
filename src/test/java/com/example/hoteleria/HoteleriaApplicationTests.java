package com.example.hoteleria;

import com.example.hoteleria.controller.ClienteController;
import com.example.hoteleria.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class HoteleriaApplicationTests {
	@Autowired
	WebApplicationContext context;
	WebTestClient client = MockMvcWebTestClient.bindToController(new ClienteController())
			.build();
	//client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

	@BeforeEach
	void setUp() {
		client = MockMvcWebTestClient.bindToApplicationContext(this.context).build();
	}
	@Test
	void contextLoads() {
	}

	@Test
	void test_get_clientes_is_ok(){
		client.get().uri("http://localhost:8080/api/v1/clientes")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk();
	}


}
