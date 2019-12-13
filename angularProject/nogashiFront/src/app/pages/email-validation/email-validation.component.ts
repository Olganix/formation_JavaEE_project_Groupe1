import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";


@Component({
  selector: 'app-email-validation',
  templateUrl: './email-validation.component.html',
  styleUrls: ['./email-validation.component.scss']
})
export class EmailValidationComponent implements OnInit 
{
  token = null;


  constructor(private route: ActivatedRoute) { }

  ngOnInit() 
  {
    this.token = this.route.snapshot.paramMap.get("token");   // https://medium.com/better-programming/angular-6-url-parameters-860db789db85

    console.log(this.route.snapshot.params.token);
  }
}
