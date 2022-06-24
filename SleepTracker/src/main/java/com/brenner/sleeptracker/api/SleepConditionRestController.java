package com.brenner.sleeptracker.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brenner.sleeptracker.data.SleepConditionRepo;
import com.brenner.sleeptracker.data.entities.SleepCondition;

import lombok.extern.slf4j.Slf4j;

/**
 * RESTful API supporting Sleep Condition entities
 *
 * @author dbrenner
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class SleepConditionRestController {

	@Autowired
	SleepConditionRepo sleepConditionRepo;
	
	/**
	 * GET mapping to return the list of Sleep Condition entities.
	 * 
	 * @return The List of entities sorted by SleepCondition.sleepCondition.
	 */
	@GetMapping(path="/sleepConditions")
	List<SleepCondition> getAll() {
		
		log.debug("Call to get all SleepConditions");
		
		return this.sleepConditionRepo.findAll(Sort.by(Sort.Direction.ASC, "sleepCondition"));
	}
	
	/**
	 * GET mapping to retrieve a single entity. Throws an HTTP 404 if the entity doesn't exist
	 * 
	 * @param id Entity unique identifier
	 * @return The entity
	 */
	@GetMapping(path="/sleepConditions/{id}")
	SleepCondition getSleepCondition(@PathVariable Integer id) {
		
		log.debug("Call to get a SleepCondition with id: " + id);
		
		Optional<SleepCondition> optSleep = this.sleepConditionRepo.findById(id);
		
		if (optSleep.isEmpty()) {
			throw new NotFoundException("Sleep condition with id " + id + " was not found.");
		}
		
		return optSleep.get();
	}
	
	/**
	 * POST mapping to add a new entity
	 * 
	 * @param newSleepCondition Entity details to persist
	 * @return The entity with unique identifier after persistence
	 */
	@PostMapping(path="/sleepConditions")
	SleepCondition addNewSleepCondition(@RequestBody SleepCondition newSleepCondition) {
		
		log.debug("Call to save a new SleepCondition: " + newSleepCondition);
		
		return this.sleepConditionRepo.save(newSleepCondition);
	}
	
	/**
	 * PUT mapping to update a SleepCondition entity. Throws HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Unique identifier for the entity
	 * @param sleepCondition The data to update
	 * @return The updated SleepCondition
	 */
	@PutMapping(path="/sleepConditions/{id}")
	SleepCondition updateSleepCondition(@PathVariable Integer id, @RequestBody SleepCondition sleepCondition) {
		
		log.debug("Call to update SleepCondition with id: " + id + "; data: " + sleepCondition);
		
		Optional<SleepCondition> optSleep = this.sleepConditionRepo.findById(id);
		
		if (optSleep.isEmpty()) {
			throw new NotFoundException("Sleep condition with id " + id + " was not found.");
		}
		
		SleepCondition newSleep = optSleep.get();
		newSleep.setSleepCondition(sleepCondition.getSleepCondition());
		newSleep.setPerception(sleepCondition.getPerception());
		
		return this.sleepConditionRepo.save(newSleep);
	}

	/**
	 * DELETE mapping to delete a SleepCondition entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id The entity unique identifier.
	 */
	@DeleteMapping(path="/sleepConditions/{id}")
	void deleteSleepCondition(@PathVariable Integer id) {
		
		Optional<SleepCondition> optSleep = this.sleepConditionRepo.findById(id);
		
		if (optSleep.isEmpty()) {
			throw new NotFoundException("Sleep condition with id " + id + " was not found.");
		}
		
		this.sleepConditionRepo.delete(optSleep.get());
	}

}
