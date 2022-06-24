import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { UserProfile } from '../objects/UserProfile';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class UserProfileHttpServiceService {

    userProfileBaseUrl = environment.apiBaseUrl + "/api/userProfile";

    constructor(private httpClient: HttpClient) { }

    public getUserProfileByUserId(userId: number): Observable<UserProfile> {
        var getUserProfileUrl = this.userProfileBaseUrl + "/forUser/" + userId;
        return this.httpClient.get<UserProfile>(getUserProfileUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public getAllUserProfiles(): Observable<UserProfile[]> {
        return this.httpClient.get<UserProfile[]>(this.userProfileBaseUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public updateUserProfile(userProfile: UserProfile): Observable<any> {
        var updateUserProfileUrl = this.userProfileBaseUrl + "/" + userProfile.userProfileId;
        return this.httpClient.put<UserProfile>(updateUserProfileUrl, userProfile, httpOptions)
            .pipe(catchError(this.processError));
    }

    public addUserProfile(userProfile: UserProfile): Observable<any> {
        console.log("sending to api: " + JSON.stringify(userProfile));
        return this.httpClient.post<UserProfile>(this.userProfileBaseUrl, userProfile, httpOptions)
            .pipe(catchError(this.processError));
    }

    public deleteUserProfile(userProfile: UserProfile): Observable<any> {
        var deleteUserProfileUrl = this.userProfileBaseUrl + "/" + userProfile.userProfileId;
        return this.httpClient.delete<UserProfile>(deleteUserProfileUrl, httpOptions)
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
