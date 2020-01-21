import {ProductStatus} from '../enum/product-status.enum';
import {ProductTemplate} from './product-template';
import {ShoppingCartStatus} from '../enum/shopping-cart-status.enum';
import {ShoppingCartByCommerce} from './shopping-cart-by-commerce';

export class Product extends ProductTemplate {

  private _status: ProductStatus;
  private _typedName: string;                       // to specified special thing to differ from ProductTemplate.
  private _reference: ProductTemplate;

  // --- Information to complete the java class

  // commerce: Commerce;
  // shoppingCart: ShoppingCartByCommerce;


  constructor(obj?: object) {
    super(obj);
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }


    /* tslint:disable:no-string-literal */
    if ( (obj !== undefined) && (obj !== null) ) {

      if ( (obj.hasOwnProperty('status')) && (obj['status'] !== null)) {
        switch (obj['status']) {
          case 'AVAILABLE': this._status = ProductStatus.AVAILABLE; break;
          case 'PROMOTION': this._status = ProductStatus.PROMOTION; break;
          case 'RESERVED': this._status = ProductStatus.RESERVED; break;
          case 'SOLD': this._status = ProductStatus.SOLD; break;
          case 'UNAVAILABLE': this._status = ProductStatus.UNAVAILABLE; break;
          case 'UNSOLD': this._status = ProductStatus.UNSOLD; break;
        }
      }

      if ((obj.hasOwnProperty('reference')) && (obj['reference'] !== null)) {
        this._reference = new ProductTemplate(obj['reference']);
      }
    }
  }

  get status(): ProductStatus {
    return this._status;
  }

  set status(value: ProductStatus) {
    this._status = value;
  }

  get typedName(): string {
    return this._typedName;
  }

  set typedName(value: string) {
    this._typedName = value;
  }


  get reference(): ProductTemplate {
    return this._reference;
  }

  set reference(value: ProductTemplate) {
    this._reference = value;
  }
}
