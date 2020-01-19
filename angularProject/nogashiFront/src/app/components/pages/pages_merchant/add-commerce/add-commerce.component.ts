import {Component, OnInit, Output} from '@angular/core';
import {Commerce} from '../../../../classes/commerce';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MerchantService} from '../../../../services/merchant.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {ActivatedRoute, Router} from '@angular/router';
import {RestResponse} from '../../../../classes/rest-response';

@Component({
  selector: 'app-add-commerce',
  templateUrl: './add-commerce.component.html',
  styleUrls: ['./add-commerce.component.scss']
})
export class AddCommerceComponent implements OnInit {

  @Output() commerce: Commerce;

  form1: FormGroup;

  id: FormControl;
  name: FormControl;
  codeSiret: FormControl;
  uniqueIdName: FormControl;
  description: FormControl;
  address: FormControl; // TODO
  // schedulerWeek: FormControl; // TODO
  pictureLogo: FormControl;
  pictureDescription: FormControl;
  isOpened: FormControl;
  /*
    commerceCategories: FormControl; // TODO
    productTemplates: FormControl; // TODO
    products: FormControl; // TODO
    shoppingCartByCommerces: FormControl; // TODO
   */

  constructor(private merchantService: MerchantService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router,
              private route: ActivatedRoute,
              private fb: FormBuilder
      ) { }

  ngOnInit() {

    // Todo aller recuperer les info pour l'update.


    const id = (this.route.snapshot.params.id !== '') ? Number(this.route.snapshot.params.id) : 0;

    this.id = new FormControl(id, [ Validators.required ]);
    this.name = new FormControl(null, [ Validators.required ]);
    this.codeSiret = new FormControl(null, [ Validators.required ]);
    this.uniqueIdName = new FormControl(null, [ Validators.required ]);
    this.description = new FormControl(null, [ Validators.required ]);
    this.address = new FormControl(null, []);
    // this.schedulerWeek = new FormControl(null, [ Validators.required ]);
    this.pictureLogo = new FormControl('NoLogo.jpg', [ Validators.required ]);
    this.pictureDescription = new FormControl('NoDescription.jpg', []);
    this.isOpened = new FormControl(false, [ Validators.required ]);


    this.form1 = this.fb.group({
      id : this.id,
      name : this.name,
      codeSiret : this.codeSiret,
      uniqueIdName: this.uniqueIdName,
      description: this.description,
      address: this.address,
      // schedulerWeek: this.schedulerWeek,
      pictureLogo: this.pictureLogo,
      pictureDescription: this.pictureDescription,
      isOpened: this.isOpened
    });

  }

  onSubmit() {
    if (this.form1.valid) {

      console.log('form:');
      console.log(this.form1.value);

      const c = new Commerce();
      c.setAddCommerce(this.form1.value.id, this.form1.value.name, this.form1.value.codeSiret, this.form1.value.uniqueIdName, this.form1.value.description, this.form1.value.address, this.form1.value.pictureLogo, this.form1.value.pictureDescription, this.form1.value.isOpened);

      this.merchantService.addOrUpdateCommerce( c ).subscribe(
        (rrp: RestResponse) => {

          console.log('addOrUpdateCommerce: ');
          console.log(rrp);

          if (rrp.status === 'SUCCESS') {
            this.infoBoxNotificationsService.addMessage('info', 'La sauvegarde de la fiche commerce a bien été effectuée.', 10);

            const cRet = new Commerce(rrp);
            this.router.navigate(['/commerceSheet/' + cRet.id]);

          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec de la sauvegarde de la fiche commerce : ' + rrp.errormessage, 10);
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec de la sauvegarde de la fiche commerce : ' + error, 10);
        });

      this.form1.reset();
    }
  }


  public controlName(): string {
    if (this.name.touched) {
      if (this.name.hasError('required')) {
        return `Le nom du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlcodeSiret(): string {
    if (this.codeSiret.touched) {
      if (this.codeSiret.hasError('required')) {
        return `Le code Siret du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlAddress(): string {
    if (this.address.touched) {
      if (this.address.hasError('required')) {
        return `L'adresse du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlDescription(): string {
    if (this.description.touched) {
      if (this.description.hasError('required')) {
        return `La description du commerce est obligatoire`;
      }
    }
    return null;
  }
}
