import { Component, OnInit } from '@angular/core';
import {BuyerService} from '../../../../services/buyer.service';
import {RestResponse} from '../../../../classes/rest-response';
import {ProductTemplate} from '../../../../classes/product-template';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  productTemplates: ProductTemplate[];

  constructor(private buyerService: BuyerService,
              private route: ActivatedRoute
            ) { }

  ngOnInit() {


    this.buyerService.getProductsByName(name).subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.productTemplates = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des products : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des products : ', error);
      });
  }

}
