import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { Account } from '../objects/Account';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

    username = new FormControl("", Validators.required);
    password = new FormControl("", Validators.required);

    authForm = new FormGroup({
        username: this.username,
        password: this.password
    });

    constructor (
        private authService: AuthService,
        private router: Router
    ) { }

    ngOnInit(): void {}

    authenticated() {
        return this.authService.authenticated;
    }

    public authenticate() {
        var user: Account = {
            username: this.username.value,
            password: this.password.value
        }

        if (user !== undefined && user != null && user.username != null && user.password != null) {
            this.authService.authenticate(user);
        }
    }

    public logout(): void {
        this.authService.logout();
    }

}
