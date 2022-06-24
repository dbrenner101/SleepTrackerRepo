import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { Location } from '../objects/Location';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class LocationsHttpServiceService {

    locationsBaseUrl = environment.apiBaseUrl + "/api/locations";

    constructor(private httpClient: HttpClient) { }

    public getAllLocations(): Observable<Location[]> {

        return this.httpClient
            .get<Location[]>(this.locationsBaseUrl)
            .pipe(
                retry(1),
                catchError(this.processError));
    }

    public updateLocation(location: Location): Observable<any> {
        var updateLocationUrl = this.locationsBaseUrl + "/" + location.locationId;
        return this.httpClient
            .put<Location>(updateLocationUrl, location, httpOptions)
                .pipe(catchError(this.processError));
    }

    public addLocation(location: Location): Observable<any> {
        return this.httpClient
            .post<Location>(this.locationsBaseUrl, location, httpOptions)
                .pipe(catchError(this.processError));
    }

    public deleteLocation(location: Location): Observable<any> {
        var deleteLocationUrl = this.locationsBaseUrl + "/" + location.locationId;

        return this.httpClient
            .delete(deleteLocationUrl, httpOptions)
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
