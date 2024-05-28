import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'; // Import du service Router
import { Banque } from "../models/banque.model";
import { BanqueService } from "../services/banque.service";

@Component({
  selector: 'app-banque-list',
  templateUrl: './banque-list.component.html',
  styleUrls: ['./banque-list.component.css']
})
export class BanqueListComponent implements OnInit {
  banques: Banque[] = [];

  constructor(private banqueService: BanqueService, private router: Router) { } // Injection du service Router

  ngOnInit(): void {
    this.loadBanques();
  }

  loadBanques(): void {
    this.banqueService.getBanques().subscribe(
      data => {
        this.banques = data;
      },
      error => {
        console.error('Error fetching banques', error);
      }
    );
  }

  deleteBanque(id: number): void {
    this.banqueService.deleteBanque(id).subscribe(
      () => {
        // Suppression réussie
        this.banques = this.banques.filter(banque => banque.id !== id);
        // Rafraîchir la page
        alert('Suppression réussie');
        window.location.reload();


      },
      error => {
        console.error('Error deleting banque', error);
        alert('Suppression réussie');
        window.location.reload();

      }
    );
  }



}
