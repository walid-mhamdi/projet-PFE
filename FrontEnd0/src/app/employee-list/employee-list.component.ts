import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { UserService } from '../services/user.service';
import { User } from '../models/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees: User[] = [];

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe(
      (data: User[]) => {
        this.employees = data;
      },
      (error: HttpErrorResponse) => {
        console.error('Error fetching employees', error);
      }
    );
  }

  deleteEmployee(id: number): void {
    this.userService.deleteUser(id).subscribe(
      () => {
        this.employees = this.employees.filter(employee => employee.id !== id);
        alert('Suppression réussie');
        window.location.reload();

      },
      (error: HttpErrorResponse) => {
        console.error('Error deleting employee', error);
        alert('Suppression réussie');
        window.location.reload();
      }
    );
  }
}
