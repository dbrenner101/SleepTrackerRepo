import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators} from '@angular/forms';

import { Habit } from '../objects/Habit';
import { HabitsHttpService } from './habits.http.service';

@Component({
  selector: 'app-habits',
  templateUrl: './habits.component.html',
  styleUrls: ['./habits.component.css']
})
export class HabitsComponent implements OnInit {

    habits: any = [];
    selectedHabit: Habit;

    habit = new FormControl("", [Validators.required]);
    perception = new FormControl("", [Validators.required]);

    habitsForm = new FormGroup({
        habitList: new FormControl(""),
        habitId: new FormControl(""),
        habit: this.habit,
        perception: this.perception
    });

    constructor(private httpClient: HabitsHttpService) { }

    ngOnInit(): void {
        this.getAllHabits();
    }

    get f(){
        return this.habitsForm.controls;
    }

    getAllHabits() {
        this.httpClient.getAllHabits().subscribe((data: {}) => {
            this.habits = data;
        });

        this.clearForm();
    }

    private clearForm() {
        this.habitsForm.get("habit").setValue("");
        this.habitsForm.get("habit").setErrors(null);
        this.habitsForm.get("perception").setValue("");
        this.habitsForm.get("perception").setErrors(null);
    }

    changeHabit(e) {
        let habitId = e.target.value;
        this.selectedHabit = this.habits.find(i => i.habitId == habitId);

        this.habitsForm.get("habit").setValue(this.selectedHabit.habit);
        this.habitsForm.get("perception").setValue(this.selectedHabit.perception);
    }

    updateHabit() {
        var updatedHabit = {
            habitId: this.selectedHabit.habitId,
            habit: this.habitsForm.get("habit").value,
            perception: this.habitsForm.get("perception").value
        }

        this.httpClient.updateHabit(updatedHabit).subscribe(result => {
            this.getAllHabits();
        });
    }

    addHabit() {

        var newHabit = {
            habit: this.habitsForm.get("habit").value,
            perception: this.habitsForm.get("perception").value
        };

        this.httpClient.addHabit(newHabit).subscribe(result => {
            this.getAllHabits();
        });
    }

    deleteHabit() {
        this.httpClient.deleteHabit(this.selectedHabit).subscribe(result => {
            this.getAllHabits();
        });
    }

}
