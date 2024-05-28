import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Operation} from "../models/operation.model";

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  constructor(private http: HttpClient) { }

  getAllOperations() {
    return this.http.get<Operation[]>('http://localhost:8090/api/operations');
  }

  saveOperation(operation: Operation) {
    return this.http.post<Operation>('http://localhost:8090/api/operations', operation);
  }
}
