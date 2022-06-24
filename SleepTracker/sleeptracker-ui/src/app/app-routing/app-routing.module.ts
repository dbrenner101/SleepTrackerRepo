import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button'
import { MatMomentDateModule } from "@angular/material-moment-adapter";
import { MatCommonModule } from '@angular/material/core';
import {MatMenuModule} from '@angular/material/menu';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';

import { AppRoutingRoutingModule } from './app-routing-routing.module';
import { AdminComponent } from '../admin/admin.component';
import { LocationsComponent } from '../locations/locations.component';
import { AttitudesComponent } from '../attitudes/attitudes.component';
import { UsersComponent } from '../users/users.component';
import { DietsComponent } from '../diets/diets.component';
import { HealthComponent } from '../health/health.component';
import { HabitsComponent } from '../habits/habits.component';
import { SleepconditionsComponent } from '../sleepconditions/sleepconditions.component';
import { SleepresultComponent } from '../sleepresult/sleepresult.component';
import { NewSleepLogComponent } from '../new-sleep-log/new-sleep-log.component';
import { EditSleepLogComponent } from '../edit-sleep-log/edit-sleep-log.component';
import { UserProfileComponent } from '../user-profile/user-profile.component';
import { AuthComponent } from '../auth/auth.component';
import { RegisterUserComponent } from '../register-user/register-user.component';

const routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full' },
    {path: "admin", component: AdminComponent},
    {path: "locations-admin", component: LocationsComponent},
    {path: "attitudes-admin", component: AttitudesComponent},
    {path: "users-admin", component: UsersComponent},
    {path: "user-profile/:userId", component: UserProfileComponent},
    {path: "register", component: RegisterUserComponent},
    {path: "diets-admin", component: DietsComponent},
    {path: "health-admin", component: HealthComponent},
    {path: "habits-admin", component: HabitsComponent},
    {path: "sleep-conditions-admin", component: SleepconditionsComponent},
    {path: "sleep-results-admin", component: SleepresultComponent},
    {path: "new-sleep-log", component: NewSleepLogComponent},
    {path: "edit-sleep-log", component: EditSleepLogComponent},
    {path: "modify-sleep-log/:sleepLogId", component: NewSleepLogComponent},
    {path: "login", component: AuthComponent},
    {path: "logout", component: AuthComponent},
    {path: "admin", component: AdminComponent}
]

@NgModule({
  declarations: [
    AdminComponent,
    LocationsComponent,
    AttitudesComponent,
    UsersComponent,
    DietsComponent,
    HealthComponent,
    HabitsComponent,
    SleepconditionsComponent,
    SleepresultComponent,
    NewSleepLogComponent,
    UserProfileComponent,
    AuthComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingRoutingModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatInputModule,
    MatButtonModule,
    MatMomentDateModule,
    MatCommonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatSelectModule,
    MatSelectModule
  ],
  exports: [
      CommonModule,
      ReactiveFormsModule,
      FormsModule,
      RouterModule,
      BrowserAnimationsModule,
      MatDatepickerModule,
      MatFormFieldModule,
      MatDatepickerModule,
      MatInputModule,
      MatButtonModule,
      MatMomentDateModule,
      MatCommonModule,
      MatMenuModule,
      MatToolbarModule,
      MatIconModule,
      MatSelectModule,
      MatSelectModule
    ],
})
export class AppRoutingModule { }
