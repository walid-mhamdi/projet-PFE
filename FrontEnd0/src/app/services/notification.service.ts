import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private subject = new Subject<string>();

  constructor() { }

  getMessage(): Subject<string> {
    return this.subject;
  }

  sendMessage(message: string): void {
    this.subject.next(message);
  }
}
