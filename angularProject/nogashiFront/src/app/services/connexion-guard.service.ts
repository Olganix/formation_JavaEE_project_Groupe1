import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { ConnexionService } from '../services/connexion.service';
import { Injectable } from '@angular/core';



@Injectable()
export class ConnexionGuard implements CanActivate 
{
    constructor(private connexionService: ConnexionService,
                private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean 
    {
        if(this.connexionService.isAuth) 
        {
            return true;
        } else {
            this.router.navigate(['/connexion']);
        }
    }
}