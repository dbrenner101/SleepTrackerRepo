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

import com.brenner.sleeptracker.data.HealthRepository;
import com.brenner.sleeptracker.data.entities.Health;

import lombok.extern.slf4j.Slf4j;

/**
 * API supporting the Health entity
 *
 * @author dbrenner
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class HealthRestController {

	@Autowired
	HealthRepository healthRepo;
	
	/**
	 * GET mapping to retrieve all Health entities.
	 * 
	 * @return A List of Health entities - they will be sorted by Health.health
	 */
	@GetMapping(path="/health")
	List<Health> getAllHealth() {
		
		log.debug("Call to get all health");
		
		return this.healthRepo.findAll(Sort.by(Sort.Direction.ASC, "health"));
	}
	
	/**
	 * GET mapping to retrieve a single Health entity
	 * 
	 * @param healthId The entity unique identifier
	 * @return The entity or 404 if it doesn't exist
	 */
	@GetMapping(path="/health/{healthId}")
	Health getHealth(@PathVariable Integer healthId) {
		
		log.debug("Call to get a Health entity with id: " + healthId);
		
		Optional<Health> health = this.healthRepo.findById(healthId);
		
		if (health.isEmpty()) {
			throw new NotFoundException("Health with id " + healthId + " was not found.");
		}
		
		return health.get();
	}
	
	/**
	 * POST mapping to persist a new Health entity
	 * 
	 * @param health The entity data to persist
	 * @return The entity after persistence with the unique identifier
	 */
	@PostMapping("/health")
	Health addNewHealth(@RequestBody Health health) {
		
		log.debug("Call to save a new Health entity: " + health);
		
		return this.healthRepo.save(health);
	}
	
	/**
	 * PUT mapping to update a Health entity.
	 * 
	 * @param healthId Unique identifier for the entity
	 * @param newHealth The entity details to update
	 * @return The updated entity or 404 if it doesn't exist
	 */
	@PutMapping(path="/health/{healthId}")
	Health updateHealth(@PathVariable Integer healthId, @RequestBody Health newHealth) {
		
		log.debug("Call to update a Health entity: " + newHealth);
		
		Optional<Health> optHealth = this.healthRepo.findById(healthId);
		
		if (optHealth.isEmpty()) {
			throw new NotFoundException("Health with id " + healthId + " was not found.");
		}
		
		Health health = optHealth.get();
		health.setHealth(newHealth.getHealth());
		health.setPerception(newHealth.getPerception());
		
		return this.healthRepo.save(health);
	}
	
	/**
	 * DELETE mapping to delete a Health entity. Throws a 404 if the entity doesn't exist 
	 * 
	 * @param healthId Unique identifier for the entity
	 */
	@DeleteMapping(path="/health/{healthId}")
	void deleteHealth(@PathVariable Integer healthId) {
		
		log.debug("Call to delete Health entity identified by: " + healthId);
		
		Optional<Health> health = this.healthRepo.findById(healthId);
		
		if (health.isEmpty()) {
			throw new NotFoundException("Health with id " + healthId + " was not found.");
		}
		
		this.healthRepo.delete(health.get());
	}

}
