package com.brenner.sleeptracker.data.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.brenner.sleeptracker.common.CommonUtils;
import com.brenner.sleeptracker.data.entities.serialization.JsonDateSerialization;
import com.brenner.sleeptracker.data.entities.serialization.UserProfileDeserializer;
import com.brenner.sleeptracker.data.entities.serialization.UserProfileSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The entity object representing User Profile 
 * 
 *
 * @author dbrenner
 *
 */
@Entity
@Table(name = "user_profile")
@JsonDeserialize(using = UserProfileDeserializer.class)
@JsonSerialize(using = UserProfileSerializer.class)
public class UserProfile {

	@Id()
	@GeneratedValue
	@JsonInclude(Include.ALWAYS)
	private Integer userProfileId;
	
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = JsonDateSerialization.class)
	private Date birthdate;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private Float weight;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name = "target_sleep_hours")
	private Float targetSleepHours;
	
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private Account user;
	
	public UserProfile() {}
	
	public UserProfile(Integer userProfileId, Date birthDate, Gender gender, Float weight, 
			String firstName, String lastName, Float targetSleepHours) {
		this.userProfileId = userProfileId;
		this.birthdate = birthDate;
		this.gender = gender;
		this.weight = weight;
		this.firstName = firstName;
		this.lastName = lastName;
		this.targetSleepHours = targetSleepHours;
	}
	
	public UserProfile(Integer userProfileId, Date birthDate, Gender gender, Float weight, 
			String firstName, String lastName, Float targetSleepHours, Account user) {
		this.userProfileId = userProfileId;
		this.birthdate = birthDate;
		this.gender = gender;
		this.weight = weight;
		this.firstName = firstName;
		this.lastName = lastName;
		this.targetSleepHours = targetSleepHours;
		this.user = user;
	}

	public Integer getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Integer userProfilId) {
		this.userProfileId = userProfilId;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public void setBirthdate(String dateString) {
		
		if (dateString != null && dateString.trim().length() > 0) {
			this.birthdate = CommonUtils.formatCommonDateString(dateString);
		}
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Float getTargetSleepHours() {
		return targetSleepHours;
	}

	public void setTargetSleepHours(Float targetSleepHours) {
		this.targetSleepHours = targetSleepHours;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthdate, firstName, gender, lastName, targetSleepHours, userProfileId, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		return Objects.equals(birthdate, other.birthdate) && Objects.equals(firstName, other.firstName)
				&& gender == other.gender && Objects.equals(lastName, other.lastName)
				&& Objects.equals(targetSleepHours, other.targetSleepHours)
				&& Objects.equals(userProfileId, other.userProfileId) && Objects.equals(weight, other.weight);
	}

	@Override
	public String toString() {
		return "UserProfile [userProfileId=" + userProfileId + ", birthdate=" + birthdate + ", gender=" + gender
				+ ", weight=" + weight + ", firstName=" + firstName + ", lastName=" + lastName + ", targetSleepHours="
				+ targetSleepHours + ", user=" + user + "]";
	}
	

}
