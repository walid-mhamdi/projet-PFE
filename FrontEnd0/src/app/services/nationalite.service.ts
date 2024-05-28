import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Nationalite } from '../models/nationalite.model';

@Injectable({
  providedIn: 'root'
})
export class NationaliteService {

  private baseUrl = 'http://localhost:8090/api/nationalite';

  constructor(private http: HttpClient) { }

  getNationalites(): Observable<Nationalite[]> {
    return this.http.get<Nationalite[]>(this.baseUrl);
  }
  updateNationalite(nationalite: Nationalite): Observable<Nationalite> {
    return this.http.put<Nationalite>(`${this.baseUrl}/${nationalite.id}`, nationalite);
  }


  getNationaliteById(id: number): Observable<Nationalite> {
    return this.http.get<Nationalite>(`${this.baseUrl}/${id}`);
  }

  createNationalite(nationalite: Nationalite): Observable<Nationalite> {
    return this.http.post<Nationalite>(this.baseUrl, nationalite);
  }

  deleteNationalite(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
