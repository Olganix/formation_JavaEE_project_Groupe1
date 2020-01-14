import { Component, OnInit } from '@angular/core';
import { Router} from '@angular/router';
import { ConnexionService } from '../../../../services/connexion.service';
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from '@angular/forms';
import { InfoBoxNotificationsService } from '../../../../services/InfoBoxNotifications.services';
import {User} from '../../../../classes/user';
import {CustomValidators} from '../../../../validators/custom-validators';
import {RestResponse} from '../../../../classes/rest-response';
import { UserRole } from 'src/app/enum/user-role.enum';


@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  UserRole = UserRole;

  role: FormControl;
  name: FormControl;
  email: FormControl;
  password: FormControl;
  confirm: FormControl;
  term: FormControl;
  declaration: FormControl;
  newsletterEnabled: FormControl;

  form1: FormGroup;

    constructor(private connexionService: ConnexionService,
                private infoBoxNotificationsService: InfoBoxNotificationsService,
                private router: Router,
                private fb: FormBuilder
            ) { }

    ngOnInit() {


      this.role = new FormControl(UserRole.INDIVIDUAL, [
        Validators.required
      ]);
      this.name = new FormControl(null, [
        Validators.required,
        CustomValidators.nameCheck()
      ]);
      this.email = new FormControl(null, [
        Validators.required,
        CustomValidators.email()
      ]);
      this.password = new FormControl(null, [
        Validators.required,
        Validators.minLength(CustomValidators.passwordRules.minLength)
      ]);
      this.confirm = new FormControl(null, [Validators.required]);

      this.term = new FormControl(false, [Validators.requiredTrue]);
      // this.declaration = new FormControl(false, [Validators.requiredTrue]);     // Todo resolve
      this.declaration = new FormControl(false);
      this.newsletterEnabled = new FormControl(false);

      this.form1 = this.fb.group({
        role : this.role,
        name : this.name,
        email : this.email,
        password: this.password,
        confirm: this.confirm,
        term: this.term,
        declaration: this.declaration,
        newsletterEnabled: this.newsletterEnabled
      }, {
        validators: CustomValidators.match_password()
      });
    }


  onSubmit() {
        if (this.form1.valid) {

          console.log('form:');
          console.log(this.form1.value);

          const user = new User();
          user.setSignin(this.form1.value.name, this.form1.value.password, this.form1.value.email, +this.form1.value.role, this.form1.value.newsletterEnabled);

          this.connexionService.signIn( user ).subscribe(
            (rrp: RestResponse) => {

              console.log('component.signIn: ');
              console.log(rrp);


              if (rrp.status === 'SUCCESS') {
                this.infoBoxNotificationsService.addMessage('info', 'Votre inscription est bien prise en compte. Veuilliez consulter vos mails afin de repondre à la validation de votre email. Merci.', 10);
                this.router.navigate(['/login']);

              } else {
                this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'inscription : ' + rrp.errormessage, 10);
              }
            },
            error => {
              console.log('Error occured', error);
              this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'inscription : ' + error, 10);
            });

          this.form1.reset();
        }
    }





  public controlName(): string {
    if (this.name.touched) {
      if (this.name.hasError('required')) {
        return `Le pseudo est obligatoire`;
      }

      if (this.name.hasError('error_name')) {
        return `pseudo non valide`;
      }
    }
    return null;
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

  public controlTerm(): string {
    if (this.term.dirty) {
      if (this.term.hasError('required')) {   // same if it's requiredTrue
        return `Les Conditions générales d'Utilisation sont obligatoires`;
      }
      return null;
    }
  }

  public controlDeclaration(): string {
      if ((+this.role.value === UserRole.MERCHANT) || (+this.role.value === UserRole.ASSOCIATION) ) {
        if (this.declaration.dirty) {
          if (this.declaration.hasError('required')) {   // same if it's requiredTrue
            return `La Déclaration sur l'Honneur est obligatoires`;
          }
        }
      }
      return null;
  }

}
