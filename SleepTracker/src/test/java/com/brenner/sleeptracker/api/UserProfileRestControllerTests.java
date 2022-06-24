package com.brenner.sleeptracker.api;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.brenner.sleeptracker.common.CommonUtils;
import com.brenner.sleeptracker.data.UserProfileRepository;
import com.brenner.sleeptracker.data.entities.Account;
import com.brenner.sleeptracker.data.entities.Gender;
import com.brenner.sleeptracker.data.entities.UserProfile;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class UserProfileRestControllerTests {
	
	@MockBean
	UserProfileRepository userProfileRepo;
	
    MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	private UserProfile up1 = new UserProfile(1, CommonUtils.formatCommonDateString("01/01/1909"), 
			Gender.FEMALE, 150F, "Mary", "Jones", 8.5F, new Account(1, "username1", "password1"));
	private UserProfile up2 = new UserProfile(2, CommonUtils.formatCommonDateString("11/12/2003"), 
			Gender.MALE, 210f, "Bill", "Bells", 6f);
	private UserProfile up3 = new UserProfile(3, CommonUtils.formatCommonDateString("06/02/1962"), 
			Gender.OTHER, 135f, "Chris", "Christopher", 13f);

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	public void getAllUserProfiles_Success() throws Exception {
		
		List<UserProfile> userProfiles = new ArrayList<>(Arrays.asList(up1, up2, up3));
		Mockito.when(this.userProfileRepo.findAll()).thenReturn(userProfiles);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/userProfile")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[1].firstName", is(up2.getFirstName())));
	}
	
	@Test
	@WithMockUser
	public void getUserProfile_Success() throws Exception {
		
		Mockito.when(this.userProfileRepo.findById(this.up3.getUserProfileId())).thenReturn(Optional.of(this.up3));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/userProfile/" + this.up3.getUserProfileId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.firstName", is(this.up3.getFirstName())));
	}
	
	@Test
	@WithMockUser
	public void addUserProfile_Success() throws Exception {
		
		UserProfile profile = new UserProfile(1, CommonUtils.formatCommonDateString("01/01/1909"), 
				Gender.FEMALE, 150F, "Mary", "Jones", 8.5F, new Account(1, "username1", "password1"));
		
		Mockito.when(this.userProfileRepo.save(profile)).thenReturn(profile);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/userProfile")
				.contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.up1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.lastName", is(this.up1.getLastName())));
	}
	
	@Test
	@WithMockUser
	public void updateUserProfile_Success() throws Exception {
		
		UserProfile up2a = new UserProfile(this.up2.getUserProfileId(), this.up2.getBirthdate(), 
				this.up2.getGender(), this.up2.getWeight(), this.up2.getFirstName(), 
				this.up2.getLastName(), this.up2.getTargetSleepHours());
		up2a.setLastName("newLastName");
		
		Mockito.when(this.userProfileRepo.save(this.up2)).thenReturn(up2a);
		Mockito.when(this.userProfileRepo.findById(this.up2.getUserProfileId())).thenReturn(Optional.of(this.up2));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/userProfile/" + this.up2.getUserProfileId())
				.contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(up2a)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.lastName", is(up2a.getLastName())));
	}
	
	@Test
	@WithMockUser
	public void updateUserProfile_Failure() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/userProfile/" + this.up2.getUserProfileId())
				.contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.up2)))
		    .andExpect(status().isNotFound())
		    .andExpect(result ->
		            assertTrue(result.getResolvedException() instanceof NotFoundException))
		    .andExpect(result ->
		            assertEquals("User profile with id 2 was not found.", result.getResolvedException().getMessage()));
	}

}
