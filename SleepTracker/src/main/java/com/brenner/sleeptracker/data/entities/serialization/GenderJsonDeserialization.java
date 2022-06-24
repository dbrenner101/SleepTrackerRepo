package com.brenner.sleeptracker.data.entities.serialization;

import java.io.IOException;

import com.brenner.sleeptracker.data.entities.Gender;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import lombok.extern.slf4j.Slf4j;

/**
 * JSON deserializer that converts a gender JSON string to a Gender enum
 *
 * @author dbrenner
 *
 */
@Slf4j
public class GenderJsonDeserialization extends StdDeserializer<Gender> {

	private static final long serialVersionUID = 8386016592617449722L;
	
	/**
	 * Default constructor
	 */
	public GenderJsonDeserialization() {
		this(null);
	} 
 
	/**
	 * Calls parent with vc
	 * @param vc
	 */
    public GenderJsonDeserialization(Class<?> vc) { 
        super(vc); 
    }

    /**
     * Deserialize method. If JSON gender is null or zero length then defaults to Gender.UNKNOWN
     */
	@Override
	public Gender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		
		JsonNode json = p.getCodec().readTree(p);
		String genderString = json.asText();
		
		log.debug("Deserializing gender: " + genderString);
		
		if (genderString == null || genderString.trim().length() == 0) {
			return Gender.UNKNOWN;
		}
		
		Gender g =  Gender.getGenderByString(genderString);
		return g;
	}

}
