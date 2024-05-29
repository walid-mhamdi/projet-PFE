import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PasswordResetComponent } from './password-reset/password-reset.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { InscriptionComponent } from './inscription/inscription.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BanqueListComponent } from './banque-list/banque-list.component';
import { BanqueFormComponent } from './banque-form/banque-form.component';
import { LayoutComponent } from './layout/layout.component';
import { NationaliteListComponent } from './nationalite/nationalite-list/nationalite-list.component';
import { NationaliteFormComponent } from './nationalite/nationalite-form/nationalite-form.component';
import { VilleListComponent } from './ville/ville-list/ville-list.component';
import { VilleFormComponent } from './ville/ville-form/ville-form.component';
import { PaysFormComponent } from './pays/pays-form/pays-form.component';
import { PaysListComponent } from './pays/pays-list/pays-list.component';
import { OperationComponent } from './operation/operation.component';
import { NgChartsModule } from 'ng2-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NotificationComponent } from './notification/notification.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    PasswordResetComponent,
    ForgotPasswordComponent,
    InscriptionComponent,
    NavbarComponent,
    BanqueListComponent,
    BanqueFormComponent,
    LayoutComponent,
    NationaliteListComponent,
    NationaliteFormComponent,
    VilleListComponent,
    VilleFormComponent,
    PaysFormComponent,
    PaysListComponent,
    OperationComponent,
    NotificationComponent,
    EmployeeListComponent,
  ],
    imports: [
      ToastrModule.forRoot({
        timeOut: 3000,
        positionClass: 'toast-top-right',
        preventDuplicates: true,
      }),
      BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        NgChartsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
