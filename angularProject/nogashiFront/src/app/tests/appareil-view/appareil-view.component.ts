import { Component, OnInit } from '@angular/core';
import { AppareilService } from '../../services/appareil.service';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-appareil-view',
  templateUrl: './appareil-view.component.html',
  styleUrls: ['./appareil-view.component.scss']
})
export class AppareilViewComponent implements OnInit 
{
  appareils: any[];
  appareilSubscription: Subscription;         //https://openclassrooms.com/fr/courses/4668271-developpez-des-applications-web-avec-angular/5089331-observez-les-donnees-avec-rxjs
  
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
    this.appareilSubscription = this.appareilService.appareilsSubject.subscribe(
      (appareils: any[]) => {
        this.appareils = appareils;
      }
    );
    this.appareilService.emitAppareilSubject();
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

  ngOnDestroy() {
    this.appareilSubscription.unsubscribe();
  }
}
