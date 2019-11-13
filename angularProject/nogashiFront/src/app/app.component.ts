import { Component, OnInit } from '@angular/core';
import { AppareilService } from './services/appareil.service';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'NoGashi';
  isAuth = false;

  appareils: any[];
  users: any;

  lastUpdate;

  readonly APP_URL = 'http://localhost:8080/nogashi';

  constructor(private appareilService: AppareilService, private _http: HttpClient) 
  {
    setTimeout(() =>
    {
      this.isAuth = true;                     //simulation of connection
    }, 4000);

    this.lastUpdate = new Promise((resolve, reject) =>          //after that lastUpdate is not a Date, but a Promise.
    {
      const date = new Date();
      setTimeout(() => 
      {
        resolve(date);
      }, 2000);
    }).then(function(value) {
      return value;
    });

    this._http.get(this.APP_URL + '/getUsers').subscribe( 
      data => 
      {
        this.users = data;
      },
      error => 
      {
        console.log('Error occured', error);
      }
    );
  }

  ngOnInit() 
  {
    this.appareils = this.appareilService.appareils;
  }


  onAllumer() 
  {
    this.appareilService.switchAllAppareil(true);
  }
  onEteindre() 
  {
    if(confirm('Etes-vous sûr de vouloir éteindre tous vos appareils ?'))
      this.appareilService.switchAllAppareil(false);
  }

  
}

