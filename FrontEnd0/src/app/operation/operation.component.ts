import { Component, OnInit } from '@angular/core';
import { OperationService } from '../services/operation.service';
import { ChartConfiguration, ChartType } from 'chart.js';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent implements OnInit {
  public lineChartData: ChartConfiguration['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Montant',
        borderColor: 'rgba(75, 192, 192, 1)',
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        fill: false,
      }
    ]
  };

  public lineChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top',
        labels: {
          font: {
            size: 14
          }
        }
      },
      tooltip: {
        enabled: true
      }
    },
    scales: {
      x: {
        display: true,
        title: {
          display: true,
          text: 'Date'
        }
      },
      y: {
        display: true,
        title: {
          display: true,
          text: 'Montant'
        }
      }
    }
  };

  public lineChartType: ChartType = 'line';

  constructor(private operationService: OperationService) { }

  ngOnInit(): void {
    this.operationService.getAllOperations().subscribe(
      operations => {
        const labels = operations.map(operation => new Date(operation.dateValeur).toLocaleDateString());
        const dataMontants = operations.map(operation =>
          operation.typeOperation === 'COTISATION' ? parseFloat(operation.montant) : -parseFloat(operation.montant)
        );

        const cumulativeMontants: number[] = dataMontants.reduce((acc: number[], montant: number) => {
          if (acc.length > 0) {
            acc.push(acc[acc.length - 1] + montant);
          } else {
            acc.push(montant);
          }
          return acc;
        }, []);

        this.lineChartData = {
          labels: labels,
          datasets: [
            {
              data: cumulativeMontants,
              label: 'Montant',
              borderColor: 'rgba(75, 192, 192, 1)',
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              fill: false,
            }
          ]
        };
      },
      error => {
        console.error('Error fetching operations:', error);
      }
    );
  }
}
