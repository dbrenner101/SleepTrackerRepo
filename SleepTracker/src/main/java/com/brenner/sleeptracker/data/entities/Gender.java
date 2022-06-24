package com.brenner.sleeptracker.data.entities;

import com.brenner.sleeptracker.data.entities.serialization.GenderJsonDeserialization;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Enum for Gender 
 *
 * @author dbrenner
 *
 */
@JsonDeserialize(using=GenderJsonDeserialization.class)
public enum Gender {

	MALE("Male"),
	FEMALE("Female"),
	OTHER("Other"),
	@JsonEnumDefaultValue UNKNOWN("");
	
	private String genderName;
	
	private Gender(String genderName) {
		this.genderName = genderName;
	}
	
	@JsonValue
	public String getGenderName() {
		return genderName;
	}
	
	public static Gender getGenderByString(String genderString) {
		switch(genderString) {
			case("Male"): 
				return Gender.MALE;
			case("Female"):
				return Gender.FEMALE;
			case("Other"):
				return Gender.OTHER;
			default:
				return Gender.UNKNOWN;
		}
	}
	
	
}
