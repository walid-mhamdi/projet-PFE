import { NgModule } from '@angular/core';
import {authGuard} from "./guard/auth.guard";
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {PasswordResetComponent} from "./password-reset/password-reset.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {InscriptionComponent} from "./inscription/inscription.component";
import {HomeComponent} from "./home/home.component";
import {NavbarComponent} from "./navbar/navbar.component";
import {EmployeeComponent} from "./employee/employee.component";
import {BanqueFormComponent} from "./banque-form/banque-form.component";
import {BanqueListComponent} from "./banque-list/banque-list.component";
import {LayoutComponent} from "./layout/layout.component";
import {NationaliteFormComponent} from "./nationalite/nationalite-form/nationalite-form.component";
import {NationaliteListComponent} from "./nationalite/nationalite-list/nationalite-list.component";
import {VilleFormComponent} from "./ville/ville-form/ville-form.component";
import {VilleListComponent} from "./ville/ville-list/ville-list.component";
import {PaysFormComponent} from "./pays/pays-form/pays-form.component";
import {PaysListComponent} from "./pays/pays-list/pays-list.component";
import {OperationComponent} from "./operation/operation.component";

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate:[authGuard]  },
  { path: 'navbar', component: NavbarComponent, canActivate:[authGuard]  },
  { path: 'inscription', component: InscriptionComponent, canActivate:[authGuard]  },
  { path: 'employee', component: EmployeeComponent, canActivate:[authGuard]  },
  { path: 'banque', component: BanqueListComponent, canActivate:[authGuard]},

  { path: 'banque-form/:id', component: BanqueFormComponent, canActivate:[authGuard] },
  { path: 'banque-form', component: BanqueFormComponent, canActivate:[authGuard] },
  { path: 'banque-list', component: BanqueListComponent, canActivate:[authGuard] },

  { path: 'ville-form/:id', component: VilleFormComponent, canActivate:[authGuard] },
  { path: 'ville-form', component: VilleFormComponent, canActivate:[authGuard] },
  { path: 'ville-list', component: VilleListComponent, canActivate:[authGuard] },

  { path: 'pays-form/:id', component: PaysFormComponent, canActivate:[authGuard] },
  { path: 'pays-form', component: PaysFormComponent, canActivate:[authGuard] },
  { path: 'pays-list', component: PaysListComponent, canActivate:[authGuard] },

  { path: 'nationalite-form/:id', component: NationaliteFormComponent, canActivate:[authGuard]},
  { path: 'nationalite-form', component: NationaliteFormComponent, canActivate:[authGuard]},
  { path: 'nationalite-list', component: NationaliteListComponent, canActivate:[authGuard]},
  {path: 'operation', component:OperationComponent, canActivate:[authGuard]},


  {path: 'layout',component:LayoutComponent, canActivate:[authGuard]},

  {path: 'login',component:LoginComponent},
  {path: 'password-reset' ,component: PasswordResetComponent},
  { path: 'forgot-password', component: ForgotPasswordComponent },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]

})
export class AppRoutingModule { }
