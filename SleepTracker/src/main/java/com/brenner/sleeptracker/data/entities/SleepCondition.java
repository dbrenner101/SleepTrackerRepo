package com.brenner.sleeptracker.data.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The entity object representing Sleep Condition 
 * 
 *
 * @author dbrenner
 *
 */
@Entity
@Table(name = "sleep_condition")
public class SleepCondition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sleep_condition_id")
	private Integer sleepConditionId;
	
	@Column(name="sleep_condition", nullable=false, unique=true)
	private String sleepCondition;
	
	private String perception;

	public SleepCondition() {}
	
	public SleepCondition(Integer sleepConditionId, String sleepCondition) {
		this.sleepConditionId = sleepConditionId;
		this.sleepCondition = sleepCondition;
	}
	
	public SleepCondition(Integer sleepConditionId, String sleepCondition, String perception) {
		this.sleepConditionId = sleepConditionId;
		this.sleepCondition = sleepCondition;
		this.perception = perception;
	}

	public Integer getSleepConditionId() {
		return sleepConditionId;
	}

	public void setSleepConditionId(Integer sleepConditionId) {
		this.sleepConditionId = sleepConditionId;
	}

	public String getSleepCondition() {
		return sleepCondition;
	}

	public void setSleepCondition(String sleepCondition) {
		this.sleepCondition = sleepCondition;
	}

	public String getPerception() {
		return perception;
	}

	public void setPerception(String perception) {
		this.perception = perception;
	}

	@Override
	public int hashCode() {
		return Objects.hash(perception, sleepCondition, sleepConditionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SleepCondition other = (SleepCondition) obj;
		return Objects.equals(perception, other.perception) && Objects.equals(sleepCondition, other.sleepCondition)
				&& Objects.equals(sleepConditionId, other.sleepConditionId);
	}

	@Override
	public String toString() {
		return "SleepCondition [sleepConditionId=" + sleepConditionId + ", sleepCondition=" + sleepCondition
				+ ", perception=" + perception + "]";
	}

}
