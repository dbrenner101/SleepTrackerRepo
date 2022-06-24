import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { Health } from '../objects/Health';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class HealthHttpService {

    healthBaseUrl = environment.apiBaseUrl + "/api/health";

    constructor(private httpClient: HttpClient) { }

    public getAllHealth(): Observable<Health[]> {
        return this.httpClient.get<Health[]>(this.healthBaseUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public updateHealth(health: Health): Observable<any> {
        var updateHealthUrl = this.healthBaseUrl + "/" + health.healthId;
        return this.httpClient.put<Health>(updateHealthUrl, health, httpOptions)
            .pipe(catchError(this.processError));
    }

    public addHealth(health: Health): Observable<any> {
        return this.httpClient.post<Health>(this.healthBaseUrl, health, httpOptions)
            .pipe(catchError(this.processError));
    }

    public deleteHealth(health: Health): Observable<any> {
        var deleteHealthUrl = this.healthBaseUrl + "/" + health.healthId;
        return this.httpClient.delete<Health>(deleteHealthUrl, httpOptions)
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
