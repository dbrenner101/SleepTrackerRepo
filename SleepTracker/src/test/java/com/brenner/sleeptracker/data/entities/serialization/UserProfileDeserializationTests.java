package com.brenner.sleeptracker.data.entities.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.brenner.sleeptracker.common.CommonUtils;
import com.brenner.sleeptracker.data.entities.Account;
import com.brenner.sleeptracker.data.entities.Gender;
import com.brenner.sleeptracker.data.entities.UserProfile;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class UserProfileDeserializationTests {
	
	@Autowired
	ObjectMapper objectMapper;
	
	Account account = new Account(1, "username", "password");
	UserProfile userProfile = new UserProfile(1, CommonUtils.formatCommonDateString("01/01/1999"), Gender.FEMALE, 125.5F, "Sally", "Smith", 12.5F, account);
	
	String userProfileJson = "{\"userProfileId\":1,\"firstName\":\"Sally\",\"lastName\":\"Smith\",\"birthdate\":\"01/01/1999\",\"gender\":\"Female\",\"weight\":125.5,\"targetSleepHours\":12.5,\"account\":{\"accountId\":1,\"username\":\"username\",\"password\":\"password\"}}";
	
	@Test
	public void testFullObjectHydration_Success() throws Exception {
		
		UserProfile up = this.objectMapper.readValue(this.userProfileJson, UserProfile.class);
		assertEquals(up, this.userProfile);
	}
	
	@Test
	public void testNoAccountHydration_Success() throws Exception {
		
		String jsonTestString = "{\"userProfileId\":1,\"firstName\":\"Sally\",\"lastName\":\"Smith\",\"birthdate\":\"01/01/1999\",\"gender\":\"Female\",\"weight\":125.5,\"targetSleepHours\":12.5}";
		UserProfile up = this.objectMapper.readValue(jsonTestString, UserProfile.class);
		
		assertTrue(up.getUser() == null);
		assertEquals(1, up.getUserProfileId());
		assertEquals("Sally", up.getFirstName());
		assertEquals("Smith", up.getLastName());
		assertEquals("01/01/1999", CommonUtils.parseDate(up.getBirthdate()));
		assertEquals("Female", up.getGender().getGenderName());
		assertEquals(125.5F, up.getWeight());
		assertEquals(12.5F, up.getTargetSleepHours());
	}
	
	@Test
	public void testNoUserProfileId_Success() throws Exception {
		
		String jsonTestString = "{\"userProfileId\":\"\",\"firstName\":\"Sally\",\"lastName\":\"Smith\",\"birthdate\":\"01/01/1999\",\"gender\":\"Female\",\"weight\":125.5,\"targetSleepHours\":12.5,\"account\":{\"accountId\":1,\"username\":\"username\",\"password\":\"password\"}}";
		UserProfile up = this.objectMapper.readValue(jsonTestString, UserProfile.class);
		
		UserProfile validateUp = new UserProfile();
		BeanUtils.copyProperties(this.userProfile, validateUp);
		validateUp.setUserProfileId(null);
		
		assertEquals(up, validateUp);
		
	}
	
	@Test
	public void testNoWeight_Success() throws Exception {
		
		String jsonTestString = "{\"userProfileId\":1,\"firstName\":\"Sally\",\"lastName\":\"Smith\",\"birthdate\":\"01/01/1999\",\"gender\":\"Female\",\"weight\":\"\",\"targetSleepHours\":12.5,\"account\":{\"accountId\":1,\"username\":\"username\",\"password\":\"password\"}}";
		UserProfile up = this.objectMapper.readValue(jsonTestString, UserProfile.class);
		
		UserProfile validateUp = new UserProfile();
		BeanUtils.copyProperties(this.userProfile, validateUp);
		validateUp.setWeight(null);
		
		assertEquals(up, validateUp);
	}
	
	@Test
	public void testNoTargetSleepHours_Success() throws Exception {
		
		String jsonTestString = "{\"userProfileId\":1,\"firstName\":\"Sally\",\"lastName\":\"Smith\",\"birthdate\":\"01/01/1999\",\"gender\":\"Female\",\"weight\":125.5,\"targetSleepHours\":\"\",\"account\":{\"accountId\":1,\"username\":\"username\",\"password\":\"password\"}}";
		UserProfile up = this.objectMapper.readValue(jsonTestString, UserProfile.class);
		
		UserProfile validateUp = new UserProfile();
		BeanUtils.copyProperties(this.userProfile, validateUp);
		validateUp.setTargetSleepHours(null);
		
		assertEquals(up, validateUp);
	}

}
