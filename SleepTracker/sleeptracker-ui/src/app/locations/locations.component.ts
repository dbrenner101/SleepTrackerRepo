import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';

import { Location } from '../objects/Location';
import { LocationsHttpServiceService } from './locations-http-service.service';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.css']
})
export class LocationsComponent implements OnInit {

    locations: any = [];
    locationName: string;
    locationId: number;
    selectedLocation: Location = null;

    location = new FormControl("", [Validators.required]);
    perception = new FormControl("", [Validators.required]);

    locationsListForm = new FormGroup({
        locationList: new FormControl(""),
        location: this.location,
        perception: this.perception
    });

    constructor(private  httpService: LocationsHttpServiceService, private formBuilder: FormBuilder) { }

    ngOnInit(): void {
        this.getAllLocations();
    }

    get f(){
        return this.locationsListForm.controls;
    }

    updateLocation() {
        var updatedLocation: Location = {
            locationName: this.locationsListForm.get("location").value,
            locationId: this.locationsListForm.get("locationList").value,
            perception: this.locationsListForm.get("perception").value
        }

        this.httpService.updateLocation(updatedLocation).subscribe(result => {
            this.getAllLocations();
        });
    }

    addLocation() {
        var newLocation: Location = {
            locationName: this.locationsListForm.get("location").value,
            perception: this.locationsListForm.get("perception").value
        }

        this.httpService.addLocation(newLocation).subscribe(result => {
            this.getAllLocations();
        });
    }

    deleteLocation() {
        var deleteLocation: Location = {
            locationName: this.locationsListForm.get("location").value,
            locationId: this.locationsListForm.get("locationList").value,
            perception: this.locationsListForm.get("perception").value
        }
        this.httpService.deleteLocation(deleteLocation).subscribe(result => {
            this.getAllLocations();
        });
    }

    changeLocation(e) {
        if (location != null) {
            let locationId = e.target.value;
            this.selectedLocation = this.locations.find(i => i.locationId == locationId);

            this.locationsListForm.get("location").setValue(this.selectedLocation.locationName);
            this.locationsListForm.get("perception").setValue(this.selectedLocation.perception);
        }
    }

    getAllLocations() {
        this.httpService.getAllLocations().subscribe((data: {}) => {
            this.locations = data;
        });

        this.clearForm();
    }

    private clearForm() {
        this.locationsListForm.get("location").setValue("");
        this.locationsListForm.get("location").setErrors(null);
        this.locationsListForm.get("perception").setValue("");
        this.locationsListForm.get("perception").setErrors(null);
    }
}
