import { Component, OnInit } from '@angular/core';
import {ConnexionService} from '../../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {Router} from '@angular/router';

@Component({
  selector: 'app-compte',
  templateUrl: './compte.component.html',
  styleUrls: ['./compte.component.scss']
})
export class CompteComponent implements OnInit {

  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router
  ) { }

  ngOnInit() {
  }

}
