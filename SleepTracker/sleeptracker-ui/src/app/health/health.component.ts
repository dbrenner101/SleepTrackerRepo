import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators} from '@angular/forms';

import { Health } from '../objects/Health';
import { HealthHttpService } from './health.http.service';

@Component({
  selector: 'app-health',
  templateUrl: './health.component.html',
  styleUrls: ['./health.component.css']
})
export class HealthComponent implements OnInit {

    allHealth: any = [];
    selectedHealth: Health;

    health = new FormControl("", [Validators.required]);
    perception = new FormControl("", [Validators.required]);

    healthForm = new FormGroup({
        healthtList: new FormControl(""),
        healthId: new FormControl(""),
        health: this.health,
        perception: this.perception
    });

    constructor(private httpClient: HealthHttpService) { }

    ngOnInit(): void {
        this.getAllHealth();
    }

    get f(){
        return this.healthForm.controls;
    }

    getAllHealth() {
        this.httpClient.getAllHealth().subscribe((data: {}) => {
            this.allHealth = data;
        });

        this.clearForm();
    }

    private clearForm() {
        this.healthForm.get("health").setValue("");
        this.healthForm.get("health").setErrors(null);
        this.healthForm.get("perception").setValue("");
        this.healthForm.get("perception").setErrors(null);
    }

    changeHealth(e) {
        let healthId = e.target.value;
        this.selectedHealth = this.allHealth.find(i => i.healthId == healthId);

        this.healthForm.get("health").setValue(this.selectedHealth.health);
        this.healthForm.get("perception").setValue(this.selectedHealth.perception);
    }

    updateHealth() {
        var updatedHealth = {
            healthId: this.selectedHealth.healthId,
            health: this.healthForm.get("health").value,
            perception: this.healthForm.get("perception").value
        }

        this.httpClient.updateHealth(updatedHealth).subscribe(result => {
            this.getAllHealth();
        });
    }

    addHealth() {

        var newHealth = {
            health: this.healthForm.get("health").value,
            perception: this.healthForm.get("perception").value
        };

        this.httpClient.addHealth(newHealth).subscribe(result => {
            this.getAllHealth();
        });
    }

    deleteHealth() {
        this.httpClient.deleteHealth(this.selectedHealth).subscribe(result => {
            this.getAllHealth();
        });
    }

}
