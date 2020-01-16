import { Component, OnInit } from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';
import {MerchantService} from '../../../../services/merchant.service';
import {RestResponse} from '../../../../classes/rest-response';

@Component({
  selector: 'app-commerce-list-merchant',
  templateUrl: './commerce-list-merchant.component.html',
  styleUrls: ['./commerce-list-merchant.component.scss']
})
export class CommerceListMerchantComponent implements OnInit {

  commerces: ProductTemplate[];

  constructor(private merchantService: MerchantService
  ) { }

  ngOnInit() {


    this.merchantService.getCommerces().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.commerces = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des commerces : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des commerces : ', error);
      });
  }

}
