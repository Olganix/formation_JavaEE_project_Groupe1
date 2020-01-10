import {CommerceCategory} from './commerce-category';
import {ProductDetail} from './product-detail';
import {DietaryRestriction} from './dietary-restriction';

export class EnumManager {

  private _commerceCategories: CommerceCategory[];
  private _productDetails: ProductDetail[];
  private _dietaryRestrictions: DietaryRestriction[];

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }


  get commerceCategories(): CommerceCategory[] {
    return this._commerceCategories;
  }

  set commerceCategories(value: CommerceCategory[]) {
    this._commerceCategories = value;
  }

  get productDetails(): ProductDetail[] {
    return this._productDetails;
  }

  set productDetails(value: ProductDetail[]) {
    this._productDetails = value;
  }

  get dietaryRestrictions(): DietaryRestriction[] {
    return this._dietaryRestrictions;
  }

  set dietaryRestrictions(value: DietaryRestriction[]) {
    this._dietaryRestrictions = value;
  }
}
