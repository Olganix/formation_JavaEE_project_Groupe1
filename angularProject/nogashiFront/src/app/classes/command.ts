import {ShoppingCartByCommerce} from './shopping-cart-by-commerce';

export class Command {

  private _id: string;
  private _shoppingCart: ShoppingCartByCommerce;
  private _status: boolean;

  constructor(id: string, shoppingCart: ShoppingCartByCommerce, status: boolean) {
    this._id = id;
    this._shoppingCart = shoppingCart;
    this._status = status;
  }

  get id(): string {
    return this._id;
  }

  set id(value: string) {
    this._id = value;
  }

  get shoppingCart(): ShoppingCartByCommerce {
    return this._shoppingCart;
  }

  set shoppingCart(value: ShoppingCartByCommerce) {
    this._shoppingCart = value;
  }

  get status(): boolean {
    return this._status;
  }

  set status(value: boolean) {
    this._status = value;
  }
}
