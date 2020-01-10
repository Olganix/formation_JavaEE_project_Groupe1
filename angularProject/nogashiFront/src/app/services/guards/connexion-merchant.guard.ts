import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {ConnexionService} from '../connexion.service';
import {InfoBoxNotificationsService} from '../InfoBoxNotifications.services';
import {UserRole} from '../../enum/user-role.enum';

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

    if (this.connexionService.getLocalConnectedRole() === UserRole.MERCHANT) {
      return true;
    } else {
      this.infoBoxNotificationsService.addMessage('error', 'Cette section est reservé aux commerçants', 10);
      return this.router.parseUrl('/login');
    }
  }

}
