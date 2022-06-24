package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.SleepResult;

/**
 * JPA repository interface to support SleepResult entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface SleepResultRepository extends JpaRepository<SleepResult, Integer> {

}
