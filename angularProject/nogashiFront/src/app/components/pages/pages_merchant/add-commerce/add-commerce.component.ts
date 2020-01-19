import {Component, OnInit, Output} from '@angular/core';
import {Commerce} from '../../../../classes/commerce';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MerchantService} from '../../../../services/merchant.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {ActivatedRoute, Router} from '@angular/router';
import {RestResponse} from '../../../../classes/rest-response';
import {ProductTemplate} from '../../../../classes/product-template';
import {UserService} from '../../../../services/user.service';

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
  private defaultLogo = 'NoLogo.jpg';
  pictureDescription: FormControl;
  private defaultDescription = 'NoDescription.jpg';
  isOpened: FormControl;


  /*
    commerceCategories: FormControl; // TODO
    productTemplates: FormControl; // TODO
    products: FormControl; // TODO
    shoppingCartByCommerces: FormControl; // TODO
   */

  constructor(private merchantService: MerchantService,
              private userService: UserService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router,
              private route: ActivatedRoute,
              private fb: FormBuilder
      ) { }

  ngOnInit() {
    const id = (this.route.snapshot.params.id !== '') ? Number(this.route.snapshot.params.id) : 0;

    this.id = new FormControl(id, [ Validators.required ]);
    this.name = new FormControl(null, [ Validators.required ]);
    this.codeSiret = new FormControl(null, [ Validators.required ]);
    this.uniqueIdName = new FormControl(null, [ Validators.required ]);
    this.description = new FormControl(null, [ Validators.required ]);
    this.address = new FormControl(null, []);
    // this.schedulerWeek = new FormControl(null, [ Validators.required ]);
    this.pictureLogo = new FormControl(null, [ Validators.required ]);
    this.pictureDescription = new FormControl(null, [ Validators.required ]);
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

    // recuperation les info pour l'update.
    if (id !== 0) {
      if ((this.merchantService.lastCommerce) && (this.merchantService.lastCommerce.id === id)) {               // on a gardé le dernier pour eviter les problemes dans les changement de page.
        this.__setFormData(this.merchantService.lastCommerce);
      } else {

        this.userService.getCommerceById(id).subscribe(    // le + c'est pour caster un string en number
          (rrp: RestResponse) => {

            if (rrp.status === 'SUCCESS') {
              this.__setFormData(new Commerce(rrp.data));
            } else {
              console.log('Echec de la recuperation de la liste des fiches produits : ' + rrp.errormessage);
            }
          },
          error => {
            console.log('Echec de la recuperation de la liste des fiches produits : ', error);
          });
      }
    }
  }


  private __setFormData(c: Commerce) {

    this.name.setValue(c.name);
    this.codeSiret.setValue(c.codeSiret);
    this.uniqueIdName.setValue(c.uniqueIdName);
    this.description.setValue(c.description);

    // this.address.setValue(c.address); // TODO

    this.defaultLogo = c.pictureLogo;
    this.defaultDescription = c.pictureDescription;
    this.isOpened.setValue(c.isOpened);
  }

  onSubmit() {
    if (this.form1.valid) {

      console.log('form:');
      console.log(this.form1.value);



      const c = new Commerce();
      c.setAddCommerce(this.form1.value.id, this.form1.value.name, this.form1.value.codeSiret, this.form1.value.uniqueIdName, this.form1.value.description, this.form1.value.address, ((this.form1.value.pictureLogo !== null) && (this.form1.value.pictureLogo.trim() !== '') ) ? this.form1.value.pictureLogo : this.defaultLogo, ((this.form1.value.pictureDescription !== null) && (this.form1.value.pictureDescription.trim() !== '') ) ? this.form1.value.pictureDescription : this.defaultDescription, this.form1.value.isOpened);

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

  public controlPictureLogo(): string {
    if (this.name.touched) {
      if (this.name.hasError('required')) {
        return `Le logo du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlPictureDescription(): string {
    if (this.pictureDescription.touched) {
      if (this.pictureDescription.hasError('required')) {
        return `L'image de description du commerce est obligatoire`;
      }
    }
    return null;
  }
}
