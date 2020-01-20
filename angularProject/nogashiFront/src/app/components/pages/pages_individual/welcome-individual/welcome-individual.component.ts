import { Component, OnInit } from '@angular/core';
import {ConnexionService} from '../../../../services/connexion.service';

@Component({
  selector: 'app-welcome-individual',
  templateUrl: './welcome-individual.component.html',
  styleUrls: ['./welcome-individual.component.scss']
})
export class WelcomeIndividualComponent implements OnInit {

  constructor(private connexionService: ConnexionService
          ) { }

  ngOnInit() {
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
