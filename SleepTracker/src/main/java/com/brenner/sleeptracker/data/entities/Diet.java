package com.brenner.sleeptracker.data.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The entity object representing Diet 
 * 
 *
 * @author dbrenner
 *
 */
@Entity
public class Diet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dietId;
	
	@Column(name="diet", nullable=false, unique=true)
	private String diet;
	
	private String perception;
	
	public Diet() {}
	
	public Diet(Integer dietId, String diet) {
		this.diet = diet;
		this.dietId = dietId;
	}

	public Integer getDietId() {
		return dietId;
	}

	public void setDietId(Integer dietId) {
		this.dietId = dietId;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getPerception() {
		return perception;
	}

	public void setPerception(String perception) {
		this.perception = perception;
	}

	@Override
	public int hashCode() {
		return Objects.hash(diet, dietId, perception);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diet other = (Diet) obj;
		return Objects.equals(diet, other.diet) && Objects.equals(dietId, other.dietId)
				&& Objects.equals(perception, other.perception);
	}

	@Override
	public String toString() {
		return "Diet [dietId=" + dietId + ", diet=" + diet + ", perception=" + perception + "]";
	}

}
