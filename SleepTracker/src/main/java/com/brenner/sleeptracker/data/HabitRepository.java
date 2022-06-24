package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.Habit;

/**
 * JPA repository interface to support Habit entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface HabitRepository extends JpaRepository<Habit, Integer> {

}
