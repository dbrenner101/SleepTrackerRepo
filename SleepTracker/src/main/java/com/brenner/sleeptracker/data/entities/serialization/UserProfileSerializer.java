package com.brenner.sleeptracker.data.entities.serialization;

import java.io.IOException;

import com.brenner.sleeptracker.common.CommonUtils;
import com.brenner.sleeptracker.data.entities.Account;
import com.brenner.sleeptracker.data.entities.UserProfile;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * Serializer to convert a UserProfile object to JSON
 *
 * @author dbrenner
 *
 */

@Slf4j
public class UserProfileSerializer extends StdSerializer<UserProfile> {
	
	private static final long serialVersionUID = 3008164385554268963L;

	public UserProfileSerializer() {
		this(null);
	}

	protected UserProfileSerializer(Class<UserProfile> t) {
		super(t);
	}

	/**
	 * Convert UserProfile to JSON
	 */
	@Override
	public void serialize(UserProfile up, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		log.debug("Serializing: " + up);
		
		gen.writeStartObject();
		
		if (up.getUserProfileId() != null) {
			gen.writeNumberField("userProfileId", up.getUserProfileId());
		}
		else {
			gen.writeStringField("userProfileId", "");
		}
		gen.writeStringField("firstName", up.getFirstName() != null ? up.getFirstName() : "");
		gen.writeStringField("lastName", up.getLastName() != null ? up.getLastName() : "");
		gen.writeStringField("birthdate", up.getBirthdate() != null ? CommonUtils.parseDate(up.getBirthdate()) : "");
		gen.writeStringField("gender", up.getGender() != null ? up.getGender().getGenderName() : "");
		if (up.getWeight() != null) {
			gen.writeNumberField("weight", up.getWeight());
		} else {
			gen.writeStringField("weight", "");
		}
		
		if (up.getTargetSleepHours() != null) {
			gen.writeNumberField("targetSleepHours",  up.getTargetSleepHours());
		}
		else {
			gen.writeStringField("targetSleepHours", "");
		}
		
		if (up.getUser() != null) {
			Account u = up.getUser();
			gen.writeObjectFieldStart("account");
			gen.writeNumberField("accountId", u.getAccountId());
			gen.writeStringField("username", u.getUsername());
			gen.writeStringField("password", u.getPassword());
			gen.writeStringField("role", u.getRole());
			gen.writeEndObject();
		}
		gen.writeEndObject();
		
		log.debug("UserProfile JSON: " + gen.toString());
		
	}

}
