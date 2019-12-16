import { Component, OnInit } from '@angular/core';
import { Router} from '@angular/router';
import { ConnexionService } from '../../../../services/connexion.service';
import { NgForm } from '@angular/forms';
import { InfoBoxNotificationsService } from '../../../../services/InfoBoxNotifications.services';


@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit
{

    constructor(private connexionService: ConnexionService,
                private infoBoxNotificationsService: InfoBoxNotificationsService,
                private router: Router
            ) { }

    ngOnInit()
    {

    }

    //todo voir pour les validations des champs, l'affichage des errors, voir aussi du coté de bootstrap qui propose des trucs.
    onSubmit(form: NgForm)
    {
        console.log(form.value);

        this.connexionService.signIn(form.value['name'], form.value['password'], form.value['email'], form.value['role'], form.value['newsletterEnabled'])
        .then(()=>
        {
            this.infoBoxNotificationsService.addMessage("info", "Votre inscription est bien prise en compte. Veuilliez consulter vos mails afin de repondre à la validation de votre email. Merci.", 10);
            this.router.navigate(['/login']);

        }).catch( (error) =>
        {
            this.infoBoxNotificationsService.addMessage("error", "Echec de l'inscription : "+ error, 10);
        });
    }
}
