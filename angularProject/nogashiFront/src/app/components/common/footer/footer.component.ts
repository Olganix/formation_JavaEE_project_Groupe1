import { Component, OnInit } from '@angular/core';
import {ConnexionService} from '../../../services/connexion.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  constructor(private connexionService: ConnexionService,
              private router: Router
  ) { }

  ngOnInit() {
  }
}
