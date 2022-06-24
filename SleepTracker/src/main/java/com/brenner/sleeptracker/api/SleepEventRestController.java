package com.brenner.sleeptracker.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brenner.sleeptracker.data.AttitudeRepository;
import com.brenner.sleeptracker.data.DietRepository;
import com.brenner.sleeptracker.data.LocationRepository;
import com.brenner.sleeptracker.data.SleepEventRepo;
import com.brenner.sleeptracker.data.entities.SleepEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * RESTful API for working with SleepEvent entities
 *
 * @author dbrenner
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost*"})
@Slf4j
public class SleepEventRestController {
	
	@Autowired
	SleepEventRepo sleepEventRepo;
	
	@Autowired
	LocationRepository locationRepo;
	
	@Autowired
	AttitudeRepository attitudeRepo;
	
	@Autowired
	DietRepository dietRepo;
	
	/**
	 * GET mapping to retrieve a List of SleepEvents. List will be unsorted.
	 * 
	 * @return The list of SleepEvents.
	 */
	@GetMapping(path="/sleepEvents")
	List<SleepEvent> getAll() {
		
		log.debug("Call to get all SleepEvents");
		
		return this.sleepEventRepo.findAll();
	}
	
	/**
	 * Call to get a single SleepEvent entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Entity unique identifier
	 * @return The SleepEvent entity
	 */
	@GetMapping(path="/sleepEvents/{id}")
	SleepEvent getSleepEvent(@PathVariable Long id) {

		log.debug("Call to get SleepEvent: " + id);
		
		Optional<SleepEvent> optSleepEvent = this.sleepEventRepo.findById(id);
		
		if (optSleepEvent.isEmpty()) {
			throw new NotFoundException("Sleep event with id " + id + " was not found");
		}
		
		return optSleepEvent.get();
	}
	
	/**
	 * POST mapping to add a new SleepEvent entity
	 * 
	 * @param sleepEvent The entity to persist
	 * @return The persisted entity with unique identifier
	 */
	@PostMapping(path="/sleepEvents")
	SleepEvent addSleepEvent(@RequestBody SleepEvent sleepEvent) {
		
		log.debug("Call to add new SleepEvent: " + sleepEvent);
		
		return this.sleepEventRepo.save(sleepEvent);
	}
	
	/**
	 * PUT mapping to update SleepEvent entity. Throws an HTTP 404 if the entity doesn't exist
	 * 
	 * @param id Unique identifier for the entity
	 * @param sleepEvent Updated entity data
	 * @return The updated entity
	 */
	@PutMapping(path="/sleepEvents/{id}")
	SleepEvent saveSleepEvent(@PathVariable Long id, @RequestBody SleepEvent sleepEvent) {
		
		log.debug("Call to update a SleepEvent with id: " + id + " and date: " + sleepEvent);
		
		Optional<SleepEvent> optSleepEvent = this.sleepEventRepo.findById(id);
		
		if (optSleepEvent.isEmpty()) {
			throw new NotFoundException("Sleep event with id " + id + " was not found");
		}
		
		SleepEvent newSleepEvent = optSleepEvent.get();
		newSleepEvent.setWakeTime(sleepEvent.getWakeTime());
		newSleepEvent.setSleepStartTime(sleepEvent.getSleepStartTime());
		
		newSleepEvent.setLocation(sleepEvent.getLocation());
		newSleepEvent.setAttitude(sleepEvent.getAttitude());
		newSleepEvent.setDiet(sleepEvent.getDiet());
		newSleepEvent.setHabits(sleepEvent.getHabits());
		newSleepEvent.setHealth(sleepEvent.getHealth());
		newSleepEvent.setSleepCondition(sleepEvent.getSleepCondition());
		newSleepEvent.setSleepResults(sleepEvent.getSleepResults());
		
		return this.sleepEventRepo.save(newSleepEvent);
		
	}
	
	/**
	 * DELETE mapping to delete a SleepEvent entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Unique identifier for the entity
	 */
	@DeleteMapping(path="/sleepEvents/{id}")
	void deleteSleepEvent(@PathVariable Long id) {
		
		log.debug("Call with delete SleepEvent: " + id);
		
		Optional<SleepEvent> optSleepEvent = this.sleepEventRepo.findById(id);
		
		if (optSleepEvent.isEmpty()) {
			throw new NotFoundException("Sleep event with id " + id + " was not found");
		}
		
		this.sleepEventRepo.delete(optSleepEvent.get());
	}

}
