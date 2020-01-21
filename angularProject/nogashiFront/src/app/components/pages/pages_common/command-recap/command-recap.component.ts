import {Component, OnInit} from '@angular/core';
import {BuyerService} from '../../../../services/buyer.service';
import {ShoppingCart} from '../../../../classes/shopping-cart';
import {RestResponse} from '../../../../classes/rest-response';
import {ShoppingCartByCommerce} from '../../../../classes/shopping-cart-by-commerce';

@Component({
  selector: 'app-command-recap',
  templateUrl: './command-recap.component.html',
  styleUrls: ['./command-recap.component.scss']
})
export class CommandRecapComponent implements OnInit {

  shoppingCart: ShoppingCart;
  groupByCommerce_ProductTemplate: any;

  constructor(private buyerService: BuyerService,
        ) { }

  ngOnInit() {

    this.buyerService.getShoppingCart().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.shoppingCart = new ShoppingCart(rrp.data);
          this.init();
        } else {
          console.log('Echec de la recuperation du shoppingCart : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation du shoppingCart : ', error);
      });
  }


  init() {

    this.groupByCommerce_ProductTemplate = [];
    for (const scbc of this.shoppingCart.shoppingCartByCommerces) {

      const productTemplates = [];
      for (const p of scbc.products) {
        const ptId = p.reference.id;

        let isFound = false;
        // tslint:disable-next-line:prefer-for-of
        for (let i = 0; i < productTemplates.length; i++) {
          if (productTemplates[i].pt.id === ptId) {
            isFound = true;
            productTemplates[i].lp.push(p);
            break;
          }
        }
        if (!isFound) {
          productTemplates.push({pt: p.reference, lp:  [p]} );
        }
      }

      this.groupByCommerce_ProductTemplate.push({scbc, lpt: productTemplates});
    }

    console.log('this.groupByCommerce_ProductTemplate :')
    console.log(this.groupByCommerce_ProductTemplate);
  }

}
