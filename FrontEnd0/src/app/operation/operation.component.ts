import { Component, OnInit } from '@angular/core';
import { OperationService } from '../services/operation.service';
import { ChartConfiguration } from 'chart.js';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent implements OnInit {
  data: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: []
  };

  constructor(private operationService: OperationService) { }

  ngOnInit(): void {
    this.operationService.getAllOperations().subscribe(
      operations => {
        const cotisationMontants = operations
          .filter(operation => operation.typeOperation === 'COTISATION')
          .map(operation => parseFloat(operation.montant));

        const reglementMontants = operations
          .filter(operation => operation.typeOperation === 'REGLEMENT')
          .map(operation => -parseFloat(operation.montant));

        this.data = {
          labels: ['COTISATION', 'REGLEMENT'],
          datasets: [
            {
              label: 'Montant',
              data: [this.calculateTotal(cotisationMontants), this.calculateTotal(reglementMontants)],
              backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 99, 132, 0.2)'],
              borderColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 99, 132, 1)'],
              borderWidth: 1
            }
          ]
        };
      },
      error => {
        console.error('Error fetching operations:', error);
      }
    );
  }

  calculateTotal(values: number[]): number {
    return values.reduce((total, value) => total + value, 0);
  }
}
