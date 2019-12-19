import { Component, OnInit } from '@angular/core';
import { ConnexionService } from '../../../services/connexion.service';
import {RestResponse} from '../../../classes/rest-response';
import {environment} from '../../../../environments/environment';
import {log} from 'util';
import {ProductTemplate} from "../../../classes/product-template";
import {ListingService} from "../../../services/listing.service";

@Component({
  selector: 'app-test-spring-rest',
  templateUrl: './test-spring-rest.component.html',
  styleUrls: ['./test-spring-rest.component.scss']
})

// test communication spring MVC

export class TestSpringRestComponent implements OnInit
{
  users: any;
  merchants: any;
  listProductsTemplates: any;
  productTemplate: ProductTemplate;

  constructor(private connexionService: ConnexionService, private listingService: ListingService)
  {

  }

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
    this.listingService.getProductsTemplates().subscribe(
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
}
