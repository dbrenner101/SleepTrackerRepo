package com.brenner.sleeptracker.data.entities.serialization;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
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
public class UserProfileSerializationTests {
	
	@Autowired
	ObjectMapper objectMapper;
	
	Account account = new Account(1, "username", "password");
	UserProfile userProfile = new UserProfile(1, CommonUtils.formatCommonDateString("01/01/1999"), Gender.FEMALE, 125.5F, "Sally", "Smith", 12.5F, account);
	
	String userProfileJson = "{\"userProfileId\":1,\"firstName\":\"Sally\",\"lastName\":\"Smith\",\"birthdate\":\"01/01/1999\",\"gender\":\"Female\",\"weight\":125.5,\"targetSleepHours\":12.5,\"account\":{\"accountId\":1,\"username\":\"username\",\"password\":\"password\"}}";
	
	@Test
	public void testUserProfileSerializationFullObject_Success() throws Exception {
		
		String userProfileJson = this.objectMapper.writeValueAsString(this.userProfile);
		
		JSONObject userProfileObj = new JSONObject(userProfileJson);
		assertEquals(this.userProfile.getUserProfileId(), userProfileObj.get("userProfileId"));
		assertEquals(CommonUtils.parseDate(this.userProfile.getBirthdate()), userProfileObj.get("birthdate"));
		assertEquals(this.userProfile.getGender().getGenderName(), userProfileObj.get("gender"));
		assertEquals(this.userProfile.getWeight(), Float.valueOf(userProfileObj.getString("weight")));
		assertEquals(this.userProfile.getFirstName(), userProfileObj.getString("firstName"));
		assertEquals(this.userProfile.getLastName(), userProfileObj.getString("lastName"));
		assertEquals(this.userProfile.getTargetSleepHours(), Float.valueOf(userProfileObj.getString("targetSleepHours")));
		
		JSONObject accountObject = new JSONObject(userProfileObj.getString("account"));
		assertEquals(this.userProfile.getUser().getAccountId(), accountObject.getInt("accountId"));
		assertEquals(this.userProfile.getUser().getUsername(), accountObject.getString("username"));
		assertEquals(this.userProfile.getUser().getPassword(), accountObject.getString("password"));
		assertEquals(this.userProfile.getUser().getRole(), accountObject.getString("role"));
	}
	
	@Test
	public void testUserProfileSerializationNoAccount_Success() throws Exception {
		
		UserProfile up = new UserProfile();
		BeanUtils.copyProperties(this.userProfile, up);
		up.setUser(null);
		
		String userProfileJson = this.objectMapper.writeValueAsString(up);
		
		JSONObject userProfileObj = new JSONObject(userProfileJson);
		assertEquals(up.getUserProfileId(), userProfileObj.get("userProfileId"));
		assertEquals(CommonUtils.parseDate(up.getBirthdate()), userProfileObj.get("birthdate"));
		assertEquals(up.getGender().getGenderName(), userProfileObj.get("gender"));
		assertEquals(up.getWeight(), Float.valueOf(userProfileObj.getString("weight")));
		assertEquals(up.getFirstName(), userProfileObj.getString("firstName"));
		assertEquals(up.getLastName(), userProfileObj.getString("lastName"));
		assertEquals(up.getTargetSleepHours(), Float.valueOf(userProfileObj.getString("targetSleepHours")));
		
		assertThatExceptionOfType(JSONException.class).isThrownBy(() -> {
			new JSONObject(userProfileObj.getString("account"));
		});
	}
	
	@Test
	public void testUserProfileSerializationNoUserProfileId_Success() throws Exception {
		
		UserProfile up = new UserProfile();
		BeanUtils.copyProperties(this.userProfile, up);
		up.setUserProfileId(null);
		
		String userProfileJson = this.objectMapper.writeValueAsString(up);
		
		JSONObject userProfileObj = new JSONObject(userProfileJson);
		assertEquals("", userProfileObj.get("userProfileId"));
		assertEquals(CommonUtils.parseDate(up.getBirthdate()), userProfileObj.get("birthdate"));
		assertEquals(up.getGender().getGenderName(), userProfileObj.get("gender"));
		assertEquals(up.getWeight(), Float.valueOf(userProfileObj.getString("weight")));
		assertEquals(up.getFirstName(), userProfileObj.getString("firstName"));
		assertEquals(up.getLastName(), userProfileObj.getString("lastName"));
		assertEquals(up.getTargetSleepHours(), Float.valueOf(userProfileObj.getString("targetSleepHours")));
	}
	
	@Test
	public void testUserProfileSerializationNoWeight_Success() throws Exception {
		
		UserProfile up = new UserProfile();
		BeanUtils.copyProperties(this.userProfile, up);
		up.setWeight(null);
		
		String userProfileJson = this.objectMapper.writeValueAsString(up);
		
		JSONObject userProfileObj = new JSONObject(userProfileJson);
		assertEquals(up.getUserProfileId(), userProfileObj.get("userProfileId"));
		assertEquals(CommonUtils.parseDate(up.getBirthdate()), userProfileObj.get("birthdate"));
		assertEquals(up.getGender().getGenderName(), userProfileObj.get("gender"));
		assertEquals("", userProfileObj.getString("weight"));
		assertEquals(up.getFirstName(), userProfileObj.getString("firstName"));
		assertEquals(up.getLastName(), userProfileObj.getString("lastName"));
		assertEquals(up.getTargetSleepHours(), Float.valueOf(userProfileObj.getString("targetSleepHours")));
	}
	
	@Test
	public void testUserProfileSerializationNoTargetSleepHoursId_Success() throws Exception {
		
		UserProfile up = new UserProfile();
		BeanUtils.copyProperties(this.userProfile, up);
		up.setTargetSleepHours(null);
		
		String userProfileJson = this.objectMapper.writeValueAsString(up);
		
		JSONObject userProfileObj = new JSONObject(userProfileJson);
		assertEquals(up.getUserProfileId(), userProfileObj.get("userProfileId"));
		assertEquals(CommonUtils.parseDate(up.getBirthdate()), userProfileObj.get("birthdate"));
		assertEquals(up.getGender().getGenderName(), userProfileObj.get("gender"));
		assertEquals(up.getWeight(), Float.valueOf(userProfileObj.getString("weight")));
		assertEquals(up.getFirstName(), userProfileObj.getString("firstName"));
		assertEquals(up.getLastName(), userProfileObj.getString("lastName"));
		assertEquals("", userProfileObj.getString("targetSleepHours"));
	}
	
	@Test
	public void testUserProfileSerializationEmptyObjectHoursId_Success() throws Exception {
		
		UserProfile up = new UserProfile();
		
		String userProfileJson = this.objectMapper.writeValueAsString(up);
		
		JSONObject userProfileObj = new JSONObject(userProfileJson);
		assertEquals("", userProfileObj.get("userProfileId"));
		assertEquals("", userProfileObj.get("birthdate"));
		assertEquals("", userProfileObj.get("gender"));
		assertEquals("", userProfileObj.getString("weight"));
		assertEquals("", userProfileObj.getString("firstName"));
		assertEquals("", userProfileObj.getString("lastName"));
		assertEquals("", userProfileObj.getString("targetSleepHours"));
	}

}
