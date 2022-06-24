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

import com.brenner.sleeptracker.data.DietRepository;
import com.brenner.sleeptracker.data.entities.Diet;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * API to support Diet entities. Has support for GET/POST/PUT/DELETE
 *
 * @author dbrenner
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class DietRestController {
	
	@Autowired
	DietRepository dietRepo;
	
	/**
	 * API GET entry point to retrieve a list of diet entities
	 * 
	 * @return The List of diet entities which will be sorted by diet
	 */
	@GetMapping(path="/diets")
	public List<Diet> getAll() {
		
		log.debug("API call to get all Diest");
		
		return this.dietRepo.findAll(Sort.by(Sort.Direction.ASC, "diet"));
	}
	
	/**
	 * GET mapping for a single Diet entity
	 * 
	 * @param dietId Unique identifier for a Diet entity
	 * @return
	 */
	@GetMapping(path="/diets/{dietId}")
	public Diet getDiet(@PathVariable Integer dietId) {
		
		log.debug("API call to get one Diet with id: " + dietId);
		
		Optional<Diet> diet = this.dietRepo.findById(dietId);
		
		if (diet.isEmpty()) {
			throw new NotFoundException("Diet with id " + dietId + " was not found");
		}
		
		log.debug("Returning Diet: " + diet.get());
		
		return diet.get();
	}
	
	/**
	 * POST mapping to add a new Diet entry
	 * 
	 * @param diet The entity details to persist
	 * @return The new Diet entity after persistence with unique identifier
	 */
	@PostMapping(path="/diets")
	public Diet addDiet(@RequestBody Diet diet) {
		
		log.debug("API request to save new Diet: " + diet);
		
		return this.dietRepo.save(diet);
	}
	
	/**
	 * PUT mapping to update a Diet entity. This will return a 404 error if the Diet to update is not found.
	 * 
	 * @param dietId Unique identifier for the Diet entity
	 * @param newDiet The updated Diet entity details to persist
	 * @return The updated Diet entity
	 */
	@PutMapping(path="/diets/{dietId}")
	public Diet updateDiet(@PathVariable Integer dietId, @RequestBody Diet newDiet) {
		
		log.debug("API call to update Diet: " + newDiet);
		
		Optional<Diet> optDiet = this.dietRepo.findById(dietId);
		
		if (optDiet.isEmpty()) {
			throw new NotFoundException("Diet with id " + dietId + " was not found.");
		}
		
		Diet diet = optDiet.get();
		diet.setDiet(newDiet.getDiet());
		diet.setPerception(newDiet.getPerception());
		return this.dietRepo.save(diet);
	}
	
	/**
	 * DELETE mapping to remove a Diet entity. Will return a 404 if the entity is not found.
	 * 
	 * @param dietId Unique identifier for the entity
	 */
	@DeleteMapping(path="/diets/{dietId}")
	public void deleteDiet(@PathVariable Integer dietId) {
		
		log.debug("API call to delete Diet with id: " + dietId);
		
		Optional<Diet> diet = this.dietRepo.findById(dietId);
		if (diet.isEmpty()) {
			throw new NotFoundException("Diet with id " + dietId + " was not found.");
		}
		
		this.dietRepo.delete(diet.get());
	}

}
