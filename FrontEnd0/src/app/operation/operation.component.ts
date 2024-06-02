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
  public cotisationPercentage: number = 0;
  public reglementPercentage: number = 0;

  // Variables pour stocker toutes les données
  private allLabels: any[] = [];
  private allCumulativeMontants: number[] = [];
  private allCumulativeCotisations: number[] = [];
  private allCumulativeReglements: number[] = [];

  constructor(private operationService: OperationService) { }

  ngOnInit(): void {
    this.fetchOperations();
  }

  fetchOperations(): void {
    this.operationService.getAllOperations().subscribe(
      operations => {
        this.updateChartData(operations);
        this.calculatePercentages();
      },
      error => {
        console.error('Error fetching operations:', error);
      }
    );
  }

  updateChartData(operations: Operation[]): void {
    this.allLabels = operations.map(operation => {
      const date = new Date(operation.dateValeur);
      return `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
    });

    const cotisations = operations.filter(operation => operation.typeOperation === 'COTISATION');
    const reglements = operations.filter(operation => operation.typeOperation === 'REGLEMENT');

    const dataMontantsTotal = operations.map(operation =>
      operation.typeOperation === 'COTISATION' ? parseFloat(operation.montant) : -parseFloat(operation.montant)
    );
    const completeCotisations = operations.map(operation =>
      operation.typeOperation === 'COTISATION' ? parseFloat(operation.montant) : 0
    );
    const completeReglements = operations.map(operation =>
      operation.typeOperation === 'REGLEMENT' ? parseFloat(operation.montant) : 0
    );

    this.allCumulativeMontants = dataMontantsTotal.reduce((acc: number[], montant: number) => {
      if (acc.length > 0) {
        acc.push(acc[acc.length - 1] + montant);
      } else {
        acc.push(montant);
      }
      return acc;
    }, []);

    this.allCumulativeCotisations = completeCotisations.reduce((acc: number[], montant: number) => {
      if (acc.length > 0) {
        acc.push(acc[acc.length - 1] + montant);
      } else {
        acc.push(montant);
      }
      return acc;
    }, []);

    this.allCumulativeReglements = completeReglements.reduce((acc: number[], montant: number) => {
      if (acc.length > 0) {
        acc.push(acc[acc.length - 1] + montant);
      } else {
        acc.push(montant);
      }
      return acc;
    }, []);

    // Mettre à jour les 10 dernières entrées pour l'affichage
    const recentLabels = this.allLabels.slice(-10);
    const recentCumulativeMontants = this.allCumulativeMontants.slice(-10);
    const recentCumulativeCotisations = this.allCumulativeCotisations.slice(-10);
    const recentCumulativeReglements = this.allCumulativeReglements.slice(-10);

    this.lineChartData = {
      labels: recentLabels,
      datasets: [
        {
          data: recentCumulativeMontants,
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
          data: recentCumulativeCotisations,
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
          data: recentCumulativeReglements,
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

    this.cumulativeCotisation = recentCumulativeCotisations[recentCumulativeCotisations.length - 1];
    this.cumulativeReglement = recentCumulativeReglements[recentCumulativeReglements.length - 1];
  }

  calculatePercentages(): void {
    const previousCotisation = this.allCumulativeCotisations[this.allCumulativeCotisations.length - 2] || 0;
    const currentCotisation = this.cumulativeCotisation;
    this.cotisationPercentage = previousCotisation !== 0
      ? ((currentCotisation - previousCotisation) / previousCotisation) * 100
      : currentCotisation !== 0 ? 100 : 0;

    const previousReglement = this.allCumulativeReglements[this.allCumulativeReglements.length - 2] || 0;
    const currentReglement = this.cumulativeReglement;
    this.reglementPercentage = previousReglement !== 0
      ? ((currentReglement - previousReglement) / previousReglement) * 100
      : currentReglement !== 0 ? 100 : 0;
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

  downloadCSV(): void {
    const csvData = this.convertToCSV(this.allLabels, [
      { data: this.allCumulativeMontants },
      { data: this.allCumulativeCotisations },
      { data: this.allCumulativeReglements }
    ]);
    const blob = new Blob([csvData], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.setAttribute('style', 'display:none');
    a.href = url;
    a.download = 'operations.csv';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  }

  private convertToCSV(labels: any[], datasets: any[]): string {
    let csv = 'Date et Heure, Montant Total, Cumul Cotisation, Cumul Règlement\n';
    for (let i = 0; i < labels.length; i++) {
      const montantTotal = datasets[0]?.data[i] ?? '';
      const cumulCotisation = datasets[1]?.data[i] ?? '';
      const cumulReglement = datasets[2]?.data[i] ?? '';

      csv += `${labels[i]}, ${montantTotal}, ${cumulCotisation}, ${cumulReglement}\n`;
    }
    return csv;
  }
}
