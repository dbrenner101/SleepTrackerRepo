import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { Account } from "../objects/Account";

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class UsersHttpService {

    usersBaseUrl = environment.apiBaseUrl + "/api/users";


    constructor(private httpClient: HttpClient) { }

    public getUserByUserId(accountId: number): Observable<Account> {
        return this.httpClient.get<Account>(this.usersBaseUrl + "/" + accountId, httpOptions)
            .pipe(catchError(this.processError));
    }

    public getAllUsers(): Observable<Account[]> {
        return this.httpClient.get<Account[]>(this.usersBaseUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public updateUser(account: Account): Observable<any> {
        var updateUserUrl = this.usersBaseUrl + "/" + account.accountId;
        return this.httpClient.put<Account>(updateUserUrl, account, httpOptions)
            .pipe(catchError(this.processError));
    }

    public addUser(user: Account): Observable<any> {
        return this.httpClient.post<Account>(environment.apiBaseUrl + "/newUser", user);
    }

    public deleteUser(user: Account): Observable<any> {
        var deleteUserUrl = this.usersBaseUrl + "/" + user.accountId;
        return this.httpClient.delete<Account>(deleteUserUrl, httpOptions)
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
