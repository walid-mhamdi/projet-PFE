import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {Banque} from "../models/banque.model";
import {BanqueService} from "../services/banque.service";

@Component({
  selector: 'app-banque-form',
  templateUrl: './banque-form.component.html',
  styleUrls: ['./banque-form.component.css']
})
export class BanqueFormComponent implements OnInit {
  banque: Banque = { id: 0, code: '', libelle: '' };
  isEditMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private banqueService: BanqueService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.banqueService.getBanqueById(+id).subscribe(
        data => {
          this.banque = data;

        },
        error => {
          console.error('Error fetching banque', error);
        }
      );
    }
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.banqueService.updateBanque(this.banque).subscribe(
        () => this.router.navigate(['/banque']),
        error => {
          console.error('Error updating banque', error);
        }
      );
    } else {
      this.banqueService.createBanque(this.banque).subscribe(
        () => this.router.navigate(['/banque']),
        error => {
          console.error('Error creating banque', error);
        }
      );
    }
  }
}
