import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

import { environment } from 'src/environments/environment';

import { Account } from '../objects/Account';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

    authServiceUrl = environment.apiBaseUrl + "/api/auth";

    authenticated = false;

    authResponse;

    constructor(
        private httpService: HttpClient
    ) { }

    authenticate(user) {

        if (user != undefined) {
            this.auth(user).subscribe((data: {}) => {
                var accString = JSON.stringify(data);
                var acc: Account = JSON.parse(accString);
                if (acc.username) {
                    this.authenticated = true;
                    window.sessionStorage.setItem("USERNAME", user.username);
                    window.sessionStorage.setItem("PASSWORD", user.password);
                } else {
                    this.authenticated = false;
                }
            });
        }
    }

    private auth(user: Account): Observable<any> {

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type':  'application/json',
                authorization : 'Basic ' + btoa(user.username + ':' + user.password)
           })
        };
        return this.httpService.get<String>(this.authServiceUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    isAuthenticated() {
        return this.authenticated;
    }

    logout() {
        window.sessionStorage.removeItem("USERNAME");
        window.sessionStorage.removeItem("PASSWORD");
        this.authenticated = false;
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
