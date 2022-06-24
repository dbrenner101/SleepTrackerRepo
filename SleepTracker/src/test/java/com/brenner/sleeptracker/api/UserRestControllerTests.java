package com.brenner.sleeptracker.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.brenner.sleeptracker.data.AccountRepository;
import com.brenner.sleeptracker.data.entities.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@SpringBootTest
public class UserRestControllerTests {
	
	MockMvc mockMvc;
	
	@MockBean
	private AccountRepository userRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	Account user1 = new Account(1, "user1", "password1");
	Account user2 = new Account(2, "user2", "password2");
	Account user3 = new Account(2, "user3", "password3"); 
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@Test
	@WithMockUser
	void getUserById_Success() throws Exception {
		
		Mockito.when(userRepo.findById(user3.getAccountId())).thenReturn(Optional.of(user3));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/users/" + user3.getAccountId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username", is("user3")));
		
	}
	
	@Test
	@WithMockUser
	void getUserById_NotFound() throws Exception {
		
		Mockito.when(userRepo.findById(user3.getAccountId())).thenReturn(Optional.empty());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/users/" + user3.getAccountId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
		
	}
	
	@Test
	@WithMockUser
	void getAllUsers_Success() throws Exception {
		
		List<Account> users = new ArrayList<>(Arrays.asList(user1, user2, user3));
		
		Mockito.when(userRepo.findAll()).thenReturn(users);
		
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/api/users")
	            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[2].username", is("user3")));
	}
	
	@Test
	@WithMockUser
	void updateUser_Success() throws Exception {
		
		Account updatedUser = new Account(1, "user1", "password1");
		
		Mockito.when(userRepo.findById(user1.getAccountId())).thenReturn(Optional.of(user1));
		Mockito.when(userRepo.save(updatedUser)).thenReturn(updatedUser);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/users/" + user1.getAccountId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.objectMapper.writeValueAsString(updatedUser));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.username", is("user1")));
	}
	
	@Test
	@WithMockUser
	public void updateUserRecord_recordNotFound() throws Exception {
	    Account updatedUser = new Account(1, "user1", "password1");

	    Mockito.when(userRepo.findById(updatedUser.getAccountId())).thenReturn(null);

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/users/" + updatedUser.getAccountId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.objectMapper.writeValueAsString(updatedUser));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isNotFound())
	            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
	}
	
	@Test
	@WithMockUser
	public void deletePatientById_success() throws Exception {
		
	    Mockito.when(userRepo.findById(user2.getAccountId())).thenReturn(Optional.of(user2));

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/api/users/2")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void deletePatientById_notFound() throws Exception {
		
	    Mockito.when(userRepo.findById(5)).thenReturn(null);

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/api/users/2")
	            .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isNotFound())
	    .andExpect(result ->
	            assertTrue(result.getResolvedException() instanceof NotFoundException))
	    .andExpect(result ->
	            assertEquals("User does not exist.", result.getResolvedException().getMessage()));
	}
	 
}
