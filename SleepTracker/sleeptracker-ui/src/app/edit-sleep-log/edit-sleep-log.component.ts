import { Component, OnInit } from '@angular/core';
import { SleepLogHttpServiceService } from '../sleeplog/sleep-log-http-service.service';

@Component({
  selector: 'app-edit-sleep-log',
  templateUrl: './edit-sleep-log.component.html',
  styleUrls: ['./edit-sleep-log.component.css']
})
export class EditSleepLogComponent implements OnInit {

    sleepLogEvents: any = [];

    constructor(private sleepLogHttpService: SleepLogHttpServiceService) { }

    ngOnInit(): void {
        this.getAllLogEvents();
    }

    public getAllLogEvents(): void {
        this.sleepLogHttpService.getAllSleepEvents().subscribe((data: {}) => {
            this.sleepLogEvents = data;
        });
    }

    public deleteSleepEvent(sleepEventId): void {
        this.sleepLogHttpService.deleteSleepEventById(sleepEventId).subscribe((data: {}) => {
            this.getAllLogEvents();
        });
    }

}
