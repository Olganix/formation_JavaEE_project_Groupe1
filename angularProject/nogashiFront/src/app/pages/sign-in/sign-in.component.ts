import { Component, OnInit } from '@angular/core';
import { Router} from '@angular/router';
import { ConnexionService } from '../../services/connexion.service';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit 
{

    constructor(private connexionService: ConnexionService,
            private router: Router
            ) { }

    ngOnInit() 
    {

    }

    onSubmit(form: NgForm) 
    {
        console.log(form.value);

        this.connexionService.signIn(form.value['name'], form.value['password'], form.value['email'], form.value['role'], form.value['newsletterEnabled'])
        .then(()=>
        {
            //todo information success, l'email validation dans un component de common qui retour des notifications.
            this.router.navigate(['/login']);

        }).catch( () =>
        {
            //todo information de fail component de common qui retour des notifications.
        });
    }
}
