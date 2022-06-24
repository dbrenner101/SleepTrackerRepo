package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.SleepEvent;

/**
 * JPA repository interface to support SleepEvent entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface SleepEventRepo extends JpaRepository<SleepEvent, Long> {

}
