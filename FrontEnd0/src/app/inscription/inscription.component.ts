import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { InscriptionService } from '../services/inscription.service';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.css']
})
export class InscriptionComponent {
  inscription = {
    nom: '',
    email: '',
    mdp: '',
    role: ''
  };

  roles = ['EMPLOYEE', 'ADMINISTRATEUR'];

  constructor(private inscriptionService: InscriptionService, private router: Router) {}

  onSubmit() {
    this.inscriptionService.inscrire(this.inscription).subscribe(
      () => {
        alert('Inscription réussie');
        // Réinitialise les champs du formulaire à des valeurs vides
        this.inscription = {
          nom: '',
          email: '',
          mdp: '',
          role: ''
        };
        // Recharge la page actuelle sans changer l'historique de navigation
        this.router.navigateByUrl('/inscription', { skipLocationChange: true }).then(() => {
          this.router.navigate([this.router.url]);
        });
      },
      (error) => {
        alert('Erreur: ' + error.error);
      }
    );
  }
}
