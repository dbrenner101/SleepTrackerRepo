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

import com.brenner.sleeptracker.data.SleepResultRepository;
import com.brenner.sleeptracker.data.entities.SleepResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class SleepResultRestControllerTests {
	
	@MockBean
	SleepResultRepository sleepResultRepo;

    MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	SleepResult sr1 = new SleepResult(1, "sleepResult1", "Positive");
	SleepResult sr2 = new SleepResult(2, "sleepResult2", "Negative");
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	public void testGetAllSleepResult_Success() throws Exception {
		
		List<SleepResult> sleepResults = new ArrayList<>(Arrays.asList(sr1, sr2));
		Mockito.when(this.sleepResultRepo.findAll(Sort.by(Sort.Direction.ASC, "sleepResult"))).thenReturn(sleepResults);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/sleepResults")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$[1].sleepResult", is(this.sr2.getSleepResult())));
	}
	
	@Test
	@WithMockUser
	public void testGetOneSleepResult_Success() throws Exception {
		
		Mockito.when(this.sleepResultRepo.findById(this.sr1.getSleepResultId())).thenReturn(Optional.of(this.sr1));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/sleepResults/" + this.sr1.getSleepResultId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.perception", is(this.sr1.getPerception())));
	}
	
	@Test
	@WithMockUser
	public void testGetOneSleepResult_NotFoundException() throws Exception {
		
		Mockito.when(this.sleepResultRepo.findById(this.sr1.getSleepResultId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/sleepResults/" + this.sr1.getSleepResultId())
				.contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isNotFound())
		    .andExpect(result ->
		            assertTrue(result.getResolvedException() instanceof NotFoundException))
		    .andExpect(result ->
		            assertEquals("Sleep results for id " + this.sr1.getSleepResultId() + " was not found.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser
	public void testAddSleepResult_Success() throws Exception {
		
		Mockito.when(this.sleepResultRepo.save(this.sr2)).thenReturn(this.sr2);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/sleepResults")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.sr2)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.sleepResult", is(this.sr2.getSleepResult())));
	}
	
	@Test
	@WithMockUser
	public void testUpdateSleepResult_Success() throws Exception {
		
		SleepResult updatedSr = new SleepResult();
		BeanUtils.copyProperties(this.sr1, updatedSr);
		updatedSr.setSleepResult("updatedResult");
		
		Mockito.when(this.sleepResultRepo.findById(this.sr1.getSleepResultId())).thenReturn(Optional.of(this.sr1));
		Mockito.when(this.sleepResultRepo.save(updatedSr)).thenReturn(updatedSr);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/sleepResults/" + this.sr1.getSleepResultId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(updatedSr)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.sleepResult", is(updatedSr.getSleepResult())));
	}
	
	@Test
	@WithMockUser
	public void testUpdateSleepResult_NotFoundException() throws Exception {
		
		Mockito.when(this.sleepResultRepo.findById(this.sr1.getSleepResultId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/sleepResults/" + this.sr1.getSleepResultId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.sr1)))
		    .andExpect(status().isNotFound())
		    .andExpect(result ->
		            assertTrue(result.getResolvedException() instanceof NotFoundException))
		    .andExpect(result ->
		            assertEquals("Sleep results for id " + this.sr1.getSleepResultId() + " was not found.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser
	public void testDeleteSleepResult_Success() throws Exception {
		
		Mockito.when(this.sleepResultRepo.findById(this.sr2.getSleepResultId())).thenReturn(Optional.of(this.sr2));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/sleepResults/" + this.sr2.getSleepResultId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.sr2)))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void testDeleteSleepResult_NotFoundException() throws Exception {
		
		Mockito.when(this.sleepResultRepo.findById(this.sr2.getSleepResultId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/sleepResults/" + this.sr2.getSleepResultId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.sr2)))
		    .andExpect(status().isNotFound())
		    .andExpect(result ->
		            assertTrue(result.getResolvedException() instanceof NotFoundException))
		    .andExpect(result ->
		            assertEquals("Sleep results for id " + this.sr2.getSleepResultId() + " was not found.", result.getResolvedException().getMessage()));
	}

}
