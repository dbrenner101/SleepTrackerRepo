package com.brenner.sleeptracker.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.brenner.sleeptracker.data.DietRepository;
import com.brenner.sleeptracker.data.entities.Diet;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class DietRestControllerTests {
	
	@MockBean
	DietRepository dietRepo;
	
    MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	private Diet diet1 = new Diet(1, "diet1");
	private Diet diet2 = new Diet(2, "diet2");
	private Diet diet3 = new Diet(3, "diet3");
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	public void testGetAllDiets_Success() throws Exception {
		
		
		List<Diet> atts = new ArrayList<>(Arrays.asList(diet1, diet2, diet3));
		
		Mockito.when(dietRepo.findAll(Sort.by(Sort.Direction.ASC, "diet"))).thenReturn(atts);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/diets")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[1].diet", is(this.diet2.getDiet())));
	}
	
	@Test
	@WithMockUser
	public void testAddDiet_Success() throws Exception {
		
		Mockito.when(dietRepo.save(diet1)).thenReturn(diet1);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/diets")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.objectMapper.writeValueAsString(diet1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.diet", is(this.diet1.getDiet())));
	}
	
	@Test
	@WithMockUser
	public void testUpdateDiet_Success() throws Exception {
		
		Diet newDiet = new Diet(this.diet3.getDietId(), this.diet3.getDiet());
		newDiet.setDiet("FooBar");
		
		Mockito.when(this.dietRepo.save(this.diet3)).thenReturn(newDiet);
		
		Optional<Diet> optDiet = Optional.of(this.diet3);
		Mockito.when(this.dietRepo.findById(this.diet3.getDietId())).thenReturn(optDiet);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/diets/" + newDiet.getDietId())
	            .contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.diet3)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.diet", is(newDiet.getDiet())));
	}
	
	@Test
	@WithMockUser
	public void testGetDiet_Success() throws Exception {
		
		Optional<Diet> optDiet = Optional.of(this.diet3);
		Mockito.when(this.dietRepo.findById(this.diet3.getDietId())).thenReturn(optDiet);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/diets/" + this.diet3.getDietId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.diet3)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.diet", is(this.diet3.getDiet())));;
		
		
	}
	
	@Test
	@WithMockUser
	public void testUpdateAttitudeNull_Fail() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/diets")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(405));
	}
	
	@Test
	@WithMockUser
	public void testUpdateAttitudeBadId_Fail() throws Exception {
		
		Mockito.when(this.dietRepo.findById(this.diet3.getDietId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/diets/99")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.diet3)))
			.andExpect(status().is(404));
	}

}
