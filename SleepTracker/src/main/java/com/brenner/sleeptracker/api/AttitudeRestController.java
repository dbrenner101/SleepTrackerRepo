package com.brenner.sleeptracker.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.brenner.sleeptracker.data.entities.Attitude;

import lombok.extern.slf4j.Slf4j;

/**
 * API for managing Attitude entities
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost*"})
@Slf4j
public class AttitudeRestController {
	
	@Autowired
	AttitudeRepository attitudeRepo;
	
	/**
	 * API access for the list of stored attitudes. The list will be pre-sorted by Attitude.attitude.
	 * 
	 * @return A List<Attitude> of stored attitudes or null if there are none.
	 */
	@GetMapping("/attitudes")
	List<Attitude> all() {
		
		log.debug("API call to get all attitudes");
		
		return attitudeRepo.findAll(Sort.by(Sort.Direction.ASC, "attitude"));
	}
	
	/**
	 * API entry point to add a new Attitude
	 * 
	 * @param newAttitude The Attitude entity to store
	 * @return The same entity with an Attitude.attitudeId 
	 */
	@PostMapping(path="/attitudes")
	@PreAuthorize("hasAnyAuthority('ROLE_USER')")
	Attitude newAttitude(@RequestBody Attitude newAttitude) {
		
		log.debug("API call to save nw Attitude: " + newAttitude.toString());
		
		return attitudeRepo.save(newAttitude);
	}
	
	/**
	 * API access to retrieve a specific Attitude based on attitudeId.
	 * 
	 * @param id the unique identifier for the Attitude
	 * @return The Attitude associated with the supplied id
	 * @throws NotFoundException (404) if the attitude to update doesn't already exist
	 */
	@GetMapping("/attitudes/{id}")
	Attitude one(@PathVariable Integer id) {
		
		log.debug("API call to get one Attitude with id: " + id);
		
		Optional<Attitude> att = attitudeRepo.findById(id);
		
		if (att.isEmpty()) {
			throw new NotFoundException("Attitude with id " + id + " not found.");
		}
		
		log.debug("Returning Attitude: " + att.get());
		
		return att.get();
	}
	
	/**
	 * API access to update an Attitude entity
	 * 
	 * @param id The unique identifier for the Attitude
	 * @param newAttitude The updated Attitude to persist
	 * @return The updated Attitude 
	 * @throws NotFoundException (404) if the attitude to update doesn't already exist
	 */
	@PutMapping(path="/attitudes/{id}")
	Attitude replaceAttitude(@PathVariable Integer id, @RequestBody Attitude newAttitude) {
		
		log.debug("API call to update Attitude: " + newAttitude.toString());
		
		Optional<Attitude> optAtt = attitudeRepo.findById(id);
		
		if (optAtt.isEmpty()) {
			throw new NotFoundException("Unable to locate Attitude with id " + id + ".");
		}
		
		Attitude att = optAtt.get();
		att.setAttitude(newAttitude.getAttitude());
		att.setDescription(newAttitude.getDescription());
		att.setPerception(newAttitude.getPerception());
		
		return attitudeRepo.save(att);
	}
	
	/**
	 * API access to delete an Attitude
	 * 
	 * @param id the unique identifier for the Attitude
	 * @throws NotFoundException (404) if the attitude to delete doesn't already exist
	 */
	@DeleteMapping(value="/attitudes/{id}")
	void delete(@PathVariable Integer id) {
		
		log.debug("API call to delete Attitude with id: " + id);
		
		if (attitudeRepo.findById(id).isEmpty()) {
			throw new NotFoundException("Attitude with id " + id + " is not found.");
		}
		
		attitudeRepo.deleteById(id);
	}

}
