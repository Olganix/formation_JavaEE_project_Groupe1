import { Component, OnInit } from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';
import {RestResponse} from '../../../../classes/rest-response';
import {MerchantService} from '../../../../services/merchant.service';

@Component({
  selector: 'app-product-template-list-merchant',
  templateUrl: './product-template-list-merchant.component.html',
  styleUrls: ['./product-template-list-merchant.component.scss']
})
export class ProductTemplateListMerchantComponent implements OnInit {

  productTemplates: ProductTemplate[];

  constructor(private merchantService: MerchantService
              ) { }

  ngOnInit() {


    this.merchantService.getProductTemplates().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.productTemplates = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des productTemplates : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des productTemplates : ', error);
      });
  }

}
