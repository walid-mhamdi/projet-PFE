import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ville } from '../models/ville.model';

@Injectable({
  providedIn: 'root'
})
export class VilleService {
  private apiUrl = 'http://localhost:8090/api/ville';

  constructor(private http: HttpClient) { }

  getVilles(): Observable<Ville[]> {
    return this.http.get<Ville[]>(this.apiUrl);
  }

  getVilleById(id: number): Observable<Ville> {
    return this.http.get<Ville>(`${this.apiUrl}/${id}`);
  }

  createVille(ville: Ville): Observable<Ville> {
    return this.http.post<Ville>(this.apiUrl, ville);
  }

  updateVille(ville: Ville): Observable<Ville> {
    return this.http.put<Ville>(`${this.apiUrl}/${ville.id}`, ville);
  }

  deleteVille(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
