import {ShoppingCartByCommerce} from './shopping-cart-by-commerce';
import {ShoppingCartStatus} from '../enum/shopping-cart-status.enum';

export class ShoppingCart {

  private _status: ShoppingCartStatus;
  private _shoppingCartByCommerces: ShoppingCartByCommerce[];

  // --- Information to complete the java class
  // Buyer buyer;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }


  get status(): ShoppingCartStatus {
    return this._status;
  }

  set status(value: ShoppingCartStatus) {
    this._status = value;
  }

  get shoppingCartByCommerces(): ShoppingCartByCommerce[] {
    return this._shoppingCartByCommerces;
  }

  set shoppingCartByCommerces(value: ShoppingCartByCommerce[]) {
    this._shoppingCartByCommerces = value;
  }
}
