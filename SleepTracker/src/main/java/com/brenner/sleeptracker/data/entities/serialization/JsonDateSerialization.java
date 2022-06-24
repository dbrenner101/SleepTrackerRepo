package com.brenner.sleeptracker.data.entities.serialization;

import java.io.IOException;
import java.util.Date;

import com.brenner.sleeptracker.common.CommonUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * Serializer to convert a Date object to a JSON string
 *
 * @author dbrenner
 *
 */
@Slf4j
public class JsonDateSerialization extends StdSerializer<Date> {

	private static final long serialVersionUID = 5136212569328436488L;

	protected JsonDateSerialization() {
		super(Date.class);
	}

	/**
	 * Converts the supplied Date into a String. 
	 * 
	 * {@link CommonUtils#parseDate(Date)}
	 */
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		log.debug("Converting " + date + " to JSON");
		
		gen.writeString(CommonUtils.parseDate(date));
	}

}
