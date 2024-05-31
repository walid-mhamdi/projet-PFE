import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'; // Import the Router service
import { Banque } from "../models/banque.model";
import { BanqueService } from "../services/banque.service";

@Component({
  selector: 'app-banque-list',
  templateUrl: './banque-list.component.html',
  styleUrls: ['./banque-list.component.css']
})
export class BanqueListComponent implements OnInit {
  banques: Banque[] = [];
  isLoading = true; // Add isLoading state

  constructor(private banqueService: BanqueService, private router: Router) { } // Inject the Router service

  ngOnInit(): void {
    this.loadBanques();
  }

  loadBanques(): void {
    this.isLoading = true;
    this.banqueService.getBanques().subscribe(
      data => {
        this.banques = data;
        this.isLoading = false;
      },
      error => {
        console.error('Error fetching banques', error);
        this.isLoading = false;
      }
    );
  }

  deleteBanque(id: number): void {
    if (confirm('Are you sure you want to delete this banque?')) {
      this.banqueService.deleteBanque(id).subscribe(
        () => {
          this.banques = this.banques.filter(banque => banque.id !== id);
          alert('Suppression réussie');
        },
        error => {
          console.error('Error deleting banque', error);
          alert('Erreur lors de la suppression');
        }
      );
    }
  }
}
