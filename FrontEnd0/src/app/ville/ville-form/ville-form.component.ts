import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VilleService } from '../../services/ville.service';
import {Ville} from "../../models/ville.model";


@Component({
  selector: 'app-ville-form',
  templateUrl: './ville-form.component.html',
  styleUrls: ['./ville-form.component.css']
})
export class VilleFormComponent implements OnInit {
  ville: Ville = { id: 0, code: '', ville: '' };
  isEditMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private villeService: VilleService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.villeService.getVilleById(+id).subscribe(
        data => {
          this.ville = data;

        },
        error => {
          console.error('Error fetching ville', error);
        }
      );
    }
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.villeService.updateVille(this.ville).subscribe(
        () => this.router.navigate(['/ville-list']),
        error => {
          console.error('Error updating ville', error);
        }
      );
    } else {
      this.villeService.createVille(this.ville).subscribe(
        () => this.router.navigate(['/ville-list']),
        error => {
          console.error('Error creating ville', error);
        }
      );
    }
  }
}
