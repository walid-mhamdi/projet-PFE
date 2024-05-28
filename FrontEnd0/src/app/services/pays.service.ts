import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pays } from '../models/pays.model';

@Injectable({
  providedIn: 'root'
})
export class PaysService {
  private apiUrl = 'http://localhost:8090/api/pays';

  constructor(private http: HttpClient) { }

  getAllPays(): Observable<Pays[]> {
    return this.http.get<Pays[]>(this.apiUrl);
  }

  getPaysById(id: number): Observable<Pays> {
    return this.http.get<Pays>(`${this.apiUrl}/${id}`);
  }

  createPays(pays: Pays): Observable<Pays> {
    return this.http.post<Pays>(this.apiUrl, pays);
  }

  updatePays(pays: Pays): Observable<Pays> {
    return this.http.put<Pays>(`${this.apiUrl}/${pays.id}`, pays);
  }

  deletePays(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
