import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Nationalite } from '../../models/nationalite.model';
import { NationaliteService } from '../../services/nationalite.service';

@Component({
  selector: 'app-nationalite-list',
  templateUrl: './nationalite-list.component.html',
  styleUrls: ['./nationalite-list.component.css']
})
export class NationaliteListComponent implements OnInit {
  nationalites: Nationalite[] = [];

  constructor(private nationaliteService: NationaliteService, private router: Router) { }

  ngOnInit(): void {
    this.loadNationalites();
  }

  loadNationalites(): void {
    this.nationaliteService.getNationalites().subscribe(
      data => {
        this.nationalites = data;
      },
      error => {
        console.error('Error fetching nationalites', error);
        window.location.reload();

      }
    );
  }

  deleteNationalite(id: number): void {
    this.nationaliteService.deleteNationalite(id).subscribe(
      () => {
        this.nationalites = this.nationalites.filter(nationalite => nationalite.id !== id);
        alert('Suppression réussie');
        window.location.reload();

      },
      error => {
        console.error('Error deleting nationalite', error);
        alert('Erreur lors de la suppression');
        window.location.reload();

      }
    );
  }
}
