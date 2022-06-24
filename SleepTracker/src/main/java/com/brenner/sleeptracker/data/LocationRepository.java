package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.Location;

/**
 * JPA repository interface to support Location entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
