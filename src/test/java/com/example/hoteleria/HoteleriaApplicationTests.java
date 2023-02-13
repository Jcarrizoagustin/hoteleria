package com.example.hoteleria;

import com.example.hoteleria.controller.ClienteController;
import com.example.hoteleria.dtos.cliente.ClienteCreateDto;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.services.ClienteService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HoteleriaApplicationTests {
	private final String PATH = "/api/v1";
	@Autowired
	MockMvc mockMvc;
	@Test
	void get_habitaciones_is_ok() throws Exception {
		mockMvc.perform(get(PATH+"/habitaciones")).andExpect(status().isOk());
	}

	@Test
	void get_clientes_is_ok() throws Exception {
		mockMvc.perform(get(PATH+"/clientes")).andExpect(status().isOk());
	}

	@Test
	void post_cliente_is_badRequest() throws Exception{
		//Al postear un cliente se tiene que pasar en el body un objeto valido, de lo contrario sera una bad request
		mockMvc.perform(post(PATH+"/clientes")).andExpect(status().isBadRequest());

	}

	@Test
	void post_cliente_isOk() throws Exception{
		ClienteCreateDto dto = new ClienteCreateDto();
		dto.setNombre("Laurita");
		dto.setApellido("Chicle");
		dto.setTelefono("3834885522");
		dto.setEmail("laura@gmail.com");
		Gson gson = new Gson();
		String json = gson.toJson(dto);

		mockMvc
				.perform(post(PATH+"/clientes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk());
	}

	@Test
	void post_cliente_con_telefono_ya_registrado_isConflict() throws Exception{
		ClienteCreateDto dto = new ClienteCreateDto();
		dto.setNombre("Laurita");
		dto.setApellido("Chicle");
		dto.setTelefono("3834808080");
		dto.setEmail("laura@gmail.com");
		Gson gson = new Gson();
		String json = gson.toJson(dto);

		mockMvc
				.perform(post(PATH+"/clientes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isConflict());
	}

}
