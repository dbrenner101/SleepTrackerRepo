package com.brenner.sleeptracker.data.entities;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The entity object representing Sleep Event 
 * 
 *
 * @author dbrenner
 *
 */
@Entity
@Table(name="sleep_event")
public class SleepEvent {

	@Id
	@GeneratedValue
	private Long sleepEventId;
	
	@Column(name = "sleep_start_time", nullable=false)
	private Date sleepStartTime;
	
	@Column(name = "wakeTime", nullable=false)
	private Date wakeTime;
	
	@Transient
	private String sleepLength;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Location location;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attitudeId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Attitude attitude;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sleepConditionId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private SleepCondition sleepCondition;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dietId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Diet diet;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "healthId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Health health;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "sleep_event_to_habits_mapping", 
    	joinColumns = { 
    			@JoinColumn(name = "sleep_event_id", unique = false, nullable = false) 
    		}, inverseJoinColumns = { @JoinColumn(name = "habit_id", unique = false, nullable = false) })
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Habit> habits;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "sleep_event_to_sleep_results_mapping", 
    	joinColumns = { 
    			@JoinColumn(name = "sleep_event_id", unique = false, nullable = false) 
    		}, inverseJoinColumns = { @JoinColumn(name = "sleep_result_id", unique = false, nullable = false) })
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<SleepResult> sleepResults;
	
	public SleepEvent() {}
	
	public SleepEvent(Long sleepEventId, Date sleepStartTime, Date wakeTime) {
		this.sleepEventId = sleepEventId;
		this.sleepStartTime = sleepStartTime;
		this.wakeTime = wakeTime;
	}

	public Long getSleepEventId() {
		return sleepEventId;
	}

	public void setSleepEventId(Long sleepEventId) {
		this.sleepEventId = sleepEventId;
	}

	public Date getSleepStartTime() {
		return sleepStartTime;
	}

	public void setSleepStartTime(Date sleepStartTime) {
		this.sleepStartTime = sleepStartTime;
	}

	public Date getWakeTime() {
		return wakeTime;
	}

	public void setWakeTime(Date wakeTime) {
		this.wakeTime = wakeTime;
	}

	public String getSleepLength() {
		if (this.getSleepStartTime() != null && this.getWakeTime() != null) {
			long secs = (this.getWakeTime().getTime() - this.getSleepStartTime().getTime())/1000;
			
			long hours = secs / 3600;
			
			return Long.toString(hours);
		}
		return null;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Attitude getAttitude() {
		return attitude;
	}

	public void setAttitude(Attitude attitude) {
		this.attitude = attitude;
	}

	public SleepCondition getSleepCondition() {
		return sleepCondition;
	}

	public void setSleepCondition(SleepCondition sleepCondition) {
		this.sleepCondition = sleepCondition;
	}

	public Diet getDiet() {
		return diet;
	}

	public void setDiet(Diet diet) {
		this.diet = diet;
	}

	public Health getHealth() {
		return health;
	}

	public void setHealth(Health health) {
		this.health = health;
	}

	public List<Habit> getHabits() {
		return habits;
	}

	public void setHabits(List<Habit> habits) {
		this.habits = habits;
	}

	public List<SleepResult> getSleepResults() {
		return sleepResults;
	}

	public void setSleepResults(List<SleepResult> sleepResults) {
		this.sleepResults = sleepResults;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attitude, diet, habits, health, location, sleepCondition, sleepEventId, sleepResults,
				sleepStartTime, wakeTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SleepEvent other = (SleepEvent) obj;
		return Objects.equals(attitude, other.attitude) && Objects.equals(diet, other.diet)
				&& Objects.equals(habits, other.habits) && Objects.equals(health, other.health)
				&& Objects.equals(location, other.location) && Objects.equals(sleepCondition, other.sleepCondition)
				&& Objects.equals(sleepEventId, other.sleepEventId) && Objects.equals(sleepResults, other.sleepResults)
				&& Objects.equals(sleepStartTime, other.sleepStartTime) && Objects.equals(wakeTime, other.wakeTime);
	}

	@Override
	public String toString() {
		return "SleepEvent [sleepEventId=" + sleepEventId + ", sleepStartTime=" + sleepStartTime + ", wakeTime="
				+ wakeTime + ", location=" + location + ", attitude=" + attitude + ", sleepCondition=" + sleepCondition
				+ ", diet=" + diet + ", health=" + health + ", habits=" + habits + ", sleepResults=" + sleepResults
				+ "]";
	}

}
