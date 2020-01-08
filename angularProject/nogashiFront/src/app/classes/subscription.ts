import {Commerce} from './commerce';
import {ProductTemplate} from './product-template';


export class Subscription {

  private _productTemplate: ProductTemplate;
  private _commerce: Commerce;

  // --- Information to complete the java class
  // Buyer buyer;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }


  get productTemplate(): ProductTemplate {
    return this._productTemplate;
  }

  set productTemplate(value: ProductTemplate) {
    this._productTemplate = value;
  }

  get commerce(): Commerce {
    return this._commerce;
  }

  set commerce(value: Commerce) {
    this._commerce = value;
  }
}
