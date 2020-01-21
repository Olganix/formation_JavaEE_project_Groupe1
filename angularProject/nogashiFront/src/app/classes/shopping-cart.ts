import {ShoppingCartByCommerce} from './shopping-cart-by-commerce';
import {ShoppingCartStatus} from '../enum/shopping-cart-status.enum';
import {Buyer} from './buyer';
import {SchedulerWeekType} from '../enum/scheduler-week-type.enum';
import {SchedulerDay} from './scheduler-day';

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

      this._shoppingCartByCommerces = [];
      if ((obj.hasOwnProperty('shoppingCartByCommerces')) && (obj['shoppingCartByCommerces'] !== null)) {
        for (const tmp of obj['shoppingCartByCommerces']) {
          this._shoppingCartByCommerces.push(new ShoppingCartByCommerce(tmp));
        }
      }

      //todo for reference
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
