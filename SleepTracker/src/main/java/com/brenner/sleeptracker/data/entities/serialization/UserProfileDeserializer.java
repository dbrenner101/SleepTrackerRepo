package com.brenner.sleeptracker.data.entities.serialization;

import java.io.IOException;

import com.brenner.sleeptracker.data.entities.Gender;
import com.brenner.sleeptracker.data.entities.Account;
import com.brenner.sleeptracker.data.entities.UserProfile;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import lombok.extern.slf4j.Slf4j;

/**
 * Deserializer to convert a UserProfile JSON string to a UserProfile object. 
 *
 * @author dbrenner
 *
 */
@Slf4j
public class UserProfileDeserializer extends StdDeserializer<UserProfile> {
	
	private static final long serialVersionUID = 7959432137511483940L;

	public UserProfileDeserializer() {
		this(null);
	}

	protected UserProfileDeserializer(Class<?> vc) {
		super(vc);
	}

	/**
	 * Reads the json tree and parses into a UserProfile object. UserProfile.Account is optional.
	 */
	@Override
	public UserProfile deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		
		UserProfile userProfile = new UserProfile();
		
		JsonNode json = p.getCodec().readTree(p);
		
		log.debug("Converting " + json.toPrettyString() + " to UserProfile");
		
		if (json.get("userProfileId") != null && json.get("userProfileId").asText().length() > 0) {
			userProfile.setUserProfileId(json.get("userProfileId").asInt());
		}
		
		userProfile.setBirthdate(json.get("birthdate").asText());
		userProfile.setFirstName(json.get("firstName").asText());
		
		if (json.get("gender").asText() != null && json.get("gender").asText().length() > 0) {
			userProfile.setGender(Gender.getGenderByString(json.get("gender").asText()));
		}
		userProfile.setLastName(json.get("lastName").asText());
		
		if (json.get("targetSleepHours") != null && json.get("targetSleepHours").asText().length() > 0) {
			userProfile.setTargetSleepHours(Float.valueOf(json.get("targetSleepHours").asText()));
		}
		
		if (json.get("weight").asText() != null && json.get("weight").asText().length() > 0) {
			userProfile.setWeight(Float.valueOf(json.get("weight").asText()));
		}
		
		if (json.get("account") != null) {
			log.debug("No account object on JSON string");
			
			JsonNode userNode = json.get("account");
			Account user = new Account();
			user.setAccountId(userNode.get("accountId").asInt());
			user.setUsername(userNode.get("username").asText());
			if (userNode.get("password") != null) {
				user.setPassword(userNode.get("password").asText());
			}
			userProfile.setUser(user);
		}	
		
		return userProfile;
	}

}
