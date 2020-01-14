import { Component, OnInit } from '@angular/core';
import { ConnexionService } from '../../../services/connexion.service';
import {RestResponse} from '../../../classes/rest-response';
import {environment} from '../../../../environments/environment';
import {log} from 'util';
import {InfoBoxNotificationsService} from '../../../services/InfoBoxNotifications.services';
import {ProductTemplate} from '../../../classes/product-template';
import {MerchantService} from '../../../services/merchant.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from '../../../validators/custom-validators';
import {User} from '../../../classes/user';
import {Commerce} from '../../../classes/commerce';
import {Address} from '../../../classes/address';
import { UserRole } from 'src/app/enum/user-role.enum';
@Component({
  selector: 'app-test-spring-rest',
  templateUrl: './test-spring-rest.component.html',
  styleUrls: ['./test-spring-rest.component.scss']
})

// test communication spring MVC, also test for basic mechanic.


export class TestSpringRestComponent implements OnInit {

  UserRole = UserRole;


  users: any;
  merchants: any;
  listProductsTemplates: any;
  commerces: any;


  // formAddCommerce
  commerce_isOpened: FormControl;
  commerce_name: FormControl;
  commerce_codeSiret: FormControl;
  commerce_uniqueIdName: FormControl;
  commerce_description: FormControl;
  commerce_pictureLogo: FormControl;
  commerce_pictureDescription: FormControl;
  commerce_address: FormControl;
  commerce_addressExtra: FormControl;
  commerce_postalCode: FormControl;
  commerce_cityName: FormControl;
  commerce_stateName: FormControl;
  commerce_longitude: FormControl;
  commerce_latitude: FormControl;

  formAddCommerce: FormGroup;


  constructor(private infoBoxNotificationsService: InfoBoxNotificationsService,
              private connexionService: ConnexionService,
              private merchantService: MerchantService,
              private fb: FormBuilder
              ) { }


  ngOnInit() {
    this.connexionService.getUsers().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.users = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des users : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des users : ', error);
      });



    this.connexionService.getMerchants().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.merchants = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des merchants : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des merchants : ', error);
      });


    // Listing des fiches produits de la BDD
    this.connexionService.getProductsTemplates().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.listProductsTemplates = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des fiches produits : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des fiches produits : ', error);
      });


    // Commerces                        // TiynyMCE: https://www.npmjs.com/package/@tinymce/tinymce-angular  https://www.tiny.cloud/docs/quick-start/
    this.merchantService.getCommerces().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.commerces = rrp.data;
        } else {

          console.log('Echec de la recuperation de la liste des Commerces : ' + rrp.errormessage);

          if (rrp.errorCode === 5) {                        // you have to be connected as Merchant to get is Commerce
            this.infoBoxNotificationsService.addMessage('warn', 'Vous devez connecté en tant que Commerçant pour voir la liste de vos commerces', 10);
          }
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des Commerces : ', error);
      });

    this.commerce_isOpened = new FormControl(0);
    this.commerce_name = new FormControl(null, [ Validators.required ]);
    this.commerce_codeSiret = new FormControl(null, [ Validators.required ]);
    this.commerce_uniqueIdName = new FormControl(null);
    this.commerce_description = new FormControl(null, [ Validators.required ]);
    this.commerce_pictureLogo = new FormControl(null, [ Validators.required ]);
    this.commerce_pictureDescription = new FormControl(null, [ Validators.required ]);
    this.commerce_address = new FormControl(null, [ Validators.required ]);
    this.commerce_addressExtra = new FormControl(null);
    this.commerce_postalCode = new FormControl(null, [ Validators.required ]);
    this.commerce_cityName = new FormControl(null, [ Validators.required ]);
    this.commerce_stateName = new FormControl('France', [ Validators.required ]);
    this.commerce_longitude = new FormControl(null, [ Validators.required ]);
    this.commerce_latitude = new FormControl(null, [ Validators.required ]);

    this.formAddCommerce = this.fb.group({
      commerce_isOpened : this.commerce_isOpened,
      commerce_name : this.commerce_name,
      commerce_codeSiret : this.commerce_codeSiret,
      commerce_uniqueIdName : this.commerce_uniqueIdName,
      commerce_description: this.commerce_description,
      commerce_pictureLogo: this.commerce_pictureLogo,
      commerce_pictureDescription: this.commerce_pictureDescription,
      commerce_address: this.commerce_address,
      commerce_addressExtra: this.commerce_addressExtra,
      commerce_postalCode: this.commerce_postalCode,
      commerce_cityName: this.commerce_cityName,
      commerce_stateName: this.commerce_stateName,
      commerce_longitude: this.commerce_longitude,
      commerce_latitude: this.commerce_latitude
    });


  }




  addCommerce() {
    if (this.formAddCommerce.valid) {

      console.log('formAddCommerce:');
      console.log(this.formAddCommerce.value);

      const commerce = new Commerce();
      commerce.setAddCommerce(this.formAddCommerce.value.commerce_name, this.formAddCommerce.value.commerce_codeSiret, this.formAddCommerce.value.commerce_uniqueIdName, this.formAddCommerce.value.commerce_description, new Address({address: this.formAddCommerce.value.commerce_address, addressExtra: this.formAddCommerce.value.commerce_addressExtra, postalCode: this.formAddCommerce.value.commerce_postalCode, cityName: this.formAddCommerce.value.commerce_cityName, stateName: this.formAddCommerce.value.commerce_stateName, longitude : this.formAddCommerce.value.commerce_longitude,  latitude: this.formAddCommerce.value.commerce_latitude}),  this.formAddCommerce.value.commerce_pictureLogo, this.formAddCommerce.value.commerce_pictureDescription, this.formAddCommerce.value.commerce_isOpened === 1);

      this.merchantService.addCommerce( commerce ).subscribe(
        (rrp: RestResponse) => {

          console.log('component.signIn: ');
          console.log(rrp);


          if (rrp.status === 'SUCCESS') {
            this.infoBoxNotificationsService.addMessage('info', 'Vos modifications ont bien étaient prise en compte.', 10);

          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'enregistrement des modifications : ' + rrp.errormessage, 10);
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'enregistrement des modifications : ' + error, 10);
        });

      this.formAddCommerce.reset();
    }
  }



  public controlCommerce_name(): string               { return ( ((this.commerce_name.touched) && (this.commerce_name.hasError('required'))) ?                `Le Nom / La Raison Sociale est obligatoire` : null ); }
  public controlCommerce_codeSiret(): string          { return ( ((this.commerce_codeSiret.touched) && (this.commerce_codeSiret.hasError('required'))) ?      `Le code Siret est obligatoire` : null ); }
  public controlCommerce_description(): string        { return ( ((this.commerce_description.touched) && (this.commerce_description.hasError('required'))) ?  `La Description est obligatoire` : null ); }
  public controlCommerce_pictureLogo(): string        { return ( ((this.commerce_pictureLogo.touched) && (this.commerce_pictureLogo.hasError('required'))) ?  `Le Logo est obligatoire` : null ); }
  public controlCommerce_pictureDescription(): string { return ( ((this.commerce_pictureDescription.touched) && (this.commerce_pictureDescription.hasError('required'))) ?  `La Photo ou Illustration est obligatoire` : null ); }
  public controlCommerce_address_address(): string    { return ( ((this.commerce_address.touched) && (this.commerce_address.hasError('required'))) ?          `L'addresse est obligatoire` : null ); }
  public controlCommerce_address_postalCode(): string { return ( ((this.commerce_postalCode.touched) && (this.commerce_postalCode.hasError('required'))) ?    `Le code Postal est obligatoire` : null ); }
  public controlCommerce_address_cityName(): string   { return ( ((this.commerce_cityName.touched) && (this.commerce_cityName.hasError('required'))) ?        `La Ville est obligatoire` : null ); }
  public controlCommerce_address_stateName(): string  { return ( ((this.commerce_stateName.touched) && (this.commerce_stateName.hasError('required'))) ?      `Le Pays est obligatoire` : null ); }
  public controlCommerce_address_longitude(): string  { return ( ((this.commerce_longitude.touched) && (this.commerce_longitude.hasError('required'))) ?      `La Longitude est obligatoire` : null ); }
  public controlCommerce_address_latitude(): string   { return ( ((this.commerce_latitude.touched) && (this.commerce_latitude.hasError('required'))) ?        `La Latitude est obligatoire` : null ); }


}
