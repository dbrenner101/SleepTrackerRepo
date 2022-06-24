package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.Account;

/**
 * JPA repository interface to support Account entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Account findByUsername(String username);
}
