import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Nationalite } from '../../models/nationalite.model';
import { NationaliteService } from '../../services/nationalite.service';
import { NotificationService } from '../../services/notification.service';


@Component({
  selector: 'app-nationalite-list',
  templateUrl: './nationalite-list.component.html',
  styleUrls: ['./nationalite-list.component.css']
})
export class NationaliteListComponent implements OnInit {
  nationalites: Nationalite[] = [];

  constructor(
    private nationaliteService: NationaliteService,
    private router: Router,
    private notificationService: NotificationService,

) { }

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
        this.notificationService.changeMessage('Erreur lors du chargement des nationalités', 'danger');
      }
    );
  }

  deleteNationalite(id: number): void {
    this.nationaliteService.deleteNationalite(id).subscribe(
      (response) => {
        console.log('Deletion successful', response);
        this.nationalites = this.nationalites.filter(nationalite => nationalite.id !== id);
        this.notificationService.changeMessage('Nationalité supprimée avec succès', 'success');
        window.location.reload();

      },
      (error) => {
        console.error('Error deleting nationalite', error);
        this.notificationService.changeMessage('Nationalité supprimée avec succès', 'success');
        window.location.reload();

      }
    );
  }





}
