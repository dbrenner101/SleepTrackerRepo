import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { Attitude } from '../objects/Attitude';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AttitudesHttpService {

    attitudesBaseUrl = environment.apiBaseUrl + "/api/attitudes";

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type':  'application/json',
            'Authorization': 'Basic ' + btoa('username:password')
       })
    };

    constructor(
        private httpClient: HttpClient,
        private authService: AuthService) { }

    public getAllAttitudes(): Observable<Attitude[]> {
        return this.httpClient.get<Attitude[]>(this.attitudesBaseUrl)
            .pipe(catchError(this.processError));
    }

    public updateAttitude(attitude: Attitude): Observable<any> {
        var updateAttitudeUrl = this.attitudesBaseUrl + "/" + attitude.attitudeId;
        return this.httpClient.put<Attitude>(updateAttitudeUrl, attitude, this.httpOptions)
            .pipe(catchError(this.processError));
    }

    public addAttitude(attitude: Attitude): Observable<any> {
        return this.httpClient.post<Attitude>(this.attitudesBaseUrl, attitude, this.httpOptions)
            .pipe(catchError(this.processError));
    }

    public deleteAttitude(attitude: Attitude): Observable<any> {
        var deleteAttitudeUrl = this.attitudesBaseUrl + "/" + attitude.attitudeId;
        return this.httpClient.delete<Attitude>(deleteAttitudeUrl, this.httpOptions)
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
