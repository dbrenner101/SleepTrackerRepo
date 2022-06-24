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

import com.brenner.sleeptracker.data.UserProfileRepository;
import com.brenner.sleeptracker.data.entities.UserProfile;

import lombok.extern.slf4j.Slf4j;

/**
 * RESTful API to interface with UserProfile data
 *
 * @author dbrenner
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class UserProfileRestController {
	
	@Autowired
	UserProfileRepository userProfileRepo;
	
	/**
	 * GET mapping to retrieve all userProfiles
	 * 
	 * @return The List of all UserProfile entities
	 */
	@GetMapping(path="/userProfile")
	public List<UserProfile> getAll() {
		
		log.debug("Call to get all UserProfile");
		
		return this.userProfileRepo.findAll();
	}
	
	/**
	 * GET mapping to retrieve a single UserProfile entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Entity unique identifier
	 * @return UserProfile represented by the id
	 */
	@GetMapping(path="/userProfile/{id}")
	public UserProfile getOne(@PathVariable Integer id) {
		
		log.debug("Call to get a UserProfile: " + id);
		
		Optional<UserProfile> optUserProfile = this.userProfileRepo.findById(id);
		
		if (optUserProfile.isEmpty()) {
			throw new NotFoundException("User profile with id " + id + " was not found.");
		}
		
		return optUserProfile.get();
	}
	
	/**
	 * GET mapping to retrieve a UserProfile entity for a specific User. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param userId Unique identifier for the User entity
	 * @return The UserProfile for the User
	 */
	@GetMapping(path="/userProfile/forUser/{userId}")
	public UserProfile getuserProfileByUserId(@PathVariable Integer userId) {
		
		log.debug("Call to get UserProfile for userId: " + userId);
		
		UserProfile userProfile = this.userProfileRepo.findByUserId(userId);
		
		if (userProfile == null) {
			throw new NotFoundException("User profile with user id " + userId + " was not found.");
		}
		
		return userProfile;
	}
	
	/**
	 * POST mapping to add a new UserProfile entity.
	 * 
	 * @param userProfile Entity data to persist
	 * @return The UserProfile with unique identifier after persistence.
	 */
	@PostMapping(path="/userProfile")
	public UserProfile add(@RequestBody UserProfile userProfile) {
		
		log.debug("Call to add a new UserProfile: " + userProfile);
		
		UserProfile savedProfile = this.userProfileRepo.save(userProfile);
		
		return savedProfile;
	}
	
	/**
	 * PUT mapping to update a UserProfile entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Unique identifier for the entity
	 * @param userProfile The updated entity data
	 * @return The entity object after persistence
	 */
	@PutMapping(path = "/userProfile/{id}")
	public UserProfile update(@PathVariable Integer id, @RequestBody UserProfile userProfile) {
		
		log.debug("Call to update UserProfile with id: " + id + " with data: " + userProfile);
		
		Optional<UserProfile> optUserProfile = this.userProfileRepo.findById(id);
		if (optUserProfile.isEmpty()) {
			throw new NotFoundException("User profile with id " + id + " was not found.");
		}
		
		UserProfile newUserProfile = optUserProfile.get();
		newUserProfile.setBirthdate(userProfile.getBirthdate());
		newUserProfile.setFirstName(userProfile.getFirstName());
		newUserProfile.setLastName(userProfile.getLastName());
		newUserProfile.setGender(userProfile.getGender());
		newUserProfile.setWeight(userProfile.getWeight());
		newUserProfile.setTargetSleepHours(newUserProfile.getTargetSleepHours());
		
		return this.userProfileRepo.save(newUserProfile);
	}
	
	/**
	 * DELETE mapping to delete a UserProfile entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id The entity unique identifier
	 */
	@DeleteMapping(path = "/userProfile/{id}")
	public void delete(@PathVariable Integer id) {
		
		Optional<UserProfile> optUserProfile = this.userProfileRepo.findById(id);
		if (optUserProfile.isEmpty()) {
			throw new NotFoundException("User profile with id " + id + " was not found.");
		}
		
		this.userProfileRepo.delete(optUserProfile.get());
	}

}
