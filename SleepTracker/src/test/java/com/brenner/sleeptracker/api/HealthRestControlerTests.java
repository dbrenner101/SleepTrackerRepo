package com.brenner.sleeptracker.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.brenner.sleeptracker.data.HealthRepository;
import com.brenner.sleeptracker.data.entities.Health;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class HealthRestControlerTests {
	
	@MockBean
	HealthRepository healthRepo;
	
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	private Health health1 = new Health(1, "Well", "Positive");
	private Health health2 = new Health(2, "Ill", "Negative");
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	public void getAllHealth_Success() throws Exception {
		
		List<Health> allHealth = new ArrayList<>(Arrays.asList(health1, health2));
		Mockito.when(this.healthRepo.findAll(Sort.by(Sort.Direction.ASC, "health"))).thenReturn(allHealth);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/health")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].health", is(this.health1.getHealth())));
	}
	
	@Test
	@WithMockUser
	public void getHealth_Success() throws Exception {
		
		Mockito.when(this.healthRepo.findById(this.health1.getHealthId())).thenReturn(Optional.of(this.health1));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/health/" + this.health1.getHealthId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.health", is(this.health1.getHealth())));
	}
	
	@Test
	@WithMockUser
	public void getHealthNotFound_Failure() throws Exception {
		
		Mockito.when(this.healthRepo.findById(this.health1.getHealthId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/health/" + this.health1.getHealthId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(result ->
					assertTrue(result.getResolvedException() instanceof NotFoundException))
			.andExpect(result ->
					assertEquals(result.getResolvedException().getMessage(), "Health with id 1 was not found."));
	}
	
	@Test
	@WithMockUser
	public void addHealth_Success() throws Exception {
		
		Mockito.when(this.healthRepo.save(this.health2)).thenReturn(this.health2);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/health")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.health2)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.health", is(this.health2.getHealth())));
	}
	
	@Test
	@WithMockUser
	public void addHealthNullValue_Failure() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/health")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result ->
				assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException));
	}
	
	@Test
	@WithMockUser
	public void updateHealth_Success() throws Exception {
		
		Health health1Dup = new Health(this.health1.getHealthId(), "fooBar", this.health1.getPerception());
		
		Mockito.when(this.healthRepo.findById(this.health1.getHealthId())).thenReturn(Optional.of(this.health1));
		Mockito.when(this.healthRepo.save(health1Dup)).thenReturn(health1Dup);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/health/" + this.health1.getHealthId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(health1Dup)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.health", is("fooBar")));
	}

}
