import { Component, OnInit } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { ConnexionService } from '../../services/connexion.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  isfail: boolean; 

  constructor(private connexionService: ConnexionService,
              private router: Router) { }

  ngOnInit() {
  }

  signIn(name: String, password : String, email : String)
  {
    this.isfail = false;
    if(this.connexionService.signIn(name, password, email))
      this.router.navigate(['/login']);
    else
      this.isfail = true;

  }

}
