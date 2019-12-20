import { Component, OnInit } from '@angular/core';
import {RestResponse} from '../../../../classes/rest-response';
import {environment} from '../../../../../environments/environment';
import {map, retry} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {ProductTemplate} from '../../../../classes/product-template';
import {AbstractControlDirective, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ConnexionService} from '../../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {Router} from '@angular/router';
import {CustomValidators} from '../../../../validators/custom-validators';

@Component({
  selector: 'app-list-products-templates',
  templateUrl: './list-products-templates.component.html',
  styleUrls: ['./list-products-templates.component.scss']
})
export class ListProductsTemplatesComponent implements OnInit {

  listProductsTemplates: any;

  name: FormControl;
  description: FormControl;
  price: FormControl;
  isWrapped: FormControl;

  form1: FormGroup;



  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router,
              private fb: FormBuilder
  ) { }

  ngOnInit() {

  /* AJOUT D'UNE FICHE PRODUIT */

    this.name = new FormControl(null, [
      Validators.required,
      CustomValidators.nameCheck()
    ]);
    this.description = new FormControl(null, [
      Validators.required,
      CustomValidators.nameCheck()
    ]);
    this.price = new FormControl(null, [
      Validators.required
      // Validators.price()
    ]);

    this.form1 = this.fb.group({
      name : this.name,
      description : this.description,
      price: this.price,
      isWrapped: this.isWrapped
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

  }

  /*
    addProductTemplate(form: AbstractControlDirective): void {
      if (form.valid) {
        this.listProductsTemplates.push(this.productTemplate);
        this.productTemplate = new ProductTemplate();
        form.reset();
      }
    }
  */



  onSubmit() {
    console.log(this.form1.value);

    if (this.form1.valid) {

      console.log('form:');
      console.log(this.form1.value);

      const productTemplate = new ProductTemplate();
      productTemplate.setAddProductTemplate(this.form1.value.name, this.form1.value.description, this.form1.value.price, this.form1.value.isWrapped);

      this.connexionService.addProductTemplate( productTemplate ).subscribe(
        (rrp: RestResponse) => {

          console.log('component.addProductTemplate: ');
          console.log(rrp);


          if (rrp.status === 'SUCCESS') {
            this.infoBoxNotificationsService.addMessage('info', 'L\'ajout de la fiche a bien été pris en compte.', 10);
            // this.router.navigate(['/test/testSpringRest']);   // todo uncomment

          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec (1) de l\'ajout : ' + rrp.errormessage, 10);
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec (2) de l\'ajout : ' + error, 10);
        });

      this.form1.reset();
    }
  }


  public controlName(): string {
    if (this.name.touched) {
      if (this.name.hasError('required')) {
        return `Le nom est obligatoire`;
      }

      if (this.name.hasError('error_name')) {
        return `Nom non valide`;
      }
    }
    return null;
  }

  /*
  public controlDescription(): string {
    if (this.description.touched) {
      if (this.description.hasError('required')) {
        return `La description est obligatoire`;
      }

      if (this.description.hasError('error_description')) {
        return `Description non valide`;
      }
    }
    return null;
  }

  public controlPrice(): string {
    if (this.price.touched) {
      if (this.price.hasError('required')) {
        return `Le prix est obligatoire`;
      }

      if (this.price.hasError('error_price')) {
        return `Prix non valide`;
      }
    }
    return null;
  }
*/
  }

