// src/app/employee/employee.component.ts

import { Component, OnInit } from '@angular/core';
import { UtilisateurService } from '../services/utilisateur.service';
import { Utilisateur } from '../models/utilisateur.model';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  utilisateurs: Utilisateur[] = [];

  constructor(private utilisateurService: UtilisateurService) { }

  ngOnInit(): void {
    this.fetchUtilisateurs();
  }

  fetchUtilisateurs(): void {
    this.utilisateurService.getUtilisateurs().subscribe(
      (utilisateurs: Utilisateur[]) => {
        this.utilisateurs = utilisateurs;
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des utilisateurs :', error);
      }
    );
  }

}
