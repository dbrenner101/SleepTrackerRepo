import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';

import { environment } from 'src/environments/environment';

import { Habit } from '../objects/Habit';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json'
   })
};

@Injectable({
  providedIn: 'root'
})
export class HabitsHttpService {

    habitsBaseUrl = environment.apiBaseUrl + "/api/habits";

    constructor(private httpClient: HttpClient) { }

    public getAllHabits(): Observable<Habit[]> {
        return this.httpClient.get<Habit[]>(this.habitsBaseUrl, httpOptions)
            .pipe(catchError(this.processError));
    }

    public updateHabit(habit: Habit): Observable<Habit> {
        var updateHabitUrl = this.habitsBaseUrl + "/" + habit.habitId;
        return this.httpClient.put<Habit>(updateHabitUrl, habit, httpOptions)
            .pipe(catchError(this.processError));
    }

    public addHabit(habit: Habit): Observable<Habit> {
        return this.httpClient.post<Habit>(this.habitsBaseUrl, habit, httpOptions)
            .pipe(catchError(this.processError));
    }

    public deleteHabit(habit: Habit): Observable<any> {
        var deleteHabitUrl = this.habitsBaseUrl + "/" + habit.habitId;
        return this.httpClient.delete<Habit>(deleteHabitUrl, httpOptions)
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
