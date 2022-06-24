import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { SleepResult } from '../objects/SleepResult';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class SleepresulthttpService {

    sleepResultsBaseUrl = environment.apiBaseUrl + "/api/sleepResults";

    constructor(private httpClient: HttpClient) { }

    public getAllSleepResults(): Observable<SleepResult[]> {
        return this.httpClient.get<SleepResult[]>(this.sleepResultsBaseUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public updateSleepResult(sleepResult: SleepResult): Observable<any> {
        var updateSleepResultUrl = this.sleepResultsBaseUrl + "/" + sleepResult.sleepResultId;
        return this.httpClient.put<SleepResult>(updateSleepResultUrl, sleepResult, httpOptions)
            .pipe(catchError(this.processError));
    }

    public addSleepResult(sleepResult: SleepResult): Observable<any> {
        return this.httpClient.post<SleepResult>(this.sleepResultsBaseUrl, sleepResult, httpOptions)
            .pipe(catchError(this.processError));
    }

    public deleteSleepResult(sleepResult: SleepResult): Observable<any> {
        var deleteSleepResultUrl = this.sleepResultsBaseUrl + "/" + sleepResult.sleepResultId;
        return this.httpClient.delete<SleepResult>(deleteSleepResultUrl, httpOptions)
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
