import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private messageSource = new Subject<{ text: string, type: string }>();
  currentMessage = this.messageSource.asObservable();

  changeMessage(message: string, type: string) {
    this.messageSource.next({ text: message, type: type });
  }
}
