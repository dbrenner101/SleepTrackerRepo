package com.brenner.sleeptracker.data.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The entity object representing Sleep Result 
 * 
 *
 * @author dbrenner
 *
 */
@Entity
public class SleepResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sleep_result_id")
	private Integer sleepResultId;
	
	@Column(name="sleep_result", nullable=false, unique=true)
	private String sleepResult;
	
	private String perception;

	public SleepResult() {}
	
	public SleepResult(Integer sleepResultId, String sleepResult, String perception) {
		this.sleepResultId = sleepResultId;
		this.sleepResult = sleepResult;
		this.perception = perception;
	}

	public Integer getSleepResultId() {
		return sleepResultId;
	}

	public void setSleepResultId(Integer sleepResultId) {
		this.sleepResultId = sleepResultId;
	}

	public String getSleepResult() {
		return sleepResult;
	}

	public void setSleepResult(String sleepResult) {
		this.sleepResult = sleepResult;
	}

	public String getPerception() {
		return perception;
	}

	public void setPerception(String perception) {
		this.perception = perception;
	}

	@Override
	public int hashCode() {
		return Objects.hash(perception, sleepResult, sleepResultId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SleepResult other = (SleepResult) obj;
		return Objects.equals(perception, other.perception) && Objects.equals(sleepResult, other.sleepResult)
				&& Objects.equals(sleepResultId, other.sleepResultId);
	}

	@Override
	public String toString() {
		return "SleepResult [sleepResultId=" + sleepResultId + ", sleepResult=" + sleepResult + ", perception="
				+ perception + "]";
	}
	
	

}
