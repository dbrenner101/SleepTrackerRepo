package com.brenner.sleeptracker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brenner.sleeptracker.data.entities.UserProfile;

/**
 * JPA repository interface to support UserProfile entities
 *
 * @author dbrenner
 *
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

	/**
	 * Method to support retrieving a UserProfile based on the user (Account) unique identifier
	 * 
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT up.*, u.* FROM user_profile up LEFT JOIN users u on up.user_user_id = u.user_id WHERE up.user_user_id=?1", nativeQuery = true)
	UserProfile findByUserId(Integer userId);
}
