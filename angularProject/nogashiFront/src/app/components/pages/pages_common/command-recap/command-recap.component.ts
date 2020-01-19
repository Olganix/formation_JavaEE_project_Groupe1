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
          console.log('Echec de la recuperation du shoppingCart : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation du shoppingCart : ', error);
      });
  }

}
