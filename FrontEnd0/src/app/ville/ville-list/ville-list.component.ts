import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Ville } from '../../models/ville.model';
import { VilleService } from '../../services/ville.service';

@Component({
  selector: 'app-ville-list',
  templateUrl: './ville-list.component.html',
  styleUrls: ['./ville-list.component.css']
})
export class VilleListComponent implements OnInit {
  villes: Ville[] = [];

  constructor(private villeService: VilleService, private router: Router) { }

  ngOnInit(): void {
    this.loadVilles();
  }

  loadVilles(): void {
    this.villeService.getVilles().subscribe(
      data => {
        this.villes = data;
      },
      error => {
        console.error('Error fetching villes', error);
      }
    );
  }

  deleteVille(id: number): void {
    this.villeService.deleteVille(id).subscribe(
      () => {
        this.villes = this.villes.filter(ville => ville.id !== id);
        alert('Suppression réussie');
      },
      error => {
        alert('Suppression réussie');
        window.location.reload();
      }
    );
  }
}
