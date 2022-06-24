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

import com.brenner.sleeptracker.data.LocationRepository;
import com.brenner.sleeptracker.data.entities.Location;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class LocationRestControllerTests {

	@MockBean
	LocationRepository locationRepo;
	
    MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	private Location loc1 = new Location(1, "location1", "Negative");
	private Location loc2 = new Location(2, "location2", "Positive");
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	public void testGetAllLocations_Success() throws Exception {
		
		List<Location> allLocations = new ArrayList<>(Arrays.asList(loc1, loc2));
		
		Mockito.when(this.locationRepo.findAll(Sort.by(Sort.Direction.ASC, "locationName"))).thenReturn(allLocations);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/locations")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$[1].locationName", is("location2")));
	}
	
	@Test
	@WithMockUser
	public void testGetOneLocation_Success() throws Exception {
		
		Mockito.when(this.locationRepo.findById(this.loc1.getLocationId())).thenReturn(Optional.of(this.loc1));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/locations/" + this.loc1.getLocationId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.locationName", is(this.loc1.getLocationName())));
	}
	
	@Test
	@WithMockUser
	public void testGetOneLocation_Failure() throws Exception {
		
		Mockito.when(this.locationRepo.findById(this.loc1.getLocationId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/locations/" + this.loc1.getLocationId())
				.contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isNotFound())
	    .andExpect(result ->
	            assertTrue(result.getResolvedException() instanceof NotFoundException))
	    .andExpect(result ->
	            assertEquals("Location with id 1 was not found.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser
	public void testAddLocation_Success() throws Exception {
		
		Mockito.when(this.locationRepo.save(this.loc2)).thenReturn(this.loc2);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/locations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.loc2)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.locationName", is(this.loc2.getLocationName())));
	}
	
	@Test
	@WithMockUser
	public void testUpdateLocation_Success() throws Exception {
		
		Mockito.when(this.locationRepo.findById(this.loc2.getLocationId())).thenReturn(Optional.of(this.loc2));
		
		Location updatedLocation = new Location(this.loc2.getLocationId(), "newLocationName", this.loc2.getPerception());
		
		Mockito.when(this.locationRepo.save(updatedLocation)).thenReturn(updatedLocation);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/locations/" + this.loc2.getLocationId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(updatedLocation)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.locationName", is("newLocationName")));
	}
	
	@Test
	@WithMockUser
	public void testUpdateLocation_Failure() throws Exception {
		
		Mockito.when(this.locationRepo.findById(this.loc2.getLocationId())).thenReturn(Optional.empty());
		Location updatedLocation = new Location(this.loc2.getLocationId(), "newLocationName", this.loc2.getPerception());

		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/locations/" + this.loc2.getLocationId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(updatedLocation)))
	    .andExpect(status().isNotFound())
	    .andExpect(result ->
	            assertTrue(result.getResolvedException() instanceof NotFoundException))
	    .andExpect(result ->
	            assertEquals("Location with id 2 was not found.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser
	public void testDeleteLocation_Success() throws Exception {
		
		Mockito.when(this.locationRepo.findById(this.loc2.getLocationId())).thenReturn(Optional.of(this.loc2));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/locations/" + this.loc2.getLocationId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.loc2)))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void testDeleteLocation_Failure() throws Exception {
		
		Mockito.when(this.locationRepo.findById(this.loc2.getLocationId())).thenReturn(Optional.empty());
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/locations/" + this.loc2.getLocationId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.loc2)))
	    .andExpect(status().isNotFound())
	    .andExpect(result ->
	            assertTrue(result.getResolvedException() instanceof NotFoundException))
	    .andExpect(result ->
	            assertEquals("Location with id 2 was not found.", result.getResolvedException().getMessage()));
	}

}
