package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.SleepCondition;

/**
 * JPA repository interface to support SleepCondition entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface SleepConditionRepo extends JpaRepository<SleepCondition, Integer> {

}
