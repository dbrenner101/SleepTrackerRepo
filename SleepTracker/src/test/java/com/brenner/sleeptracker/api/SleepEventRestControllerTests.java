package com.brenner.sleeptracker.api;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.brenner.sleeptracker.data.SleepEventRepo;
import com.brenner.sleeptracker.data.entities.SleepEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class SleepEventRestControllerTests {
	
	@MockBean
	private SleepEventRepo sleepEventRepo;
	
    MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm");
	
	private SleepEvent se1 = new SleepEvent(1L, parseDate("06/01/2022 10:05"), parseDate("06/02/2022 10:05"));
	private SleepEvent se2 = new SleepEvent(2L, parseDate("01/02/1953 6:00"), parseDate("01/03/1953 6:00"));
	private SleepEvent se3 = new SleepEvent(3L, parseDate("12/18/2005 7:12"), parseDate("12/19/2005 7:12"));
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	
	@Test
	@WithMockUser
	public void testGetAllSleepEvents_Success() throws Exception {
		List<SleepEvent> ses = Arrays.asList(se1, se2, se3);
		
		Mockito.when(this.sleepEventRepo.findAll()).thenReturn(ses);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/sleepEvents")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect((status().isOk()))
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$[1].sleepLength", is("24")));
	}
	
	@Test
	@WithMockUser
	public void testGetSleepEvent_Success() throws Exception {
		Mockito.when(this.sleepEventRepo.findById(this.se3.getSleepEventId())).thenReturn(Optional.of(this.se3));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/sleepEvents/" + this.se3.getSleepEventId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect((status().isOk()))
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.sleepLength", is("24")));
			
	}
	
	@Test
	@WithMockUser
	public void testAddSleepEvent_Success() throws Exception {
		Mockito.when(this.sleepEventRepo.save(this.se1)).thenReturn(this.se1);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/sleepEvents")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.se1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.sleepLength", is("24")));
			
	}
	
	@Test
	@WithMockUser
	public void testUpdateSleepEvent_Success() throws Exception {
		Mockito.when(this.sleepEventRepo.findById(this.se2.getSleepEventId())).thenReturn(Optional.of(this.se2));
		Mockito.when(this.sleepEventRepo.save(this.se2)).thenReturn(this.se2);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/sleepEvents/" + this.se2.getSleepEventId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.se2)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.sleepEventId", is(2)));
			
	}
	
	
	
	private static Date parseDate(String dateString) {
		
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
