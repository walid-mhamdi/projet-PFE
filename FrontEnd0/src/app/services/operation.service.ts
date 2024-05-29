import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Operation } from '../models/operation.model';

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  private apiUrl = 'http://localhost:8090/api/operations';

  constructor(private http: HttpClient) { }

  getAllOperations(): Observable<Operation[]> {
    return this.http.get<Operation[]>(this.apiUrl);
  }

  saveOperation(operation: Operation): Observable<Operation> {
    return this.http.post<Operation>(this.apiUrl, operation);
  }
}
