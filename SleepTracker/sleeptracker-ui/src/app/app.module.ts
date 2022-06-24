import { Component, Injectable, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { EditSleepLogComponent } from './edit-sleep-log/edit-sleep-log.component';
import { AuthService } from './auth/auth.service';
import { RegisterUserComponent } from './register-user/register-user.component';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        var username = window.sessionStorage.getItem("USERNAME");
        var password = window.sessionStorage.getItem("PASSWORD");

        var xhr;
        if (username == null || password == null) {
            xhr = req.clone({
                setHeaders: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type':  'application/json',
                    //'Authorization': 'Basic ' + btoa(username + ':' + password)
                }
            });
        }
        else {
            xhr = req.clone({
                setHeaders: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type':  'application/json',
                    'Authorization': 'Basic ' + btoa(username + ':' + password)
                }
            });
        }

        return next.handle(xhr);
    }
}

@NgModule({
  declarations: [
    AppComponent,
    EditSleepLogComponent,
    RegisterUserComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule,
    AppRoutingModule
  ],
  exports: [
    CommonModule,
  ],
  providers: [
    AuthService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
