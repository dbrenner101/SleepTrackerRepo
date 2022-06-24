package com.brenner.sleeptracker.data.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity object representing Attitude
 *
 * @author dbrenner
 *
 */
@Entity
@Table(name="Attitude")
public class Attitude {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer attitudeId;
	
	@Column(name="attitude", nullable=false, unique=true)
	private String attitude;
	
	private String perception;
	
	private String description;
	
	public Attitude() {}
	
	public Attitude(Integer attitudeId, String attitude) {
		this.attitudeId = attitudeId;
		this.attitude = attitude;
	}
	
	public Attitude(String attitude, String description) {
		this.attitude = attitude;
		this.description = description;
	}

	public Integer getAttitudeId() {
		return attitudeId;
	}

	public void setAttitudeId(Integer attitudeId) {
		this.attitudeId = attitudeId;
	}

	public String getAttitude() {
		return attitude;
	}

	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPerception() {
		return perception;
	}

	public void setPerception(String perception) {
		this.perception = perception;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attitude, attitudeId, description, perception);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attitude other = (Attitude) obj;
		return Objects.equals(attitude, other.attitude) && Objects.equals(attitudeId, other.attitudeId)
				&& Objects.equals(description, other.description) && Objects.equals(perception, other.perception);
	}

	@Override
	public String toString() {
		return "Attitude [attitudeId=" + attitudeId + ", attitude=" + attitude + ", perception=" + perception
				+ ", description=" + description + "]";
	}
	
	

}
