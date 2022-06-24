package com.brenner.sleeptracker.api;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brenner.sleeptracker.data.AccountRepository;
import com.brenner.sleeptracker.data.entities.Account;
import com.brenner.sleeptracker.security.SecurityConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * Authentication service to support Spring Security call authentication. This validates that the user credentials exist.
 * 
 * This class implements the Spring UserDetailsService and plugs into Spring Security for authentication
 * 
 *  @see SecurityConfiguration
 *
 */
@Service
@Slf4j
public class AuthenticationService implements UserDetailsService, AuthenticationManager {
	
	@Autowired
	private AccountRepository userRepo;

	/**
	 * The authentication method to validate the username exists before credentials are assessed
	 */
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.debug("Looking up user: " + username);
		
		Account account = userRepo.findByUsername(username);
		
		if (account == null) {
			throw new UsernameNotFoundException(username + " is not valid.");
		}
		
		log.debug("User found");
		
		User user = createUser(account);
		
		return user;
	}
	
	/**
	 * Helper method to create a Spring User object.
	 * 
	 * @param u User account information
	 * @return Spring User 
	 */
	public User createUser(Account u) {
		
        return new User(u.getUsername(), u.getPassword(), createAuthorities(u));
    }
	
	
	private Collection<GrantedAuthority> createAuthorities(Account u) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return  authorities;
    }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User u = loadUserByUsername(authentication.getName());
		
		if (u != null) {
			return new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities());
		}
		return null;
	}

}
