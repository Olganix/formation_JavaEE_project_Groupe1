import { Component, OnInit } from '@angular/core';
import { Router} from '@angular/router';
import { ConnexionService } from '../../../../services/connexion.service';
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from '@angular/forms';
import { InfoBoxNotificationsService } from '../../../../services/InfoBoxNotifications.services';
import {CustomValidators} from '../../../../validators/custom-validators';
import {User} from '../../../../classes/user';
import {RestResponse} from '../../../../classes/rest-response';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  name: FormControl;
  password: FormControl;

  form1: FormGroup;

  userEmailNotValidate: User = null;

  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router,
              private fb: FormBuilder
              ) { }

  ngOnInit() {

    this.name = new FormControl(null, [ Validators.required ]);
    this.password = new FormControl(null, [ Validators.required, Validators.minLength(CustomValidators.passwordRules.minLength) ]);

    this.form1 = this.fb.group({
      name : this.name,
      password: this.password
    });
  }



  onSubmit() {

    console.log(this.form1.value);

    if (this.form1.valid) {

      const user = new User();
      user.setLogin(this.form1.value.name, this.form1.value.password);

      this.connexionService.login( user ).subscribe(
        (rrp: RestResponse) => {

          console.log('component.login: ');
          console.log(rrp);


          if (rrp.status === 'SUCCESS') {

            const userRet = new User(rrp.data);
            switch (userRet.role) {
              case 'INDIVIDUAL': this.router.navigate(['/individual/welcome']); break;
              case 'MERCHANT': this.router.navigate(['/merchant/welcome']); break;
              case 'ASSOCIATION': this.router.navigate(['/association/welcome']); break;
              case 'ADMIN': this.router.navigate(['/admin/welcome']); break;
            }

          } else {

            if (rrp.errorCode === 2) {                       // email not valid

              this.userEmailNotValidate = new User(rrp.data);
              this.infoBoxNotificationsService.addMessage('error', 'Echec de la connexion car votre email n\'est pas validé.<br>Veuilliez vous référer au message en dessous du bouton "connexion"', 20);

            } else {
              this.infoBoxNotificationsService.addMessage('error', 'Echec de la connexion : ' + rrp.errormessage, 10);
            }
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec de la connexion : ' + error, 10);
        });

      this.form1.reset();
    }
  }

  sendEmailValidation() {

    this.connexionService.sendEmailValidation( this.userEmailNotValidate ).subscribe(
      (rrp: RestResponse) => {

        console.log('component.sendemailvalidation: ');
        console.log(rrp);

        if (rrp.status === 'SUCCESS') {
          this.infoBoxNotificationsService.addMessage('info', 'Un email de validation vous a été envoyé.', 20);
        } else {
          this.infoBoxNotificationsService.addMessage('error', 'Echec l\'envois du mail de validation. êtes-vous sur que votre email est bon ?', 10);
        }
      },
      error => {
        console.log('Error occured', error);
        this.infoBoxNotificationsService.addMessage('error', 'Echec l\'envois du mail de validation : ' + error, 10);
      });
  }

  public controlName(): string {
    if (this.name.touched) {
      if (this.name.hasError('required')) {
        return `Le pseudo (ou Email) est obligatoire`;
      }
    }
    return null;
  }

  public controlPassword(): string {
    if (this.password.touched) {
      if (this.password.hasError('required')) {
        return `Le mot de passe est obligatoire`;
      }
    }
    return null;
  }

}
