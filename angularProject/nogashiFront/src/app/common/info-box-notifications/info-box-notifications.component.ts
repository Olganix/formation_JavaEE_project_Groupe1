import { Component, OnInit } from '@angular/core';
import { InfoBoxNotificationsService } from '../../services/InfoBoxNotifications.services';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-info-box-notifications',
  templateUrl: './info-box-notifications.component.html',
  styleUrls: ['./info-box-notifications.component.scss']
})
export class InfoBoxNotificationsComponent implements OnInit 
{
  messages: any[];
  messagesSubscription: Subscription;         //https://openclassrooms.com/fr/courses/4668271-developpez-des-applications-web-avec-angular/5089331-observez-les-donnees-avec-rxjs
  
  constructor(private infoBoxNotificationsService: InfoBoxNotificationsService) { }

  ngOnInit()
  {
    this.messagesSubscription = this.infoBoxNotificationsService.messagesSubject.subscribe(
      (messages: any[]) => {
        this.messages = messages;
      }
    );
    this.infoBoxNotificationsService.emitMessagesSubject();
  }

  ngOnDestroy() {
    this.messagesSubscription.unsubscribe();
  }

}
