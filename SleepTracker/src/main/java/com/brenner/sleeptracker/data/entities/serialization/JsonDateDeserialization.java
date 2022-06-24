package com.brenner.sleeptracker.data.entities.serialization;

import java.io.IOException;
import java.sql.Date;

import com.brenner.sleeptracker.common.CommonUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import lombok.extern.slf4j.Slf4j;

/**
 * JSON deserializer to convert a date string to a Date in MM/dd/yyyy format
 *
 * @author dbrenner
 *
 */
@Slf4j
public class JsonDateDeserialization extends StdDeserializer<Date> {

	protected JsonDateDeserialization(Class<?> vc) {
		super(vc);
	}

	private static final long serialVersionUID = 2279723858865463859L;

	/**
	 * Converts a date string into a Date object. If the string is null or zero length returns null.
	 * 
	 * {@link CommonUtils#formatCommonDateString(String)}
	 */
	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		
		JsonNode json = p.getCodec().readTree(p);
		String dateString = json.asText();
		
		log.debug("Deserializing date string: " + dateString);
		
		if (dateString == null || dateString.trim().length() == 0) {
			return null;
		}
		
		return new Date(CommonUtils.formatCommonDateString(dateString).getTime());
	}

}
