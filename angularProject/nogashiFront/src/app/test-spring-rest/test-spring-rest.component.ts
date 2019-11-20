import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-test-spring-rest',
  templateUrl: './test-spring-rest.component.html',
  styleUrls: ['./test-spring-rest.component.scss']
})

//test communication spring MVC

export class TestSpringRestComponent implements OnInit {

  
  users: any;
  readonly APP_URL = 'http://localhost:8080/nogashi';


  constructor( private _http: HttpClient) 
  {

    this._http.get(this.APP_URL + '/getUsers').subscribe(
      data => 
      {
        this.users = ("data" in data) ? data["data"] : data;
      },
      error => 
      {
        console.log('Error occured', error);
      }
    );

  }

  ngOnInit() 
  {
    
  }
}
