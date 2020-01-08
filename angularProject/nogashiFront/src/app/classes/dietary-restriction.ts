import {ProductDetail} from './product-detail';

export class DietaryRestriction {

  private _name: string;
  private _description: string;
  private _listBadMatch: ProductDetail[];

  // --- Information to complete the java class
  // EnumManager enumManager;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }


  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }

  get listBadMatch(): ProductDetail[] {
    return this._listBadMatch;
  }

  set listBadMatch(value: ProductDetail[]) {
    this._listBadMatch = value;
  }
}
