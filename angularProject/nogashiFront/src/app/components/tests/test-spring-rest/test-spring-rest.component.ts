import { Component, OnInit } from '@angular/core';
import { ConnexionService } from '../../../services/connexion.service';

@Component({
  selector: 'app-test-spring-rest',
  templateUrl: './test-spring-rest.component.html',
  styleUrls: ['./test-spring-rest.component.scss']
})

//test communication spring MVC

export class TestSpringRestComponent implements OnInit
{
  users: any;

  constructor(private connexionService: ConnexionService)
  {

  }

  ngOnInit()
  {
    this.connexionService.getUsers().then( (users) =>
    {
      this.users = users;
    });
  }
}
