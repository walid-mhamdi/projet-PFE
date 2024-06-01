import { Component, OnInit } from '@angular/core';
import { OperationService } from '../services/operation.service';
import { ChartConfiguration, ChartType } from 'chart.js';
import { Operation } from '../models/operation.model';

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
        label: 'Montant Total',
        borderColor: 'rgba(0, 123, 255, 1)',
        backgroundColor: 'rgba(0, 123, 255, 0.2)',
        fill: true,
        tension: 0.4,
        pointBackgroundColor: 'rgba(0, 123, 255, 1)',
        pointBorderColor: '#fff',
        pointBorderWidth: 2,
        pointRadius: 5,
        pointHoverRadius: 7,
        showLine: true
      },
      {
        data: [],
        label: 'Cumul Cotisation',
        borderColor: 'rgba(40, 167, 69, 1)',
        backgroundColor: 'rgba(40, 167, 69, 0.2)',
        fill: true,
        tension: 0.4,
        pointBackgroundColor: 'rgba(40, 167, 69, 1)',
        pointBorderColor: '#fff',
        pointBorderWidth: 2,
        pointRadius: 5,
        pointHoverRadius: 7,
        showLine: true
      },
      {
        data: [],
        label: 'Cumul Règlement',
        borderColor: 'rgba(255, 193, 7, 1)',
        backgroundColor: 'rgba(255, 193, 7, 0.2)',
        fill: true,
        tension: 0.4,
        pointBackgroundColor: 'rgba(255, 193, 7, 1)',
        pointBorderColor: '#fff',
        pointBorderWidth: 2,
        pointRadius: 5,
        pointHoverRadius: 7,
        showLine: true
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
        enabled: true,
        callbacks: {
          label: function(context) {
            return `${context.dataset.label}: ${context.raw}`;
          }
        }
      }
    },
    scales: {
      x: {
        display: true,
        title: {
          display: true,
          text: 'Date et Heure'
        },
        grid: {
          display: false
        }
      },
      y: {
        display: true,
        title: {
          display: true,
          text: 'Montant'
        },
        grid: {
          color: 'rgba(0, 0, 0, 0.1)'
        }
      }
    }
  };

  public lineChartType: ChartType = 'line';
  public newOperation: Operation = {
    id: 0,
    montant: '',
    dateValeur: new Date(),
    typeOperation: 'COTISATION'
  };

  public cumulativeCotisation: number = 0;
  public cumulativeReglement: number = 0;

  constructor(private operationService: OperationService) { }

  ngOnInit(): void {
    this.fetchOperations();
  }

  fetchOperations(): void {
    this.operationService.getAllOperations().subscribe(
      operations => {
        this.updateChartData(operations);
      },
      error => {
        console.error('Error fetching operations:', error);
      }
    );
  }

  updateChartData(operations: Operation[]): void {
    const labels = operations.map(operation => {
      const date = new Date(operation.dateValeur);
      return `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
    });

    const cotisations = operations.filter(operation => operation.typeOperation === 'COTISATION');
    const reglements = operations.filter(operation => operation.typeOperation === 'REGLEMENT');

    const dataMontantsTotal = operations.map(operation =>
      operation.typeOperation === 'COTISATION' ? parseFloat(operation.montant) : -parseFloat(operation.montant)
    );
    const dataCotisations = cotisations.map(operation => parseFloat(operation.montant));
    const dataReglements = reglements.map(operation => parseFloat(operation.montant));

    const completeCotisations = operations.map(operation =>
      operation.typeOperation === 'COTISATION' ? parseFloat(operation.montant) : 0
    );
    const completeReglements = operations.map(operation =>
      operation.typeOperation === 'REGLEMENT' ? parseFloat(operation.montant) : 0
    );

    const cumulativeMontants: number[] = dataMontantsTotal.reduce((acc: number[], montant: number) => {
      if (acc.length > 0) {
        acc.push(acc[acc.length - 1] + montant);
      } else {
        acc.push(montant);
      }
      return acc;
    }, []);

    const cumulativeCotisations: number[] = completeCotisations.reduce((acc: number[], montant: number) => {
      if (acc.length > 0) {
        acc.push(acc[acc.length - 1] + montant);
      } else {
        acc.push(montant);
      }
      return acc;
    }, []);

    const cumulativeReglements: number[] = completeReglements.reduce((acc: number[], montant: number) => {
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
          label: 'Montant Total',
          borderColor: 'rgba(75, 192, 192, 1)',
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          fill: true,
          tension: 0.4,
          pointBackgroundColor: 'rgba(75, 192, 192, 1)',
          pointBorderColor: '#fff',
          pointBorderWidth: 2,
          pointRadius: 5,
          pointHoverRadius: 7,
          showLine: true
        },
        {
          data: cumulativeCotisations,
          label: 'Cumul Cotisation',
          borderColor: 'rgba(54, 162, 235, 1)',
          backgroundColor: 'rgba(54, 162, 235, 0.2)',
          fill: true,
          tension: 0.4,
          pointBackgroundColor: 'rgba(54, 162, 235, 1)',
          pointBorderColor: '#fff',
          pointBorderWidth: 2,
          pointRadius: 5,
          pointHoverRadius: 7,
          showLine: true
        },
        {
          data: cumulativeReglements,
          label: 'Cumul Règlement',
          borderColor: 'rgba(255, 99, 132, 1)',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          fill: true,
          tension: 0.4,
          pointBackgroundColor: 'rgba(255, 99, 132, 1)',
          pointBorderColor: '#fff',
          pointBorderWidth: 2,
          pointRadius: 5,
          pointHoverRadius: 7,
          showLine: true
        }
      ]
    };

    this.cumulativeCotisation = cumulativeCotisations[cumulativeCotisations.length - 1];
    this.cumulativeReglement = cumulativeReglements[cumulativeReglements.length - 1];
  }

  onSubmit(): void {
    if (!this.newOperation.montant || !this.newOperation.typeOperation) {
      alert('Veuillez remplir tous les champs obligatoires.');
      return;
    }

    this.newOperation.dateValeur = new Date();
    this.operationService.saveOperation(this.newOperation).subscribe(
      operation => {
        this.fetchOperations(); // Fetch updated operations to refresh the chart
        this.newOperation = { id: 0, montant: '', dateValeur: new Date(), typeOperation: 'COTISATION' };
      },
      error => {
        console.error('Error saving operation:', error);
      }
    );
  }
}
