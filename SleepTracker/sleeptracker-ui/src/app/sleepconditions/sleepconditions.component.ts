import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators} from '@angular/forms';

import { SleepCondition } from '../objects/SleepCondition';
import { SleepConditionHttpService } from './sleep-condition-http.service';

@Component({
  selector: 'app-sleepconditions',
  templateUrl: './sleepconditions.component.html',
  styleUrls: ['./sleepconditions.component.css']
})
export class SleepconditionsComponent implements OnInit {

    sleepConditions: any = [];
    selectedSleepCondition: SleepCondition;

    sleepCondition = new FormControl("", [Validators.required]);
    perception = new FormControl("", [Validators.required]);

    sleepConditionsForm = new FormGroup({
        sleepConditionList: new FormControl(""),
        sleepConditionId: new FormControl(""),
        sleepCondition: this.sleepCondition,
        perception: this.perception
    });

    constructor(private httpClient: SleepConditionHttpService) { }

    ngOnInit(): void {
        this.getAllSleepConditions();
    }

    get f(){
        return this.sleepConditionsForm.controls;
    }

    getAllSleepConditions() {
        this.httpClient.getAllSleepConditions().subscribe((data: {}) => {
            this.sleepConditions = data;
        });

        this.clearForm();
    }

    private clearForm() {
        this.sleepConditionsForm.get("sleepCondition").setValue("");
        this.sleepConditionsForm.get("sleepCondition").setErrors(null);
        this.sleepConditionsForm.get("perception").setValue("");
        this.sleepConditionsForm.get("perception").setErrors(null);
    }

    changeSleepCondition(e) {
        let sleepConditionId = e.target.value;
        this.selectedSleepCondition = this.sleepConditions.find(i => i.sleepConditionId == sleepConditionId);

        this.sleepConditionsForm.get("sleepCondition").setValue(this.selectedSleepCondition.sleepCondition);
        this.sleepConditionsForm.get("perception").setValue(this.selectedSleepCondition.perception);
    }

    updateSleepCondition() {
        var updatedSleepCondition = {
            sleepConditionId: this.selectedSleepCondition.sleepConditionId,
            sleepCondition: this.sleepConditionsForm.get("sleepCondition").value,
            perception: this.sleepConditionsForm.get("perception").value
        }

        this.httpClient.updateSleepCondition(updatedSleepCondition).subscribe(result => {
            this.getAllSleepConditions();
        });
    }

    addSleepCondition() {

        var newSleepCondition = {
            sleepCondition: this.sleepConditionsForm.get("sleepCondition").value,
            perception: this.sleepConditionsForm.get("percption").value
        };

        this.httpClient.addSleepCondition(newSleepCondition).subscribe(result => {
            this.getAllSleepConditions();
        });
    }

    deleteSleepCondition() {
        this.httpClient.deleteSleepCondition(this.selectedSleepCondition).subscribe(result => {
            this.getAllSleepConditions();
        });
    }

}
