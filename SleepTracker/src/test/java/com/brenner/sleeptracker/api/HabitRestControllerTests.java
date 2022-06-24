package com.brenner.sleeptracker.api;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.brenner.sleeptracker.data.HabitRepository;
import com.brenner.sleeptracker.data.entities.Habit;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class HabitRestControllerTests {
	
    MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	HabitRepository habitRepo;
	
	@Autowired
	private WebApplicationContext context;
	
	private static final String BASE_API_URL = "/api/habits";
	
	private Habit habit1 = new Habit(1, "habit1");
	private Habit habit2 = new Habit(2, "habit2");
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	public void testGetAllHabits_Success() throws Exception {
		
		List<Habit> habits = Arrays.asList(habit1, habit2);
		
		Mockito.when(this.habitRepo.findAll(Sort.by(Sort.Direction.ASC, "habit"))).thenReturn(habits);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get(BASE_API_URL)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$[0].habit", is(this.habit1.getHabit())));
	}
	
	@Test
	@WithMockUser
	public void getHabit_Success() throws Exception {
		Mockito.when(this.habitRepo.findById(this.habit2.getHabitId())).thenReturn(Optional.of(this.habit2));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get(BASE_API_URL + "/" + this.habit2.getHabitId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.habit", is(this.habit2.getHabit())));
	}
	
	@Test
	@WithMockUser
	public void addHabit_Success() throws Exception {
		Mockito.when(this.habitRepo.save(this.habit1)).thenReturn(this.habit1);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post(BASE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.habit1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.habit", is(this.habit1.getHabit())));
	}
	
	@Test
	@WithMockUser
	public void updateHabit_Success() throws Exception {
		Mockito.when(this.habitRepo.findById(this.habit2.getHabitId())).thenReturn(Optional.of(this.habit2));
		Mockito.when(this.habitRepo.save(this.habit2)).thenReturn(this.habit2);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put(BASE_API_URL + "/" + this.habit2.getHabitId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.habit2)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.habit", is(this.habit2.getHabit())));
	}
	
	@Test
	@WithMockUser
	public void deleteHabit_Success() throws Exception {
		Mockito.when(this.habitRepo.findById(this.habit2.getHabitId())).thenReturn(Optional.of(this.habit2));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete(BASE_API_URL + "/" + this.habit2.getHabitId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.habit2)))
			.andExpect(status().isOk());
		
	}

}
