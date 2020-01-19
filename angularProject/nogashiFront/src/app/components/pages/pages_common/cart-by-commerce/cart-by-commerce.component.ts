import {Component, Input, OnInit} from '@angular/core';
import {ShoppingCartByCommerce} from '../../../../classes/shopping-cart-by-commerce';

@Component({
  selector: 'app-cart-by-commerce',
  templateUrl: './cart-by-commerce.component.html',
  styleUrls: ['./cart-by-commerce.component.scss']
})
export class CartByCommerceComponent implements OnInit {

  @Input() shoppingCartByCommerce: ShoppingCartByCommerce;

  constructor() { }

  ngOnInit() {
  }

}
