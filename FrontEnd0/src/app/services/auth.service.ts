import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090/api'; // URL de ton backend

  constructor(private http: HttpClient, private router: Router) { }

  login(email: string, password: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = { email, password };

    return this.http.post<any>(`${this.apiUrl}/connexion`, body, { headers })
      .pipe(map(response => {
        // Stocker le token JWT dans le local storage
        localStorage.setItem('token', response.bearer);
        return response;
      }));
  }
  get token(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    // Supprimer le token JWT du local storage
    localStorage.removeItem('token');
    this.router.navigateByUrl('/login');
  }

  isLoggedIn(): boolean {
    // Vérifier si le token JWT est présent dans le local storage
    return !!localStorage.getItem('token');
  }
}
