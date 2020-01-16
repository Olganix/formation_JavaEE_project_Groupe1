import {Component, Input, OnInit} from '@angular/core';
import {Commerce} from '../../../../classes/commerce';
import {ShoppingCartByCommerce} from '../../../../classes/shopping-cart-by-commerce';
import { ShoppingCartStatus } from 'src/app/enum/shopping-cart-status.enum';
import { Utils } from 'src/app/classes/utils';

@Component({
  selector: 'app-command-merchant-view',
  templateUrl: './command-merchant-view.component.html',
  styleUrls: ['./command-merchant-view.component.scss']
})
export class CommandMerchantViewComponent implements OnInit {

  Utils = Utils;

  ShoppingCartStatus = ShoppingCartStatus;
  @Input() commerce: Commerce;
  @Input() shoppingCartByCommerce: ShoppingCartByCommerce;

  constructor() { }

  ngOnInit() {
  }

}
