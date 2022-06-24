import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { Diet } from '../objects/Diet';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class DietHttpService {

    dietsBaseUrl = environment.apiBaseUrl + "/api/diets";

    constructor(private httpClient: HttpClient) { }

    public getAllDiets(): Observable<Diet[]> {
        return this.httpClient.get<Diet[]>(this.dietsBaseUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public updateDiet(diet: Diet): Observable<any> {
        var updateDietUrl = this.dietsBaseUrl + "/" + diet.dietId;
        return this.httpClient.put<Diet>(updateDietUrl, diet, httpOptions)
            .pipe(catchError(this.processError));
    }

    public addDiet(diet: Diet): Observable<any> {
        return this.httpClient.post<Diet>(this.dietsBaseUrl, diet, httpOptions)
            .pipe(catchError(this.processError));
    }

    public deleteDiet(diet: Diet): Observable<any> {
        var deleteDietUrl = this.dietsBaseUrl + "/" + diet.dietId;
        return this.httpClient.delete<Diet>(deleteDietUrl, httpOptions)
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
