package com.brenner.sleeptracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Helper class to populate an initial set of data. Uses the Spring ApplicationRunner interface and 
 * will be call during start up.
 *
 * @author dbrenner
 *
 */
@Component
@Order(1)
@Slf4j
public class DataInitializer implements ApplicationRunner {
	
	@Autowired
	JdbcTemplate  jdbcTemplate;

	public DataInitializer() {}

	/**
	 * Run method called by Spring during start up. The method loads specific SQL files (sql/*) and executes the SQL
	 * loading data.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		log.info("initializing database....");
		
		log.info("Loading default attitudes");
		List<String> sql = loadSqlFromFile("sql/Init_Attitudes.sql");
		loadSql(sql);
		log.info("Done loading default attitudes");
		
		log.info("Loading default locations");
		sql = loadSqlFromFile("sql/Init_Locations.sql");
		loadSql(sql);
		log.info("Done loading default locations");
		
		log.info("Loading default diets");
		sql = loadSqlFromFile("sql/Init_Diet.sql");
		loadSql(sql);
		log.info("Done loading default diets");
		
		log.info("Loading default healths");
		sql = loadSqlFromFile("sql/Init_Health.sql");
		loadSql(sql);
		log.info("Done loading defaults healths");
		
		log.info("Loading defaault habits");
		sql = loadSqlFromFile("sql/Init_Habits.sql");
		loadSql(sql);
		log.info("Done loading default habits");
		
		log.info("Loading default sleep conditions");
		sql = loadSqlFromFile("sql/Init_Sleep_Conditions.sql");
		loadSql(sql); 
		log.info("Done loading default sleep conditions");
		
		log.info("Loading default sleep results");
		sql = loadSqlFromFile("sql/Init_Sleep_Results.sql");
		loadSql(sql);
		log.info("Done loading default sleep results");
		
		
		log.info("database initialized....");
		
	}
	
	private void loadSql(List<String> sqlStmts) {
		
		log.debug("preparing to execute: " + sqlStmts.size());
		
		for (String sql : sqlStmts) {
			this.jdbcTemplate.execute(sql);
		}
		
	}
	
	private List<String> loadSqlFromFile(String filePath) throws IOException {
		
		log.debug("Reading sql statements from: " + filePath);
		
		InputStream ioStream = this.getClass()
	            .getClassLoader()
	            .getResourceAsStream(filePath);
		
		List<String> sqlStmts = new ArrayList<>();
		
		String line = null;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(ioStream))){
			while((line = reader.readLine()) != null) {
				sqlStmts.add(line);
			}
		}
		
		log.debug("Returning " + sqlStmts.size() + " sql statements");
		
		return sqlStmts;
	}

}