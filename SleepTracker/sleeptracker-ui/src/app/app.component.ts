import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'sleeptracker-ui';

    constructor(
        private authService: AuthService,
        private http: HttpClient,
        private router: Router) {
            //this.authService.authenticate(undefined);
    }

    ngOnInit(): void {}

    authenticated() {
        return this.authService.authenticated;
    }

    public logout(): void {
        this.authService.logout();
        this.router.navigate(["/login"])
      }
}
