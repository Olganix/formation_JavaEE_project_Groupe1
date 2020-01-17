import {Component, OnInit, Output} from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ConnexionService} from '../../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {Router} from '@angular/router';
import {User} from '../../../../classes/user';
import {RestResponse} from '../../../../classes/rest-response';

@Component({
  selector: 'app-add-product-template',
  templateUrl: './add-product-template.component.html',
  styleUrls: ['./add-product-template.component.scss']
})
export class AddProductTemplateComponent implements OnInit {

  @Output() productTemplate: ProductTemplate;

  id: FormControl;
  name: FormControl;
  image: FormControl;
  price: FormControl;
  salePrice: FormControl;
  schedulerWeekForSaleAndUnsold: FormControl;
  description: FormControl;
  isPackaged: FormControl;

  form1: FormGroup;

  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router,
              private fb: FormBuilder
        ) { }

  ngOnInit() {
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
            this.infoBoxNotificationsService.addMessage('info', 'L\'ajout de la fiche produit a bien été effectuée.', 10);
            this.router.navigate(['/login']);

          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'ajout de la fiche produit : ' + rrp.errormessage, 10);
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'ajout de la fiche produit : ' + error, 10);
        });

      this.form1.reset();
    }
  }

}
