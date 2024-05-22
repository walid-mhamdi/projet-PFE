import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  nom: string = '';
  mdp: string = '';

  errorMessage: string = '';
  isLoginTab: boolean = true; // Pour basculer entre les onglets de connexion et d'inscription
  isSignUpSuccess: boolean = false; // Ajout d'une propriété pour suivre l'état de l'inscription
  signUpSuccessMessage: string = ''; // Ajout d'une propriété pour le message de succès

  constructor(private authService: AuthService, private router: Router) { }

  isValidEmail(email: string): boolean {
    const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return regex.test(email);
  }

  login() {
    if (this.isValidEmail(this.email)) {
      this.authService.login(this.email, this.password).subscribe({
        next: () => {
          this.router.navigate(['/home']);
        },
        error: () => {
          this.errorMessage = 'Échec de la connexion. Veuillez vérifier vos identifiants.';
        }
      });
    } else {
      this.errorMessage = 'Veuillez entrer une adresse email valide.';
    }
  }


  signUp() {
    if (this.isValidEmail(this.email)) {
      this.authService.signUp(this.nom, this.mdp, this.email).subscribe({
        next: (response) => {
          this.signUpSuccessMessage = response; // Message de succès récupéré du backend
        },
        error: () => {
          this.errorMessage = 'Échec de l\'inscription. Veuillez réessayer.';
        }
      });
    } else {
      this.errorMessage = 'Veuillez entrer une adresse email valide.';
    }
  }


  toggleTab(isLogin: boolean) {
    this.isLoginTab = isLogin;
  }
}
