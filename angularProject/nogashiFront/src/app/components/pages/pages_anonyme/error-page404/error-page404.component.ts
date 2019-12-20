import { Component, OnInit } from '@angular/core';
import {ConnexionService} from '../../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {Router} from '@angular/router';

@Component({
  selector: 'app-error-page404',
  templateUrl: './error-page404.component.html',
  styleUrls: ['./error-page404.component.scss']
})
export class ErrorPage404Component implements OnInit {

  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router
  ) { }

  ngOnInit() {
    this.connexionService.checkRemoteConnected();
  }

}
