package com.brenner.sleeptracker.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.brenner.sleeptracker.api.AuthenticationService;

/**
 * Spring security configuration.
 *
 * @author dbrenner
 *
 */
@EnableWebSecurity(debug = false)
public class SecurityConfiguration {
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return this.authenticationService;
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	  return (web) -> web.ignoring().antMatchers("/newUser/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorize -> authorize
					.antMatchers("/newUser/**").permitAll()
					.antMatchers("/index.html").permitAll()
					.antMatchers("/api/**").hasRole("USER"))
			.httpBasic(withDefaults())
			.formLogin(withDefaults())
			.cors().and().csrf().disable();
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}

}
