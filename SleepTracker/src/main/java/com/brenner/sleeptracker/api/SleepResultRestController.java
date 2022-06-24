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

import com.brenner.sleeptracker.data.SleepResultRepository;
import com.brenner.sleeptracker.data.entities.SleepResult;

import lombok.extern.slf4j.Slf4j;

/**
 * RESTful API for SleepResult entities
 *
 * @author dbrenner
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class SleepResultRestController {
	
	@Autowired
	SleepResultRepository sleepResultRepo;

	public SleepResultRestController() {}
	
	/**
	 * GET mapping to retrieve all SleepResult entities. List is sorted by SleepResult.sleepResult
	 * 
	 * @return The list of entities
	 */
	@GetMapping(path="sleepResults")
	List<SleepResult> getAll() {
		
		log.debug("Call to get all SleepResults");
		
		return this.sleepResultRepo.findAll(Sort.by(Sort.Direction.ASC, "sleepResult"));
	}
	
	/**
	 * GET mapping to retrieve a single SleepResult entity. Throws and HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Entity unique identifier
	 * @return The request SleepResult
	 */
	@GetMapping(path="/sleepResults/{id}")
	SleepResult getSleepResult(@PathVariable Integer id) {
		
		log.debug("Call to get SleepResult with id: " + id);
		
		Optional<SleepResult> sleep = this.sleepResultRepo.findById(id);
		
		if (sleep.isEmpty()) {
			throw new NotFoundException("Sleep results for id " + id + " was not found.");
		}
		
		return sleep.get();
	}
	
	/**
	 * POST mapping to save a new SleepResult entity. 
	 * 
	 * @param sleepResult Entity data to persist
	 * @return The entity with unique identifier after persistence.
	 */
	@PostMapping(path="/sleepResults")
	SleepResult addSleepResult(@RequestBody SleepResult sleepResult) {
		
		log.debug("Call to save new SleepResult: " + sleepResult);
		
		return this.sleepResultRepo.save(sleepResult);
	}
	
	/**
	 * PUT mapping to update a SleepResult entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Entity unique identifier
	 * @param sleep The updated data
	 * @return The entity after persistence
	 */
	@PutMapping(path="/sleepResults/{id}")
	SleepResult updateSleepResult(@PathVariable Integer id, @RequestBody SleepResult sleep) {
		
		log.debug("Call to update SleepResult: " + id + " with data: " + sleep);
		
		Optional<SleepResult> optSleep = this.sleepResultRepo.findById(id);
		
		if (optSleep.isEmpty()) {
			throw new NotFoundException("Sleep results for id " + id + " was not found.");
		}
		
		SleepResult sleepResult = optSleep.get();
		sleepResult.setSleepResult(sleep.getSleepResult());
		sleepResult.setPerception(sleep.getPerception());
		
		return this.sleepResultRepo.save(sleepResult);
	}
	
	/**
	 * DELETE mapping to delete a SleepResult entity. Throws and HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Entity unique identifier.
	 */
	@DeleteMapping(path="/sleepResults/{id}")
	void deleteSleepResult(@PathVariable Integer id) {
		
		log.debug("Call to delete SleepResult: " + id);
		
		Optional<SleepResult> sleep = this.sleepResultRepo.findById(id);
		
		if (sleep.isEmpty()) {
			throw new NotFoundException("Sleep results for id " + id + " was not found.");
		}
		
		this.sleepResultRepo.delete(sleep.get());
	}

}
