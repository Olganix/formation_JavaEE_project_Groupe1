import { Component, OnInit } from '@angular/core';
import {RestResponse} from '../../../classes/rest-response';
import {ConnexionService} from '../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../services/InfoBoxNotifications.services';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router
              ) { }

  ngOnInit() {
    this.connexionService.checkRemoteConnected();
  }


  logout() {

    this.connexionService.logout().subscribe(
      (rrp: RestResponse) => {

        console.log('header: logout');
        console.log(rrp);

        if (rrp.status === 'SUCCESS') {
          this.infoBoxNotificationsService.addMessage('info', 'Vous êtes déconnecté.', 20);
        } else {
          this.infoBoxNotificationsService.addMessage('error', 'Echec de la deconnexion : ' + rrp.errormessage, 10);
        }

        this.router.navigate(['/login']);                   // same on a fail (because is not connected), you have the same result
      },
      error => {
        console.log('Error occured', error);
        this.infoBoxNotificationsService.addMessage('error', 'Echec de la deconnexion : ' + error, 10);
      });
  }

}
