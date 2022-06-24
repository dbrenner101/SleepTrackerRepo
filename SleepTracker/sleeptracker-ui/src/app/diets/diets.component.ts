import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators} from '@angular/forms';

import { Diet } from '../objects/Diet';
import { DietHttpService } from './diet.http.service';

@Component({
  selector: 'app-diets',
  templateUrl: './diets.component.html',
  styleUrls: ['./diets.component.css']
})
export class DietsComponent implements OnInit {

    diets: any = [];
    selectedDiet: Diet;

    diet = new FormControl("", [Validators.required]);
    perception = new FormControl("", [Validators.required]);

    dietsForm = new FormGroup({
        dietList: new FormControl(""),
        dietId: new FormControl(""),
        diet: this.diet,
        perception: this.perception
    });

    constructor(private httpClient: DietHttpService) { }

    ngOnInit(): void {
        this.getAllDiets();
    }

    get f(){
        return this.dietsForm.controls;
    }

    getAllDiets() {
        this.httpClient.getAllDiets().subscribe((data: {}) => {
            this.diets = data;
        });

        this.clearForm();
    }

    private clearForm() {
        this.dietsForm.get("diet").setValue("");
        this.dietsForm.get("diet").setErrors(null);
        this.dietsForm.get("perception").setValue("");
        this.dietsForm.get("perception").setErrors(null);
    }

    changeDiet(e) {
        let dietId = e.target.value;
        this.selectedDiet = this.diets.find(i => i.dietId == dietId);

        this.dietsForm.get("diet").setValue(this.selectedDiet.diet);
        this.dietsForm.get("perception").setValue(this.selectedDiet.perception);
    }

    updateDiet() {
        var updatedDiet = {
            dietId: this.selectedDiet.dietId,
            diet: this.dietsForm.get("diet").value,
            perception: this.dietsForm.get("perception").value
        }

        this.httpClient.updateDiet(updatedDiet).subscribe(result => {
            this.getAllDiets();
        });
    }

    addDiet() {

        var newDiet = {
            diet: this.dietsForm.get("diet").value,
            perception: this.dietsForm.get("perception").value
        };

        this.httpClient.addDiet(newDiet).subscribe(result => {
            this.getAllDiets();
        });
    }

    deleteDiet() {
        this.httpClient.deleteDiet(this.selectedDiet).subscribe(result => {
            this.getAllDiets();
        });
    }

}
