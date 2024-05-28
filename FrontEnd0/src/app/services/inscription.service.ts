import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InscriptionService {
  private apiUrl = 'http://localhost:8090/api/inscription';

  constructor(private http: HttpClient) {}

  inscrire(inscription: any): Observable<any> {
    return this.http.post(this.apiUrl, inscription, { responseType: 'text' });
  }
}
