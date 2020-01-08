import {ProductStatus} from '../enum/product-status.enum';
import {ProductTemplate} from './product-template';

export class Product extends ProductTemplate {

  private _status: ProductStatus;
  private _typedName: string;                       // to specified special thing to differ from ProductTemplate.

  // --- Information to complete the java class
  // reference: ProductTemplate;
  // commerce: Commerce;
  // shoppingCart: ShoppingCartByCommerce;


  constructor(obj?: object) {
    super(obj);
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
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
}
