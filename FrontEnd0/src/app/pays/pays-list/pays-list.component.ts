import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Pays } from '../../models/pays.model';
import { PaysService } from '../../services/pays.service';
import { animate, query, stagger, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-pays-list',
  templateUrl: './pays-list.component.html',
  styleUrls: ['./pays-list.component.css'],
  animations: [
    trigger('listAnimation', [
      transition('* => *', [
        query(':enter', [
          style({ opacity: 0, transform: 'translateY(-10px)' }),
          stagger(100, [
            animate('0.5s ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
          ])
        ], { optional: true }),
        query(':leave', [
          animate('0.5s ease-out', style({ opacity: 0, transform: 'translateY(-10px)' }))
        ], { optional: true })
      ])
    ])
  ]
})
export class PaysListComponent implements OnInit {
  paysList: Pays[] = [];

  constructor(private paysService: PaysService, private router: Router) {}

  ngOnInit(): void {
    this.loadPays();
  }

  loadPays(): void {
    this.paysService.getAllPays().subscribe(
      data => {
        this.paysList = data;
      },
      error => {
        console.error('Error fetching pays', error);
      }
    );
  }

  deletePays(id: number): void {
    this.paysService.deletePays(id).subscribe(
      () => {
        this.paysList = this.paysList.filter(pays => pays.id !== id);
        alert('Suppression réussie');
      },
      error => {
        alert('Erreur lors de la suppression');
        window.location.reload();
      }
    );
  }
}
