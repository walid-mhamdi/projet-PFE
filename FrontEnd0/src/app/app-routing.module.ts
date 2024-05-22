import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import {LoginComponent} from "./login/login.component";
import {authGuard} from "./guard/auth.guard";

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {path: 'login',component:LoginComponent},// Redirection par défaut vers 'home'
  { path: 'home', component: HomeComponent, canActivate:[authGuard] },

  // Ajoute d'autres routes ici
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]

})
export class AppRoutingModule { }
