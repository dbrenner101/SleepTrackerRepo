import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Account } from '../objects/Account';
import { UserProfile } from '../objects/UserProfile';
import { UsersHttpService } from '../users/users.http.service';
import { UserProfileHttpServiceService } from './user-profile-http-service.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

    selectedUserProfile: UserProfile;
    selectedUser: Account;
    userId: number;
    userProfileId: number;

    firstName = new FormControl("", [Validators.required]);
    lastName = new FormControl("", [Validators.required]);
    gender = new FormControl("");
    weight = new FormControl("");
    birthdate = new FormControl("");
    targetSleepHours = new FormControl("", [Validators.required, Validators.min(1)]);

    userProfileForm = new FormGroup({
        firstName: this.firstName,
        lastName: this.lastName,
        gender: this.gender,
        weight: this.weight,
        birthdate: this.birthdate,
        targetSleepHours: this.targetSleepHours,
    });

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private httpService: UserProfileHttpServiceService,
        private userHttpService: UsersHttpService) { }

    ngOnInit(): void {
        this.userId = parseInt(this.route.snapshot.paramMap.get('userId')!, 10);
        this.userHttpService.getUserByUserId(this.userId).subscribe((data: Account ) => {
            this.selectedUser = data;
        });

        this.getUserProfile();
    }

    public getUserProfile() {
        this.httpService.getUserProfileByUserId(this.userId).subscribe((data: UserProfile) => {
            this.selectedUserProfile = data;

            if (this.selectedUserProfile != null) {
                this.firstName.setValue(this.selectedUserProfile.firstName);
                this.lastName.setValue(this.selectedUserProfile.lastName);
                this.gender.setValue(this.selectedUserProfile.gender);
                this.weight.setValue(this.selectedUserProfile.weight);
                this.birthdate.setValue(this.selectedUserProfile.birthdate);
                this.targetSleepHours.setValue(this.selectedUserProfile.targetSleepHours);
            }
        });
    }

    public updateUserProfile() {

        var userProfile = this.buildUserProfileFromFormValues();
        userProfile.userProfileId = this.selectedUserProfile.userProfileId;

        this.httpService.updateUserProfile(userProfile).subscribe((data: UserProfile) => {
            this.selectedUserProfile= data;
        });
    }

    public addUserProfile() {

        var newUserProfile = this.buildUserProfileFromFormValues();

        this.httpService.addUserProfile(newUserProfile).subscribe((data: UserProfile) => {
            this.selectedUserProfile = data;
        })
    }

    private buildUserProfileFromFormValues(): UserProfile {

        var userProfile: UserProfile = {
            account: this.selectedUser,
            firstName: this.firstName.value,
            lastName: this.lastName.value,
            weight: this.weight.value,
            birthdate: this.birthdate.value,
            gender: this.gender.value,
            targetSleepHours: this.targetSleepHours.value
        }

        return userProfile;

    }

    public deleteUserProfile() {

    }

}
