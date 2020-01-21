import {Product} from './product';
import {ShoppingCartStatus} from '../enum/shopping-cart-status.enum';
import {Commerce} from './commerce';
import {ShoppingCart} from './shopping-cart';
import {ProductStatus} from '../enum/product-status.enum';
import {ProductTemplate} from './product-template';

export class ShoppingCartByCommerce {

  private _id: number;
  private _status: ShoppingCartStatus;
  private _price: number;

  private _commerce: Commerce;
  private _products: Product[];

  // --- Information to complete the java class
  // private _shoppingCart: ShoppingCart;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }

    /* tslint:disable:no-string-literal */
    if ( (obj !== undefined) && (obj !== null) ) {

      if ( (obj.hasOwnProperty('status')) && (obj['status'] !== null)) {
        switch (obj['status']) {
          case 'IN_PROGRESS': this._status = ShoppingCartStatus.IN_PROGRESS; break;
          case 'PAID': this._status = ShoppingCartStatus.PAID; break;
          case 'PREPARED': this._status = ShoppingCartStatus.PREPARED; break;
          case 'CONCLUDED': this._status = ShoppingCartStatus.CONCLUDED; break;
        }
      }

      if ((obj.hasOwnProperty('commerce')) && (obj['commerce'] !== null)) {
        this._commerce = new Commerce(obj['commerce']);
      }

      this._products = [];
      if ((obj.hasOwnProperty('products')) && (obj['products'] !== null)) {
        for (const tmp of obj['products']) {
          this._products.push(new Product(tmp));
        }
      }
    }
  }


  get status(): ShoppingCartStatus {
    return this._status;
  }

  set status(value: ShoppingCartStatus) {
    this._status = value;
  }

  get commerce(): Commerce {
    return this._commerce;
  }

  set commerce(value: Commerce) {
    this._commerce = value;
  }

  get products(): Product[] {
    return this._products;
  }

  set products(value: Product[]) {
    this._products = value;
  }

  get price(): number {
    return this._price;
  }

  set price(value: number) {
    this._price = value;
  }


  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }
}
