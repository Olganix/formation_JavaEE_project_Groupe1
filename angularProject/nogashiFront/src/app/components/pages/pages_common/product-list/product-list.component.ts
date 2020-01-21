import { Component, OnInit } from '@angular/core';
import {BuyerService} from '../../../../services/buyer.service';
import {RestResponse} from '../../../../classes/rest-response';
import {ProductTemplate} from '../../../../classes/product-template';
import {ActivatedRoute} from '@angular/router';
import {ProductTemplateListForAdd} from '../../../../classes/product-template-list-for-add';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  productTemplateListForAdd: ProductTemplateListForAdd;

  constructor(private buyerService: BuyerService,
              private route: ActivatedRoute
            ) { }

  ngOnInit() {
    this.buyerService.getProductTemplatesForAdd().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.productTemplateListForAdd = new ProductTemplateListForAdd(rrp.data);
        } else {
          console.log('Echec de la recuperation de la liste des productTemplates : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des productTemplates : ', error);
      });
  }

}
