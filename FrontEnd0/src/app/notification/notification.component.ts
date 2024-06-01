import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../services/notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {
  message: string = '';
  type: string = '';

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.notificationService.currentMessage.subscribe(
      ({ text, type }) => {
        this.message = text;
        this.type = type;
      },
      error => {
        console.error('Error subscribing to notification messages', error);
      }
    );
  }

  closeNotification(): void {
    this.message = '';
    this.type = '';
  }
}
