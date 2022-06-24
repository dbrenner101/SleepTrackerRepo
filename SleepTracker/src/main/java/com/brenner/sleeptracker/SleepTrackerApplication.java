package com.brenner.sleeptracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Entry point for Sleep Tracker spring boot application. Data is inititialized after this calls
 * 
 * @see DataInitializer
 *
 */
@SpringBootApplication
public class SleepTrackerApplication extends SpringBootServletInitializer {

	
	public static void main(String[] args) {
		SpringApplication.run(SleepTrackerApplication.class, args);
	}
	

}
