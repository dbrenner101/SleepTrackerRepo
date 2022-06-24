package com.brenner.sleeptracker.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.Role;
import com.brenner.sleeptracker.security.RoleEnum;

/**
 * JPA repository interface to support Role entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(RoleEnum name);

}
