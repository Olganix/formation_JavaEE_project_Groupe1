import { Component, OnInit } from '@angular/core';
<<<<<<< HEAD
import {ConnexionService} from '../../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {Router} from '@angular/router';
=======
>>>>>>> bbbb9d1561ffcf4d33c7f85639843cf9da747570

@Component({
  selector: 'app-compte',
  templateUrl: './compte.component.html',
  styleUrls: ['./compte.component.scss']
})
export class CompteComponent implements OnInit {

<<<<<<< HEAD
  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router
  ) { }
=======
  constructor() { }
>>>>>>> bbbb9d1561ffcf4d33c7f85639843cf9da747570

  ngOnInit() {
  }

}
