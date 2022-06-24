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

import com.brenner.sleeptracker.data.LocationRepository;
import com.brenner.sleeptracker.data.entities.Location;

import lombok.extern.slf4j.Slf4j;

/**
 * RESTful API to interact with Location entities.
 *
 * @author dbrenner
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class LocationRestController {
	
	@Autowired
	private LocationRepository locationRepo;
	
	/**
	 * GET mapping to retrieve all Location entities.
	 * 
	 * @return A List of Location entities sorted by Location.locationName
	 */
	@GetMapping(path="/locations")
	List<Location> getAll() {
		
		log.debug("Call to get all Locations");
		
		return locationRepo.findAll(Sort.by(Sort.Direction.ASC, "locationName"));
	}
	
	/**
	 * GET mapping to retrieve a single Location entity
	 * 
	 * @param id Entity unique identifier
	 * @return The Location entity or a 404 if it doesn't exist
	 */
	@GetMapping(path="/locations/{id}")
	Location getLocation(@PathVariable Integer id) {
		
		log.debug("Call to get a Location with id: " + id);
		
		Optional<Location> location = locationRepo.findById(id);
		
		if (location.isEmpty()) {
			throw new NotFoundException("Location with id " + id + " was not found.");
		}
		
		return location.get();
	}
	
	/**
	 * POST mapping to add a new Location entity.
	 * 
	 * @param newLocation The Location details to persist
	 * @return The Location entity after persistance with unique identifier
	 */
	@PostMapping(path="/locations")
	Location addLocation(@RequestBody Location newLocation) {
		
		log.debug("Call to add a new Location: " + newLocation);
		
		return locationRepo.save(newLocation);
	}
	
	/**
	 * PUT mapping to update a Location entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Unique identifier for the Location entity
	 * @param newLocation Entity details to update
	 * @return The updated entity
	 */
	@PutMapping(path="/locations/{id}")
	Location updateLocation(@PathVariable Integer id, @RequestBody Location newLocation) {
		
		log.debug("Call to update Location with id: " + id + " with: " + newLocation);
		
		Optional<Location> optLocation = locationRepo.findById(id);
		
		if (optLocation.isEmpty()) {
			throw new NotFoundException("Location with id " + id + " was not found.");
		}
		
		Location location = optLocation.get();
		location.setLocationName(newLocation.getLocationName());
		location.setPerception(newLocation.getPerception());
		
		return locationRepo.save(location);
	}
	
	/**
	 * DELETE mapping to delete Location entity. Throws HTTP 404 if the entity doesn't exist
	 * 
	 * @param id Unique identifier for the entity
	 */
	@DeleteMapping(path="/locations/{id}")
	void deleteLocation(@PathVariable Integer id) {
		
		if (locationRepo.findById(id).isEmpty()) {
			throw new NotFoundException("Location with id " + id + " was not found.");
		}
		
		locationRepo.deleteById(id);
	}
	
	

}
