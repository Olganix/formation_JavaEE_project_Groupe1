import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../../../../classes/product';

@Component({
  selector: 'app-product-item-in-cart-by-commerce',
  templateUrl: './product-item-in-cart-by-commerce.component.html',
  styleUrls: ['./product-item-in-cart-by-commerce.component.scss']
})
export class ProductItemInCartByCommerceComponent implements OnInit {

  @Input() product: Product;

  constructor() { }

  ngOnInit() {
  }

}
