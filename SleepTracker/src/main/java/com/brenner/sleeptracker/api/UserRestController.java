package com.brenner.sleeptracker.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brenner.sleeptracker.data.AccountRepository;
import com.brenner.sleeptracker.data.entities.Account;

import lombok.extern.slf4j.Slf4j;

/**
 * RESTful API to interact with User (Account) entities. Also supports authentication requests.
 *
 * @author dbrenner
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class UserRestController {

	@Autowired
	AccountRepository userRepo;
	
	@Autowired
	AuthenticationService authService;
	
	/**
	 * GET mapping target for new authentications
	 * 
	 * @param user The Spring User Principal
	 * @return The Principal
	 */
	@GetMapping("/auth")
	@PreAuthorize(value = "ROLE_USER")
	public Account authenticate(@CurrentSecurityContext SecurityContext securityContext) {
		
		Object user = securityContext.getAuthentication().getPrincipal(); 
		
		log.debug("Call to /api/auth by: " + user);
		
		Account a = new Account();
		a.setUsername(user.toString());
		
		return a;
	}
	
	/**
	 * GET mapping to retrieve all Account entities. 
	 * 
	 * @return The list of accounts.
	 */
	@GetMapping("/users")
	public List<Account> all() {
		
		log.debug("Call to get all Accounts");
		
		return userRepo.findAll();
	}
	
	/**
	 * GET mapping to retrieve a specific Account entity. Throws an HTTP 404 if the entity doesn't exist. 
	 * 
	 * @param id Entity unique identifier
	 * @return The Account associate with the supplied id
	 */
	@GetMapping("/users/{id}")
	public Account one(@PathVariable Integer id) {
		
		log.debug("Call to get Account with id: " + id);
		
		Optional<Account> user = userRepo.findById(id);
		
		if (user.isEmpty()) {
			throw new NotFoundException("User not found.");
		}
		
		return user.get();
	}
	
	/**
	 * PUT mapping to update an Account entity. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Entity unique identifier
	 * @param newUser Account data updates 
	 * @return The updated entity after persistence
	 */
	@PutMapping(path="/users/{id}", consumes={"application/json"})
	public Account replaceUser(@PathVariable Integer id, @RequestBody Account newUser) {
		
		log.debug("Call to update Account with id: " + id + " with date: " + newUser);
		
		Optional<Account> optionalRecord = userRepo.findById(id);
		
		if (optionalRecord == null ||optionalRecord.isEmpty()) {
			throw new NotFoundException("User record not found");
		}
		
		Account existingUser = optionalRecord.get();
		existingUser.setPassword(newUser.getPassword());
		existingUser.setUsername(newUser.getUsername());
		
		return userRepo.save(existingUser);
	}
	
	/**
	 * DELETE mapping to remove an Account. Throws an HTTP 404 if the entity doesn't exist.
	 * 
	 * @param id Account unique identifier
	 */
	@DeleteMapping(value="/users/{id}")
	public void deleteUser (@PathVariable Integer id) {
		
		log.debug("Call to delete Account with id: " + id);
		
		if (userRepo.findById(id).isEmpty()) {
			throw new NotFoundException("User does not exist.");
		}
		
		userRepo.deleteById(id);
	}
}
