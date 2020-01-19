import { Component, OnInit } from '@angular/core';
import {ConnexionService} from '../../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from '../../../../validators/custom-validators';
import {User} from '../../../../classes/user';
import {RestResponse} from '../../../../classes/rest-response';

@Component({
  selector: 'app-password-rescue',
  templateUrl: './password-rescue.component.html',
  styleUrls: ['./password-rescue.component.scss']
})
export class PasswordRescueComponent implements OnInit {

  email: FormControl;

  form1: FormGroup;

  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private fb: FormBuilder) { }

  ngOnInit() {

    this.email = new FormControl(null, [
      Validators.required,
      CustomValidators.email()
    ]);

    this.form1 = this.fb.group({
      email : this.email,
    });

  }



  onSubmit(e) {
    e.preventDefault();
    e.stopPropagation();

    if (this.form1.valid) {

      const user = new User();
      user.setSignin('', '', this.form1.value.email, 0, false);

      this.connexionService.passwordRescue( user ).subscribe(
        (rrp: RestResponse) => {

          if (rrp.status === 'SUCCESS') {
            this.infoBoxNotificationsService.addMessage('info', 'Un email vous a été envoyé pour pouvoir changer votre mot de passe', 10);
          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'envois d\'un email pour changer votre mot de passe : ' + rrp.errormessage, 10);
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'envois d\'un email pour changer votre mot de passe : ' + error, 10);
        });

      this.form1.reset();
    }
    return false;
  }


  public controlEmail(): string {
    if (this.email.touched) {
      if (this.email.hasError('required')) {
        return `L'adresse email est obligatoire`;
      }

      if (this.email.hasError('error_email')) {
        return `L'adresse email n'est pas valide`;
      }
    }
    return null;
  }


}
