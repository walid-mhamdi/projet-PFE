import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  email: string = '';
  successMessage: string = '';

  errorMessage: string = '';


  constructor(private authService: AuthService, private router: Router) { }


  resetPassword() {
    if (this.email) {
      this.authService.resetPassword(this.email).pipe(
        take(1)
      ).subscribe({
        next: () => {
          this.successMessage = 'Un email de réinitialisation.';
          this.errorMessage = '';
          this.router.navigate(['/login']);
        },
        error: (err) => {
          if (err.status === 404) {
            this.errorMessage = 'L\'adresse email spécifiée n\'existe pas.';
          } else {
            this.errorMessage = 'Échec de la réinitialisation du mot de passe. Veuillez réessayer.';
          }
          this.successMessage = '';
        }
      });
    } else {
      this.errorMessage = 'Veuillez entrer une adresse email.';
      this.successMessage = '';
    }
  }

}
