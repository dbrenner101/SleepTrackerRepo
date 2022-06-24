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

import com.brenner.sleeptracker.data.AttitudeRepository;
import com.brenner.sleeptracker.data.entities.Attitude;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class AttitudeRestControllerTests {
	
	@MockBean
	private AttitudeRepository attitudeRepo;
	
    MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	Attitude att1 = new Attitude(1, "Happy");
	Attitude att2 = new Attitude(2, "Angry");
	Attitude att3 = new Attitude(3, "Frustrated");
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	public void testGetAllAttitudes_Success() throws Exception {
		
		List<Attitude> atts = new ArrayList<>(Arrays.asList(att1, att2, att3));
		
		Mockito.when(attitudeRepo.findAll(Sort.by(Sort.Direction.ASC, "attitude"))).thenReturn(atts);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/attitudes")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[1].attitude", is("Angry")));
	}
	
	@Test
	@WithMockUser
	public void testAddAttitude_Success() throws Exception {
		
		Mockito.when(attitudeRepo.save(att1)).thenReturn(att1);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/attitudes")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.objectMapper.writeValueAsString(att1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.attitude", is("Happy")));
	}
	
	@Test
	@WithMockUser
	public void testUpdateAttitude_Success() throws Exception {
		
		Attitude newAttitude = new Attitude(att3.getAttitudeId(), att3.getAttitude());
		newAttitude.setAttitude("FooBar");
		
		Mockito.when(this.attitudeRepo.save(att3)).thenReturn(newAttitude);
		
		Optional<Attitude> optAttitude = Optional.of(this.att3);
		Mockito.when(this.attitudeRepo.findById(this.att3.getAttitudeId())).thenReturn(optAttitude);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/attitudes/" + newAttitude.getAttitudeId())
	            .contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.att3)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.attitude", is(newAttitude.getAttitude())));
	}
	
	@Test
	@WithMockUser
	public void testGetAttitude_Success() throws Exception {
		
		Optional<Attitude> optAttitude = Optional.of(this.att3);
		Mockito.when(this.attitudeRepo.findById(this.att3.getAttitudeId())).thenReturn(optAttitude);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/attitudes/" + this.att3.getAttitudeId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.att3)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.attitude", is(this.att3.getAttitude())));;
		
		
	}
	
	@Test
	@WithMockUser
	public void testUpdateAttitudeNull_Fail() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/attitudes")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(405));
	}
	
	@Test
	@WithMockUser
	public void testUpdateAttitudeBadId_Fail() throws Exception {
		
		Mockito.when(this.attitudeRepo.findById(this.att3.getAttitudeId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/attitudes/99")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.att3)))
			.andExpect(status().is(404));
	}

}
