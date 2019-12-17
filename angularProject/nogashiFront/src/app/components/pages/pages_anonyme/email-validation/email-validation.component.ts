import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {RestResponse} from '../../../../classes/rest-response';
import {ConnexionService} from '../../../../services/connexion.service';


@Component({
  selector: 'app-email-validation',
  templateUrl: './email-validation.component.html',
  styleUrls: ['./email-validation.component.scss']
})
export class EmailValidationComponent implements OnInit {

  token = null;
  haveRet = false;
  isValidate = false;

  constructor(private route: ActivatedRoute,
              private connexionService: ConnexionService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe( (queryparam: ParamMap) => {

      this.token =  queryparam.get('token');
      console.log(this.token);

      this.connexionService.emailValidation( this.token ).subscribe(
        (rrp: RestResponse) => {

          console.log('component.emailValidation: ');
          console.log(rrp);

          this.haveRet = true;
          this.isValidate = (rrp.status === 'SUCCESS');
        },
        error => {
          console.log('Error occured', error);
          this.haveRet = true;
          this.isValidate = false;
        });

    });

    /*
    // marche pas Todo a virer.
    this.token = this.route.snapshot.paramMap.get("token");   // https://medium.com/better-programming/angular-6-url-parameters-860db789db85
    console.log(this.route.snapshot.params.token);

    this.route.fragment.subscribe( (token: string) => {
      this.token =  token;
      console.log(this.token);
    });
    */
  }
}
