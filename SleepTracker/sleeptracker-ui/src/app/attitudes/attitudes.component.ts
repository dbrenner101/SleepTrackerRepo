import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { Attitude } from '../objects/Attitude';
import { AttitudesHttpService } from './attitudes-http.service';

@Component({
  selector: 'app-attitudes',
  templateUrl: './attitudes.component.html',
  styleUrls: ['./attitudes.component.css']
})
export class AttitudesComponent implements OnInit {

    attitudes: any = [];
    selectedAttitude: Attitude;
    attitude = new FormControl("", [Validators.required]);
    perception = new FormControl("", [Validators.required]);

    attitudesForm = new FormGroup({
        attitudeList: new FormControl(""),
        attitudeId: new FormControl(""),
        attitude: this.attitude,
        perception: this.perception,
        description: new FormControl("")
    });

    constructor(private httpClient: AttitudesHttpService) { }

    ngOnInit(): void {
        this.getAllAttitudes();
    }

    get f(){
        return this.attitudesForm.controls;
    }

    getAllAttitudes() {
        this.httpClient.getAllAttitudes().subscribe((data: {}) => {
            this.attitudes = data;
            this.clearForm();
        });
    }

    private clearForm() {
        this.attitudesForm.get("attitude").setValue("");
        this.attitudesForm.get("attitude").setErrors(null);
        this.attitudesForm.get("perception").setValue("");
        this.attitudesForm.get("perception").setErrors(null);
    }

    changeAttitude(e) {
        let attId = e.target.value;
        this.selectedAttitude = this.attitudes.find(i => i.attitudeId == attId);

        this.attitudesForm.get("attitude").setValue(this.selectedAttitude.attitude);
        this.attitudesForm.get("description").setValue(this.selectedAttitude.description);
        this.attitudesForm.get("perception").setValue(this.selectedAttitude.perception);
    }

    updateAttitude() {
        var updatedAttitude: Attitude = {
            attitudeId: this.selectedAttitude.attitudeId,
            attitude: this.attitudesForm.get("attitude").value,
            perception: this.attitudesForm.get("perception").value,
            description: this.attitudesForm.get("description").value
        }

        this.httpClient.updateAttitude(updatedAttitude).subscribe(result => {
            this.getAllAttitudes();
            //this.attitudesForm.reset();
        });
    }

    addAttitude() {

        var newAttitude = {
            attitude: this.attitudesForm.get("attitude").value,
            description: this.attitudesForm.get("description").value,
            perception: this.attitudesForm.get("perception").value
        };

        this.httpClient.addAttitude(newAttitude).subscribe(result => {
            this.getAllAttitudes();
            //this.attitudesForm.reset();
        });
    }

    deleteAttitude() {
        this.httpClient.deleteAttitude(this.selectedAttitude).subscribe(result => {
            this.getAllAttitudes();
            this.attitudesForm.reset();
        });
    }

}
