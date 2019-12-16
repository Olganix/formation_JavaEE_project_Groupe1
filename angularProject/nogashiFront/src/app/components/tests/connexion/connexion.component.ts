import { Component, OnInit } from '@angular/core';
import { ConnexionService } from '../../../services/connexion.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-connexion',
  templateUrl: './connexion.component.html',
  styleUrls: ['./connexion.component.scss']
})
export class ConnexionComponent implements OnInit
{

  authStatus: boolean;

  constructor(private connexionService: ConnexionService,
              private router: Router) { }

  ngOnInit()
  {
    this.authStatus = this.connexionService.isAuth;
  }

  onSignIn()
  {
    this.connexionService.signIn_fake().then(() =>
    {
      console.log('Sign in successful!');
      this.authStatus = this.connexionService.isAuth;
      this.router.navigate(['appareils']);
    });
  }

  onSignOut()
  {
    this.connexionService.signOut();
    this.authStatus = this.connexionService.isAuth;
  }
}
