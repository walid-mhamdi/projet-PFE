import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Pays } from '../../models/pays.model';
import { PaysService } from '../../services/pays.service';

@Component({
  selector: 'app-pays-form',
  templateUrl: './pays-form.component.html',
  styleUrls: ['./pays-form.component.css']
})
export class PaysFormComponent implements OnInit {
  pays: Pays = { id: 0, code: '', libelle: '' };
  isEditMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private paysService: PaysService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.paysService.getPaysById(+id).subscribe(
        data => {
          this.pays = data;
        },
        error => {
          console.error('Error fetching pays', error);
        }
      );
    }
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.paysService.updatePays(this.pays).subscribe(
        () => this.router.navigate(['/pays-list']),
        error => {
          console.error('Error updating pays', error);
        }
      );
    } else {
      this.paysService.createPays(this.pays).subscribe(
        () => this.router.navigate(['/pays-list']),
        error => {
          console.error('Error creating pays', error);
        }
      );
    }
  }
}
