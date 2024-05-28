import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {PasswordResetService} from "../services/password-reset.service";

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent {
  email: string = '';
  code: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private passwordResetService: PasswordResetService, private router: Router) {}

  onSubmit(): void {
    this.errorMessage = '';
    if (this.isValidEmail(this.email)) {
      this.isLoading = true;
      this.passwordResetService.resetPassword(this.email, this.code, this.password).subscribe({
        next: () => {
          alert('Password reset successful');
          this.router.navigate(['/login']);
        },
        error: () => {
          this.isLoading = false;
          this.errorMessage = 'Failed to reset password. Please check your inputs.';
        },
        complete: () => {
          this.isLoading = false;
        }
      });
    } else {
      this.errorMessage = 'Please enter a valid email address.';
    }
  }

  isValidEmail(email: string): boolean {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
  }
}
