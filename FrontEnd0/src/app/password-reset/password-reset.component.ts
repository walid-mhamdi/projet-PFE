import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PasswordResetService } from '../services/password-reset.service';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent {
  passwordResetForm: FormGroup;
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private passwordResetService: PasswordResetService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.passwordResetForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      code: ['', Validators.required],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        this.passwordValidator()
      ]]
    });
  }

  onSubmit(): void {
    this.errorMessage = '';
    if (this.passwordResetForm.valid) {
      this.isLoading = true;
      const { email, code, password } = this.passwordResetForm.value;
      this.passwordResetService.resetPassword(email, code, password).subscribe({
        next: () => {
          this.toastr.success('Mot de passe réinitialisé avec succès');
          this.router.navigate(['/login']);
        },
        error: () => {
          this.isLoading = false;
          this.errorMessage = 'La réinitialisation du mot de passe a échoué. Veuillez vérifier vos informations.';
        },
        complete: () => {
          this.isLoading = false;
        }
      });
    } else {
      this.errorMessage = 'Veuillez remplir correctement le formulaire.';
    }
  }

  passwordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (!value) {
        return null;
      }

      const errors: any = {};

      if (!/[A-Z]/.test(value)) {
        errors.noUpperCase = true;
      }
      if (!/[a-z]/.test(value)) {
        errors.noLowerCase = true;
      }
      if (!/\d/.test(value)) {
        errors.noDigit = true;
      }
      if (!/[^\w\s]/.test(value)) {
        errors.noSpecialChar = true;
      }

      return Object.keys(errors).length ? errors : null;
    };
  }

  get email() { return this.passwordResetForm.get('email'); }
  get code() { return this.passwordResetForm.get('code'); }
  get password() { return this.passwordResetForm.get('password'); }
}
