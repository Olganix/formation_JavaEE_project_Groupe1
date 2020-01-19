import { Component, OnInit } from '@angular/core';
import {RestResponse} from '../../../../classes/rest-response';
import {ShoppingCartByCommerce} from '../../../../classes/shopping-cart-by-commerce';
import {BuyerService} from '../../../../services/buyer.service';
import {ShoppingCart} from '../../../../classes/shopping-cart';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {

  shoppingCart: ShoppingCart;

  constructor(private buyerService: BuyerService,
            ) { }

  ngOnInit() {

    this.buyerService.getShoppingCart().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {

          this.shoppingCart.shoppingCartByCommerces = [];
          for (const scbc of rrp.data) {
            this.shoppingCart.shoppingCartByCommerces.push( new ShoppingCartByCommerce(scbc) );
          }

          this.shoppingCart.shoppingCartByCommerces = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des productTemplates : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des productTemplates : ', error);
      });
  }

}
