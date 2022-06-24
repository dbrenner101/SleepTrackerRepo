package com.brenner.sleeptracker.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brenner.sleeptracker.data.AccountRepository;
import com.brenner.sleeptracker.data.entities.Account;

import lombok.extern.slf4j.Slf4j;

/**
 * API entry point for adding a new authorized account. This call is isolated from other user calls.
 * 
 * @see UserRestController
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@Slf4j
public class AuthenticationRestController {

	@Autowired
	AccountRepository userRepo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	/**
	 * Unsecured entry point for registering a new user
	 * 
	 * @param newUser The account information to register
	 * @return The saved user account information.
	 * @throws DuplicateUsernameException A 406 error will be returned if the username already exists
	 */
	@PostMapping(path="/newUser", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Account> newUser(@RequestBody Account newUser)  throws DuplicateUsernameException {
		
		log.debug("Creating a new user");
		
		Account existingAccount = this.userRepo.findByUsername(newUser.getUsername());
		
		if (existingAccount != null) {
			throw new DuplicateUsernameException("Username " + newUser.getUsername() + " is unavailable.");
		}
		
		newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
		
		Account u = userRepo.save(newUser);
		
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

}
