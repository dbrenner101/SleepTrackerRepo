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

import com.brenner.sleeptracker.data.HabitRepository;
import com.brenner.sleeptracker.data.entities.Habit;

import lombok.extern.slf4j.Slf4j;

/**
 * API supporting interactions on the Habit entity. All REST verbs supported.
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class HabitRestController {
	
	@Autowired
	HabitRepository habitRepo;
	
	/**
	 * Access to all Habit entities. 
	 * 
	 * @return All Habit entities sorted by habit
	 */
	@GetMapping(path="/habits")
	public List<Habit> getAll() {
		
		log.debug("API call to get all Habits");
		
		return this.habitRepo.findAll(Sort.by(Sort.Direction.ASC, "habit"));
	}
	
	/**
	 * Call to retrieve a specific Habit. Will return a 404 if the requested Habit id is not found.
	 * 
	 * @param habitId Unique identifier for the Habit
	 * @return The Habit
	 */
	@GetMapping(path="/habits/{habitId}")
	public Habit getHabit(@PathVariable Integer habitId) {
		
		log.debug("API call to get a Habit with id: " + habitId);
		
		Optional<Habit> habit = this.habitRepo.findById(habitId);
		
		if (habit.isEmpty()) {
			throw new NotFoundException("Habit with id " + habitId + " was not found.");
		}
		
		log.debug("Returning: " + habit.get());
		
		return habit.get();
	}
	
	/**
	 * Access to save a new Habit
	 * 
	 * @param habit The new Habit entity to save
	 * @return The saved entity with id
	 */
	@PostMapping(path="/habits")
	public Habit addHabit(@RequestBody Habit habit) {
		
		log.debug("API call to save a new Habit: " + habit);
		
		return this.habitRepo.save(habit);
	}
	
	/**
	 * Supports updating the habit details. If the entity does not exists will return 404
	 * 
	 * @param habitId Entity unique identifier
	 * @param newHabit The updated entity
	 * @return The entity with updates after persistence
	 */
	@PutMapping(path="/habits/{habitId}")
	public Habit updateHabit(@PathVariable Integer habitId, @RequestBody Habit newHabit) {
		
		log.debug("API call to update Habit: " + newHabit);
		
		Optional<Habit> optHabit = this.habitRepo.findById(habitId);
		
		if (optHabit.isEmpty()) {
			throw new NotFoundException("Habit with id " + habitId + " was not found.");
		}
		
		Habit habit = optHabit.get();
		habit.setHabit(newHabit.getHabit());
		habit.setPerception(newHabit.getPerception());
		
		return this.habitRepo.save(habit);
	}
	
	/**
	 * Supports deleting a Habit entity. Will return a 404 if the entity does not exist.
	 * 
	 * @param habitId The entity unique identifier.
	 */
	@DeleteMapping(path="/habits/{habitId}")
	public void deleteHabit(@PathVariable Integer habitId) {
		if (habitId == null) {
			throw new InvalidRequestException("Habit id must be non-null.");
		}
		
		Optional<Habit> optHabit = this.habitRepo.findById(habitId);
		
		if (optHabit.isEmpty()) {
			throw new NotFoundException("Habit with id " + habitId + " was not found.");
		}
		
		this.habitRepo.delete(optHabit.get());
		
	}

}
