import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ConnexionService} from '../../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {CustomValidators} from '../../../../validators/custom-validators';
import {User} from '../../../../classes/user';
import {RestResponse} from '../../../../classes/rest-response';

@Component({
  selector: 'app-password-rescue-modification',
  templateUrl: './password-rescue-modification.component.html',
  styleUrls: ['./password-rescue-modification.component.scss']
})
export class PasswordRescueModificationComponent implements OnInit {

  token: string = null;

  password: FormControl;
  confirm: FormControl;

  form1: FormGroup;

  constructor(private route: ActivatedRoute,
              private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router,
              private fb: FormBuilder
  ) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe( (queryparam: ParamMap) => {
      this.token =  queryparam.get('token');
      console.log(this.token);
    });

    this.password = new FormControl(null, [
      Validators.required,
      Validators.minLength(CustomValidators.passwordRules.minLength)
    ]);
    this.confirm = new FormControl(null, [Validators.required]);

    this.form1 = this.fb.group({
      password: this.password,
      confirm: this.confirm,
    }, {
      validators: CustomValidators.match_password()
    });
  }


  onSubmit(e) {
    e.preventDefault();
    e.stopPropagation();

    if (this.form1.valid) {

      if (this.token === null) {
        this.infoBoxNotificationsService.addMessage('error', 'Error : Donnée de la page invalide.', 10);
        this.form1.reset();
        return false;
      }



      const user = new User();
      user.password = this.form1.value.password;
      user.token = this.token;

      this.connexionService.passwordRescueModification( user ).subscribe(
        (rrp: RestResponse) => {

          console.log('component.passwordRescueModification: ');
          console.log(rrp);

          if (rrp.status === 'SUCCESS') {
            this.infoBoxNotificationsService.addMessage('info', 'Votre mot de passe a bien été modifié.', 10);
            this.router.navigate(['/login']);

          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'enregistrement du nouveau mot de passe : ' + rrp.errormessage, 10);
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'enregistrement du nouveau mot de passe : ' + error, 10);
        });

      this.form1.reset();
    }
    return false;
  }





  public controlPassword(): string {
    if (this.password.touched) {
      if (this.password.hasError('required')) {
        return `Le mot de passe est obligatoire`;
      }

      if (this.password.hasError('minlength')) {
        return `La password doit contenir au moins ${CustomValidators.passwordRules.minLength} caractères. or il n'y en a ${this.password.value.length}.`;
      }
    }
    return null;
  }

  public controlPasswordConfirm(): string {
    if (this.confirm.touched) {
      if (this.confirm.hasError('required')) {
        return `La confirmation du mot de passe est obligatoire`;
      }

      if (this.form1.hasError('match_password')) {
        return `La confirmation ne correspond pas au password`;
      }
    }
    return null;
  }

}
