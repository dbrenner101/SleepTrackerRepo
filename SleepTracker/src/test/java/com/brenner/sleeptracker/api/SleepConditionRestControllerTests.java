package com.brenner.sleeptracker.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
import org.springframework.beans.BeanUtils;
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

import com.brenner.sleeptracker.data.SleepConditionRepo;
import com.brenner.sleeptracker.data.entities.SleepCondition;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class SleepConditionRestControllerTests {
	
	@MockBean
	SleepConditionRepo sleepConditionRepo;

    MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	SleepCondition sc1 = new SleepCondition(1, "sleepCondition1", "Positive");
	SleepCondition sc2 = new SleepCondition(2, "sleepCondition2", "Negative");
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	public void testGetAllSleepConditions_Success() throws Exception {
		
		List<SleepCondition> scs = new ArrayList<>(Arrays.asList(sc1, sc2));
		Mockito.when(this.sleepConditionRepo.findAll(Sort.by(Sort.Direction.ASC, "sleepCondition"))).thenReturn(scs);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/sleepConditions")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$[1].sleepCondition", is(this.sc2.getSleepCondition())));
	}
	
	@Test
	@WithMockUser
	public void testGetOneSleepCondition_Success() throws Exception {
		
		Mockito.when(this.sleepConditionRepo.findById(this.sc1.getSleepConditionId())).thenReturn(Optional.of(this.sc1));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/sleepConditions/" + this.sc1.getSleepConditionId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.perception", is(this.sc1.getPerception())));
	}
	
	@Test
	@WithMockUser
	public void testGetOneSleepCondition_NotFoundException() throws Exception {
		
		Mockito.when(this.sleepConditionRepo.findById(this.sc1.getSleepConditionId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/sleepConditions/" + this.sc1.getSleepConditionId())
				.contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isNotFound())
		    .andExpect(result ->
		            assertTrue(result.getResolvedException() instanceof NotFoundException))
		    .andExpect(result ->
		            assertEquals("Sleep condition with id " + this.sc1.getSleepConditionId() + " was not found.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser
	public void testAddSleepCondition_Success() throws Exception {
		
		Mockito.when(this.sleepConditionRepo.save(this.sc2)).thenReturn(this.sc2);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/sleepConditions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.sc2)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.sleepCondition", is(this.sc2.getSleepCondition())));
	}
	
	@Test
	@WithMockUser
	public void testUpdateSleepCondition_Success() throws Exception {
		
		SleepCondition updatedSc = new SleepCondition();
		BeanUtils.copyProperties(this.sc1, updatedSc);
		updatedSc.setSleepCondition("updatedCondition");
		
		Mockito.when(this.sleepConditionRepo.findById(this.sc1.getSleepConditionId())).thenReturn(Optional.of(this.sc1));
		Mockito.when(this.sleepConditionRepo.save(updatedSc)).thenReturn(updatedSc);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/sleepConditions/" + this.sc1.getSleepConditionId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(updatedSc)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.sleepCondition", is(updatedSc.getSleepCondition())));
	}
	
	@Test
	@WithMockUser
	public void testUpdateSleepCondition_NotFoundException() throws Exception {
		
		Mockito.when(this.sleepConditionRepo.findById(this.sc1.getSleepConditionId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/sleepConditions/" + this.sc1.getSleepConditionId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.sc1)))
		    .andExpect(status().isNotFound())
		    .andExpect(result ->
		            assertTrue(result.getResolvedException() instanceof NotFoundException))
		    .andExpect(result ->
		            assertEquals("Sleep condition with id " + this.sc1.getSleepConditionId() + " was not found.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser
	public void testDeleteSleepCondition_Success() throws Exception {
		
		Mockito.when(this.sleepConditionRepo.findById(this.sc2.getSleepConditionId())).thenReturn(Optional.of(this.sc2));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/sleepConditions/" + this.sc2.getSleepConditionId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.sc2)))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void testDeleteSleepCondition_NotFoundException() throws Exception {
		
		Mockito.when(this.sleepConditionRepo.findById(this.sc2.getSleepConditionId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/sleepConditions/" + this.sc2.getSleepConditionId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.sc2)))
		    .andExpect(status().isNotFound())
		    .andExpect(result ->
		            assertTrue(result.getResolvedException() instanceof NotFoundException))
		    .andExpect(result ->
		            assertEquals("Sleep condition with id " + this.sc2.getSleepConditionId() + " was not found.", result.getResolvedException().getMessage()));
	}

}
