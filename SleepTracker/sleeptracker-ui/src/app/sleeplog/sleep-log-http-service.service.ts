import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { SleepEvent } from '../objects/SleepEvent';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class SleepLogHttpServiceService {

    sleepEventsBaseUrl = environment.apiBaseUrl + "/api/sleepEvents";

    constructor(private httpClient: HttpClient) { }

    public getSleepEvent(sleepEventId: number): Observable<SleepEvent> {
        var sleepEventGetUrl = this.sleepEventsBaseUrl + "/" + sleepEventId;
        return this.httpClient.get<SleepEvent>(sleepEventGetUrl, httpOptions);
    }

    public getAllSleepEvents(): Observable<SleepEvent[]> {
        return this.httpClient.get<SleepEvent[]>(this.sleepEventsBaseUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public updateSleepEvent(sleepEvent: SleepEvent): Observable<any> {
        var updateSleepEventUrl = this.sleepEventsBaseUrl + "/" + sleepEvent.sleepEventId;
        return this.httpClient.put<SleepEvent>(updateSleepEventUrl, sleepEvent, httpOptions)
            .pipe(catchError(this.processError));
    }

    public addSleepEvent(sleepEvent: SleepEvent): Observable<any> {
        return this.httpClient.post<SleepEvent>(this.sleepEventsBaseUrl, sleepEvent, httpOptions)
            .pipe(catchError(this.processError));
    }

    public deleteSleepEvent(sleepEvent: SleepEvent): Observable<any> {
        var deleteSleepEventUrl = this.sleepEventsBaseUrl + "/" + sleepEvent.sleepEventId;
        return this.httpClient.delete<SleepEvent>(deleteSleepEventUrl, httpOptions)
        .pipe(catchError(this.processError));
    }

    public deleteSleepEventById(sleepEventId: number): Observable<any> {
        var deleteSleepEventUrl = this.sleepEventsBaseUrl + "/" + sleepEventId;
        return this.httpClient.delete<SleepEvent>(deleteSleepEventUrl, httpOptions)
        .pipe(catchError(this.processError));
    }

    processError(err: any) {
        let message = '';
        if (err.error instanceof ErrorEvent) {
            message = err.error.message;
        } else {
            message = `Error Code: ${err.status}\nMessage: ${err.message}`;
        }
        console.log(message);
        return throwError(() => {
            message;
        });
    }
}
