package com.brenner.sleeptracker.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to support Date parsing and formatting.
 *
 * @author dbrenner
 *
 */
@Slf4j
public class CommonUtils {
	
	/**
     * MM/dd/yyyy date format
     */
    private static final SimpleDateFormat COMMON_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    
    /**
     * Converts the date string to a Date in MM/dd/yyyy format. Throws a RuntimeException if the string cannot be converted.
     * 
     * @param dateString The string to convert into a date
     * @return The Date represented by the string
     */
    public static Date formatCommonDateString(String dateString) {
    	
    	log.debug("Formatting " + dateString + " to Date object");
    	
    	try {
			return COMMON_DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
    }
    
    /**
     * Parses a Date into MM/dd/yyy formatted String
     * 
     * @param date Date to parse
     * @return The String equivalent
     */
    public static String parseDate(Date date) {
    	
    	log.debug("Parsing " + date + " in MM/dd/yyy");
    	
    	return COMMON_DATE_FORMAT.format(date);
    }

}
