import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090/api'; // URL de votre backend

  constructor(private http: HttpClient, private router: Router) { }

  login(email: string, password: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = { email, password };

    return this.http.post<any>(`${this.apiUrl}/connexion`, body, { headers })
      .pipe(
        map(response => {
          localStorage.setItem('token', response.bearer);
          return response;
        }),
        catchError(error => {
          return throwError(() => new Error(error.error.message || 'Erreur de connexion'));
        })
      );
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigateByUrl('/login');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getUserName(): string | null {
    const token = this.token;
    if (token) {
      const decodedToken: any = jwt_decode(token);
      return decodedToken.nom; // Assurez-vous que le nom de l'utilisateur est inclus dans le token JWT
    }
    return null;
  }

  resetPassword(email: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = { email };

    return this.http.post<any>(`${this.apiUrl}/modifie-mot-de-passe`, body, { headers });
  }
}
