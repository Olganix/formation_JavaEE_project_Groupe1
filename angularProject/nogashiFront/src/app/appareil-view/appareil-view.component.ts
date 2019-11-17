import { Component, OnInit } from '@angular/core';
import { AppareilService } from '../services/appareil.service';


@Component({
  selector: 'app-appareil-view',
  templateUrl: './appareil-view.component.html',
  styleUrls: ['./appareil-view.component.scss']
})
export class AppareilViewComponent implements OnInit 
{
  isAuth = false;
  appareils: any[];
  
  lastUpdate = new Promise((resolve, reject) =>          //after that lastUpdate is not a Date, but a Promise.
  {
    const date = new Date();
    setTimeout(() => 
    {
      resolve(date);
    }, 2000);
  }).then(function(value) {
    return value;
  });




  constructor(private appareilService: AppareilService) 
  {
    
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
