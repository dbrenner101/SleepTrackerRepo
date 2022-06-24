package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.Diet;

/**
 * JPA repository interface to support Diet entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface DietRepository extends JpaRepository<Diet, Integer> {

}
