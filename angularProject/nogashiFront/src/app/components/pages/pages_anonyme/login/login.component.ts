import { Component, OnInit } from '@angular/core';
import { Router} from '@angular/router';
import { ConnexionService } from '../../../../services/connexion.service';
import { NgForm } from '@angular/forms';
import { InfoBoxNotificationsService } from '../../../../services/InfoBoxNotifications.services';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router
              ) { }

  ngOnInit() { }


  //todo voir pour les validations des champs, l'affichage des errors, voir aussi du coté de bootstrap qui propose des trucs.
  onSubmit(form: NgForm)
  {
      console.log(form.value);

      this.connexionService.login(form.value['login'], form.value['password'])
      .then((user)=>
      {
          switch(user["role"])
          {
              case "INDIVIDUAL": this.router.navigate(['/individual/welcome']); break;
              case "MERCHANT": this.router.navigate(['/merchant/welcome']); break;
              case "ASSOCIATION": this.router.navigate(['/association/welcome']); break;
              case "ADMIN": this.router.navigate(['/admin/welcome']); break;
          }

      }).catch( (error) =>
      {
          if(error=="Email not validate")
            this.infoBoxNotificationsService.addMessage("error", "Pour vous connecter vous devez d'abord valider votre adresse email.<br>Vous pouvez le faire en cliquant sur le lien à l'intérieur de l'email envoyé.<br>Si vous ne trouvez pas ce mail, veuilliez regarder dans vos courriers indésirables, ou sinon <a>cliquer sur ce lien pour renvoyer le mail</a> "+ error, 20);
          else
            this.infoBoxNotificationsService.addMessage("error", "Echec de connexion : "+ error, 10);
      });
  }

}
