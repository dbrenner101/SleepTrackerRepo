import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators} from '@angular/forms';

import { SleepResult } from '../objects/SleepResult';
import { SleepresulthttpService } from './sleepresulthttp.service';

@Component({
  selector: 'app-sleepresult',
  templateUrl: './sleepresult.component.html',
  styleUrls: ['./sleepresult.component.css']
})
export class SleepresultComponent implements OnInit {

    sleepResults: any = [];
    selectedSleepResult: SleepResult;

    sleepResult = new FormControl("", [Validators.required]);
    perception = new FormControl("", [Validators.required]);

    sleepResultsForm = new FormGroup({
        sleepResultList: new FormControl(""),
        sleepResultId: new FormControl(""),
        sleepResult: this.sleepResult,
        perception: this.perception
    });

    constructor(private httpClient: SleepresulthttpService) { }

    ngOnInit(): void {
        this.getAllSleepResults();
    }

    get f(){
        return this.sleepResultsForm.controls;
    }

    getAllSleepResults() {
        this.httpClient.getAllSleepResults().subscribe((data: {}) => {
            this.sleepResults = data;
        });

        this.clearForm();
    }

    private clearForm() {
        this.sleepResultsForm.get("sleepResult").setValue("");
        this.sleepResultsForm.get("sleepResult").setErrors(null);
        this.sleepResultsForm.get("perception").setValue("");
        this.sleepResultsForm.get("perception").setErrors(null);
    }

    changeSleepResult(e) {
        let sleepResultId = e.target.value;
        this.selectedSleepResult = this.sleepResults.find(i => i.sleepResultId == sleepResultId);

        this.sleepResultsForm.get("sleepResult").setValue(this.selectedSleepResult.sleepResult);
        this.sleepResultsForm.get("perception").setValue(this.selectedSleepResult.perception);
    }

    updateSleepResult() {
        var updatedSleepResult = {
            sleepResultId: this.selectedSleepResult.sleepResultId,
            sleepResult: this.sleepResultsForm.get("sleepResult").value,
            perception: this.sleepResultsForm.get("perception").value
        }

        this.httpClient.updateSleepResult(updatedSleepResult).subscribe(result => {
            this.getAllSleepResults();
        });
    }

    addSleepResult() {

        var newSleepResult = {
            sleepResult: this.sleepResultsForm.get("sleepResult").value,
            perception: this.sleepResultsForm.get("perception").value
        };

        this.httpClient.addSleepResult(newSleepResult).subscribe(result => {
            this.getAllSleepResults();
        });
    }

    deleteSleepResult() {
        this.httpClient.deleteSleepResult(this.selectedSleepResult).subscribe(result => {
            this.getAllSleepResults();
        });
    }

}
