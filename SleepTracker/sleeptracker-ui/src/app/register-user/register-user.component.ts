import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { UsersHttpService } from '../users/users.http.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {

    username = new FormControl("", [Validators.required]);
    password = new FormControl("", [Validators.required]);

    newUserErrorMessage: String = "";

    usersForm = new FormGroup({
        usersList: new FormControl(""),
        userId: new FormControl(""),
        username: this.username,
        password: this.password,
    });

    constructor(
        private httpClient: UsersHttpService,
        private router: Router,
        private authService: AuthService) { }

    ngOnInit(): void {}

    authenticated() {
        return this.authService.authenticated;
    }

    addUser() {
        this.newUserErrorMessage = "";

        var newUser = {
            username: this.usersForm.get("username").value,
            password: this.usersForm.get("password").value,
        };


        this.httpClient.addUser(newUser).subscribe(result => {
            this.router.navigate(["/login"]);
        }, error => {
            console.log(error);
            if (error.status == 406) {
                this.newUserErrorMessage = "Invalid username";
                this.username.reset();
            }
        });
    }

}
