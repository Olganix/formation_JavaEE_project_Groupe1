import {ShoppingCartByCommerce} from './shopping-cart-by-commerce';
import {ShoppingCartStatus} from '../enum/shopping-cart-status.enum';
import {Buyer} from './buyer';

export class ShoppingCart {

  private _id: number;
  private _status: ShoppingCartStatus;
  private _price: number;

  private _shoppingCartByCommerces: ShoppingCartByCommerce[];

  // --- Information to complete the java class
  private _buyer: Buyer;

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


  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get price(): number {
    return this._price;
  }

  set price(value: number) {
    this._price = value;
  }


  get buyer(): Buyer {
    return this._buyer;
  }

  set buyer(value: Buyer) {
    this._buyer = value;
  }
}
