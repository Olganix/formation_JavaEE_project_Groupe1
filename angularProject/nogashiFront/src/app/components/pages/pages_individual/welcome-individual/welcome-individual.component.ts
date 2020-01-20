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

}
