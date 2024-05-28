import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Banque} from "../models/banque.model";

@Injectable({
  providedIn: 'root'
})
export class BanqueService {
  private apiUrl = 'http://localhost:8090/api/banque';

  constructor(private http: HttpClient) { }

  getBanques(): Observable<Banque[]> {
    return this.http.get<Banque[]>(this.apiUrl);
  }

  getBanqueById(id: number): Observable<Banque> {
    return this.http.get<Banque>(`${this.apiUrl}/${id}`);
  }

  createBanque(banque: Banque): Observable<Banque> {
    return this.http.post<Banque>(this.apiUrl, banque);
  }

  updateBanque(banque: Banque): Observable<Banque> {
    return this.http.put<Banque>(`${this.apiUrl}/${banque.id}`, banque);
  }

  deleteBanque(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
