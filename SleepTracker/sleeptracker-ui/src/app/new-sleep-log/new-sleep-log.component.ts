import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import * as moment from 'moment';
import { Moment } from 'moment';

import { SleepEvent } from '../objects/SleepEvent';
import { Attitude } from '../objects/Attitude';
import { Location } from '../objects/Location';
import { Diet } from '../objects/Diet';
import { Health } from '../objects/Health';
import { SleepCondition } from '../objects/SleepCondition';

import { SleepLogHttpServiceService } from '../sleeplog/sleep-log-http-service.service';
import { LocationsHttpServiceService } from '../locations/locations-http-service.service';
import { AttitudesHttpService } from '../attitudes/attitudes-http.service';
import { DietHttpService } from '../diets/diet.http.service';
import { HabitsHttpService } from '../habits/habits.http.service';
import { HealthHttpService } from '../health/health.http.service';
import { SleepresulthttpService } from '../sleepresult/sleepresulthttp.service';
import { SleepConditionHttpService } from '../sleepconditions/sleep-condition-http.service';


@Component({
  selector: 'app-new-sleep-log',
  templateUrl: './new-sleep-log.component.html',
  styleUrls: ['./new-sleep-log.component.css']
})
export class NewSleepLogComponent implements OnInit {

    //sleepEvent: SleepEvent;
    selectedDate: Moment;
    selectedSleepEventId: number;

    sleepEventDate = new FormControl("", Validators.required);
    wakeAMPM = new FormControl("AM", [Validators.required]);
    fallAsleepAMPM = new FormControl("PM", [Validators.required]);

    sleepLogForm = new FormGroup({
        sleepEventDate: this.sleepEventDate,
        wakeDate: new FormControl("", [Validators.required]),
        fallAsleepHour: new FormControl("", [Validators.required]),
        fallAsleepMinute: new FormControl("", [Validators.required]),
        fallAsleepAMPM: this.fallAsleepAMPM,
        wakeHour: new FormControl("", [Validators.required]),
        wakeMinute: new FormControl("", [Validators.required]),
        wakeAMPM: this.wakeAMPM,
        locations: new FormControl(""),
        attitudes: new FormControl(""),
        diets: new FormControl(""),
        healths: new FormControl(""),
        habits: new FormControl(""),
        sleepResults: new FormControl(""),
        sleepCondition: new FormControl("")
    });

    locations: any = [];
    attitudes: any = [];
    diets: any = [];
    habits: any = [];
    healths: any = [];
    sleepResults: any = [];
    sleepConditions: any = [];

    selectedSleepLog: SleepEvent;

    constructor(
        private httpClient: SleepLogHttpServiceService,
        private locationsHttpService: LocationsHttpServiceService,
        private attitudesHttpService: AttitudesHttpService,
        private dietHttpService: DietHttpService,
        private habitHttpService: HabitsHttpService,
        private healthHttpService: HealthHttpService,
        private sleepResulsHttpService: SleepresulthttpService,
        private sleepConditionHttpService: SleepConditionHttpService,
        private route: ActivatedRoute,
        private router: Router,
    ) {}

    ngOnInit(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('sleepLogId')!, 10);

       if (id != null && !isNaN(id)) {
        this.httpClient.getSleepEvent(id).subscribe((data: SleepEvent) => {
            this.selectedSleepLog = data;
            this.updateFormValues();
            this.selectedSleepEventId = id;
        });
       }

        this.locationsHttpService.getAllLocations().subscribe((data: {}) => {
            this.locations = data;
        });

        this.attitudesHttpService.getAllAttitudes().subscribe((data: {}) => {
            this.attitudes = data;
        });

        this.dietHttpService.getAllDiets().subscribe((data: {}) => {
            this.diets = data;
        })

        this.habitHttpService.getAllHabits().subscribe((data: {}) => {
            this.habits = data;
        });

        this.healthHttpService.getAllHealth().subscribe((data: {}) => {
            this.healths = data;
        });

        this.sleepResulsHttpService.getAllSleepResults().subscribe((data: {}) => {
            this.sleepResults = data;
        })

        this.sleepConditionHttpService.getAllSleepConditions().subscribe((data: {}) => {
            this.sleepConditions = data;
        });
    }

    get f(){
        return this.sleepLogForm.controls;
    }

    updateFormValues() {

        var sleepDateLong: Moment = moment(this.selectedSleepLog.sleepStartTime);
        var wakeDateLong: Moment = moment(this.selectedSleepLog.wakeTime);

        this.sleepLogForm.get("sleepEventDate").setValue(sleepDateLong);
        this.sleepLogForm.get("fallAsleepHour").setValue(sleepDateLong.format("hh"));
        this.sleepLogForm.get("fallAsleepMinute").setValue(sleepDateLong.format("mm"));
        this.sleepLogForm.get("fallAsleepAMPM").setValue(sleepDateLong.format("A"));


        this.sleepLogForm.get("wakeDate").setValue(wakeDateLong);
        this.sleepLogForm.get("wakeHour").setValue(wakeDateLong.format("hh"));
        this.sleepLogForm.get("wakeMinute").setValue(wakeDateLong.format("mm"));
        this.sleepLogForm.get("wakeAMPM").setValue(wakeDateLong.format("A"));

        if (this.selectedSleepLog.location != null) {
            this.sleepLogForm.get("locations").setValue(this.selectedSleepLog.location.locationId);
        }

        if (this.selectedSleepLog.attitude != null) {
            this.sleepLogForm.get("attitudes").setValue(this.selectedSleepLog.attitude.attitudeId);
        }

        if (this.selectedSleepLog.diet != null) {
            this.sleepLogForm.get("diets").setValue(this.selectedSleepLog.diet.dietId);
        }

        if (this.selectedSleepLog.health != null) {
            this.sleepLogForm.get("healths").setValue(this.selectedSleepLog.health.healthId);
        }

        if (this.selectedSleepLog.sleepCondition != null) {
            this.sleepLogForm.get("sleepCondition").setValue(this.selectedSleepLog.sleepCondition.sleepConditionId);
        }

        if (this.selectedSleepLog.habits != null) {
            var habitIds: any = [];
            for (let i=0; i< this.selectedSleepLog.habits.length; i++) {
                habitIds.push(this.selectedSleepLog.habits[i].habitId);
            }
            this.sleepLogForm.get("habits").setValue(habitIds);
        }

        if (this.selectedSleepLog.sleepResults != null) {
            var sleepResultIds: any = [];
            for (let i=0; i<this.selectedSleepLog.sleepResults.length; i++) {
                sleepResultIds.push(this.selectedSleepLog.sleepResults[i].sleepResultId);
            }
            this.sleepLogForm.get("sleepResults").setValue(sleepResultIds);
        }
    }

    private convertValuesToDate(dateString: string, hoursString: string, minutesString: string, amPmString: string) {

        var dateString = dateString + " " + hoursString + ":" + minutesString + ":00 " + amPmString;
        return moment(dateString, 'MM-DD-YYYY hh:mm A');
    }

    private buildSleepEventFromFormValues() {
        var sleepEventStartDate = this.convertValuesToDate(
            this.sleepLogForm.get("sleepEventDate").value.format("MM-DD-YYYY"),
            this.sleepLogForm.get("fallAsleepHour").value,
            this.sleepLogForm.get("fallAsleepMinute").value,
            this.sleepLogForm.get("fallAsleepAMPM").value
        )

        var wakeDate = this.convertValuesToDate(
            this.sleepLogForm.get("wakeDate").value.format("MM-DD-YYYY"),
            this.sleepLogForm.get("wakeHour").value,
            this.sleepLogForm.get("wakeMinute").value,
            this.sleepLogForm.get("wakeAMPM").value
        );

        var selectedLocation: Location = this.locations.find(i => i.locationId == this.sleepLogForm.get("locations").value);
        var selectedAttitude: Attitude = this.attitudes.find(i => i.attitudeId == this.sleepLogForm.get("attitudes").value);
        var selectedDiet: Diet = this.diets.find(i => i.dietId == this.sleepLogForm.get("diets").value);
        var selectedHealth: Health = this.healths.find(i => i.healthId == this.sleepLogForm.get("healths").value);
        var selectedSleepCondition: SleepCondition = this.sleepConditions.find(i => i.sleepConditionId == this.sleepLogForm.get("sleepCondition").value)

        var selectedHabitIds: any[] = this.sleepLogForm.get("habits").value;
        var selectedHabits = new Array(selectedHabitIds.length);
        for (let i=0; i<selectedHabitIds.length; i++) {
            selectedHabits.push(this.habits.find(x => x.habitId == selectedHabitIds[i]));
        }

        var selectedSleepResultIds: any[] = this.sleepLogForm.get("sleepResults").value;
        var selectedSleepResults = new Array(selectedSleepResultIds.length);
        for (let i=0; i<selectedSleepResultIds.length; i++) {
            selectedSleepResults.push(this.sleepResults.find(x => x.sleepResultId == selectedSleepResultIds[i]));
        }

        var sleepEvent = {
            sleepEventId: this.selectedSleepEventId,
            sleepStartTime: sleepEventStartDate.toDate(),
            wakeTime: wakeDate.toDate(),
            location: selectedLocation,
            attitude: selectedAttitude,
            diet: selectedDiet,
            health: selectedHealth,
            sleepCondition: selectedSleepCondition,
            habits: selectedHabits,
            sleepResults: selectedSleepResults
        };

        return sleepEvent;

    }

    public addSleepEvent() {

        var sleepEvent = this.buildSleepEventFromFormValues();

        this.httpClient.addSleepEvent(sleepEvent).subscribe(result => {
            this.sleepLogForm.reset();
        });
    }

    public updateSleepEvent() {
        var sleepEvent = this.buildSleepEventFromFormValues();

        this.httpClient.updateSleepEvent(sleepEvent).subscribe(result => {
            this.router.navigate(["/edit-sleep-log"])
        });
    }

    public startDateChange(event: MatDatepickerInputEvent<Moment>) {
        if (event.value != null) {
            this.selectedDate = event.value;
        }

        var m: Moment = moment(this.selectedDate);
        m.add(1, 'days');
        this.sleepLogForm.get("wakeDate").setValue(m);
    }

}
