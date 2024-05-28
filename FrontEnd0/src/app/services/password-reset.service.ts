import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PasswordResetService {
  private apiUrl = 'http://localhost:8090/api';  // Replace with your backend URL

  constructor(private http: HttpClient) {}

  resetPassword(email: string, code: string, password: string): Observable<any> {
    const body = { email, code, password };
    return this.http.post(`${this.apiUrl}/nouveau-mot-de-passe`, body);

  }

}
