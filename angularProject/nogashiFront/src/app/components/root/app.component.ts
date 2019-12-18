import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit 
{
  title = 'NoGashi';

  
  constructor() 
  {
      //TODO: voir comment , dans angular, changer le title de l'onglet quand on change de page
  }

  ngOnInit() 
  {

  }

  ngOnDestroy() 
  {

  }
}

