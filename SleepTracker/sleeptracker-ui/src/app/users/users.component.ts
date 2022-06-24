import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { Account } from '../objects/Account';
import { UsersHttpService } from './users.http.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

    users: any = [];
    selectedUser: Account;

    username = new FormControl("", [Validators.required]);
    password = new FormControl("", [Validators.required]);

    usersForm = new FormGroup({
        usersList: new FormControl(""),
        userId: new FormControl(""),
        username: this.username,
        password: this.password,
    });

    constructor(private httpClient: UsersHttpService) { }

    ngOnInit(): void {
        this.getAllUsers();
    }

    private clearForm() {
        this.username.setValue("");
        this.username.setErrors(null);
        this.password.setValue("");
        this.password.setErrors(null);
    }

    get f(){
        return this.usersForm.controls;
    }

    getAllUsers() {
        this.httpClient.getAllUsers().subscribe((data: {}) => {
            this.users = data;
        });
    }

    changeUser(e) {
        let accountId = e.target.value;
        if (accountId != "") {
            console.log("accountId: " + accountId);
            this.selectedUser = this.users.find(i => i.accountId == accountId);

            this.usersForm.get("username").setValue(this.selectedUser.username);
            this.usersForm.get("password").setValue(this.selectedUser.password);
        }
    }

    updateUser() {
        var updatedUser = {
            accountId: this.selectedUser.accountId,
            username: this.usersForm.get("username").value,
            password: this.usersForm.get("password").value,
        }

        this.httpClient.updateUser(updatedUser).subscribe(result => {
            this.getAllUsers();
            this.clearForm();
        });
    }

    deleteUser() {
        this.httpClient.deleteUser(this.selectedUser).subscribe(result => {
            this.getAllUsers();
            this.clearForm();
        });
    }

}
