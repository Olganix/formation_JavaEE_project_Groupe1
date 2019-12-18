import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {ConnexionService} from '../connexion.service';
import {InfoBoxNotificationsService} from '../InfoBoxNotifications.services';

@Injectable({
  providedIn: 'root'
})
export class ConnexionMerchantGuard implements CanActivate {

  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.connexionService.getLocalConnectedRole() === 'MERCHANT') {
      return true;
    } else {
      this.infoBoxNotificationsService.addMessage('error', 'Cette section est reservé aux commerçants', 10);
      return this.router.parseUrl('/login');
    }
  }

}
