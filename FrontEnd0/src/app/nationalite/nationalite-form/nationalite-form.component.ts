// src/app/components/nationalite-form/nationalite-form.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Nationalite } from '../../models/nationalite.model';
import { NationaliteService } from '../../services/nationalite.service';

@Component({
  selector: 'app-nationalite-form',
  templateUrl: './nationalite-form.component.html',
  styleUrls: ['./nationalite-form.component.css']
})
export class NationaliteFormComponent implements OnInit {
  nationalite: Nationalite = { id: 0, code: '', libelle: '' };
  isEditMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private nationaliteService: NationaliteService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.nationaliteService.getNationaliteById(+id).subscribe(
        data => {
          this.nationalite = data;
        },
        error => {
          console.error('Error fetching nationalite', error);
        }
      );
    }
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.nationaliteService.updateNationalite(this.nationalite).subscribe(
        () => this.router.navigate(['/nationalite-list']),
        error => {
          console.error('Error updating nationalite', error);
        }
      );
    } else {
      this.nationaliteService.createNationalite(this.nationalite).subscribe(
        () => this.router.navigate(['/nationalite-list']),
        error => {
          console.error('Error creating nationalite', error);
        }
      );
    }
  }
}
