import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { SleepCondition } from '../objects/SleepCondition';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class SleepConditionHttpService {

    sleepConditionsBaseUrl = environment.apiBaseUrl + "/api/sleepConditions";

    constructor(private httpClient: HttpClient) { }

    public getAllSleepConditions(): Observable<SleepCondition[]> {
        return this.httpClient.get<SleepCondition[]>(this.sleepConditionsBaseUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public updateSleepCondition(sleepCondition: SleepCondition): Observable<any> {
        var updateSleepConditionUrl = this.sleepConditionsBaseUrl + "/" + sleepCondition.sleepConditionId;
        return this.httpClient.put<SleepCondition>(updateSleepConditionUrl, sleepCondition, httpOptions)
            .pipe(catchError(this.processError));
    }

    public addSleepCondition(sleepCondition: SleepCondition): Observable<any> {
        return this.httpClient.post<SleepCondition>(this.sleepConditionsBaseUrl, sleepCondition, httpOptions)
            .pipe(catchError(this.processError));
    }

    public deleteSleepCondition(sleepCondition: SleepCondition): Observable<any> {
        var deleteSleepConditionUrl = this.sleepConditionsBaseUrl + "/" + sleepCondition.sleepConditionId;
        return this.httpClient.delete<SleepCondition>(deleteSleepConditionUrl, httpOptions)
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
