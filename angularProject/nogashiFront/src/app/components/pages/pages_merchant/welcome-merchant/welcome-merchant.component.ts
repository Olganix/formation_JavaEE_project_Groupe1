import { Component, OnInit } from '@angular/core';
import {ConnexionService} from '../../../../services/connexion.service';

@Component({
  selector: 'app-welcome-merchant',
  templateUrl: './welcome-merchant.component.html',
  styleUrls: ['./welcome-merchant.component.scss']
})
export class WelcomeMerchantComponent implements OnInit {

  Math = Math;
  sectionDisplayed = -1; // -1 = tout ; 0 = mes commerces ; 1 = mes commandes ; 2 = mes produits ; 3 = mes employes

  constructor() { }

  ngOnInit() {
    this.sectionDisplayed = -1;
  }

  scrollToTop() {
    const scrollToTop = window.setInterval(() => {
      const pos = window.pageYOffset;
      if (pos > 0) {
        window.scrollTo(0, pos - 250); // how far to scroll on each step
      } else {
        window.clearInterval(scrollToTop);
      }
    }, 16);
  }
}
