package com.brenner.sleeptracker.data.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The entity object representing Habit 
 * 
 *
 * @author dbrenner
 *
 */
@Entity
public class Habit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer habitId;
	
	@Column(name="habit", nullable=false, unique=true)
	private String habit;
	
	@Column(name="perception")
	private String perception;
	
	public Habit() {}
	
	public Habit(Integer habitId, String habit) {
		this.habitId = habitId;
		this.habit = habit;
	}

	public Integer getHabitId() {
		return habitId;
	}

	public void setHabitId(Integer habitId) {
		this.habitId = habitId;
	}

	public String getHabit() {
		return habit;
	}

	public void setHabit(String habit) {
		this.habit = habit;
	}

	public String getPerception() {
		return perception;
	}

	public void setPerception(String perception) {
		this.perception = perception;
	}

	@Override
	public int hashCode() {
		return Objects.hash(habit, habitId, perception);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habit other = (Habit) obj;
		return Objects.equals(habit, other.habit) && Objects.equals(habitId, other.habitId)
				&& Objects.equals(perception, other.perception);
	}

	@Override
	public String toString() {
		return "Habit [habitId=" + habitId + ", habit=" + habit + ", perception=" + perception + "]";
	}

}
