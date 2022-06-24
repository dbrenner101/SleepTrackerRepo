package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.Attitude;

/**
 * JPA repository interface for Attitude entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface AttitudeRepository extends JpaRepository<Attitude, Integer> {

}
