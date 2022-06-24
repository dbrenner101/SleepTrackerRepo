package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.Health;

/**
 * JPA repository interface to support Health entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface HealthRepository extends JpaRepository<Health, Integer> {

}
