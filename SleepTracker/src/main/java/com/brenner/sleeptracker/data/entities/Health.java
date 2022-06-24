package com.brenner.sleeptracker.data.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The entity object representing Health 
 * 
 *
 * @author dbrenner
 *
 */
@Entity
public class Health {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer healthId;
	
	@Column(name="health", nullable=false, unique=true)
	private String health;
	
	private String perception;

	public Health() {}
	
	public Health(Integer healthId, String health, String perception) {
		this.health = health;
		this.healthId = healthId;
		this.perception = perception;
	}

	public Integer getHealthId() {
		return healthId;
	}

	public void setHealthId(Integer healthId) {
		this.healthId = healthId;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getPerception() {
		return perception;
	}

	public void setPerception(String perception) {
		this.perception = perception;
	}

	@Override
	public int hashCode() {
		return Objects.hash(health, healthId, perception);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Health other = (Health) obj;
		return Objects.equals(health, other.health) && Objects.equals(healthId, other.healthId)
				&& Objects.equals(perception, other.perception);
	}

	@Override
	public String toString() {
		return "Health [healthId=" + healthId + ", health=" + health + ", perception=" + perception + "]";
	}

}
