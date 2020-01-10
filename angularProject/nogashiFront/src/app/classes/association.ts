import {UserRole} from '../enum/user-role.enum';
import {Buyer} from './buyer';

export class Association extends Buyer {

  private _codeSiren: string;
  private _codeAssociation: string;

  constructor(obj?: object) {
    super(obj);
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }

    this.autoCompletionShoppingCart = true;
  }

  /*
  setAll(.., codeSiren: string, codeAssociation: string) {
    // todo complete with Buyer.setAll(...)
    this._codeSiren = codeSiren;
    this._codeAssociation = codeAssociation;
  }
  toHttpObject(): any {
    var buyer = super().toHttpObject();
    buyer.codeSiren = this._codeSiren;
    buyer.codeAssociation = this._codeAssociation;
    return buyer;
  }
  */

  get codeSiren(): string {
    return this._codeSiren;
  }

  set codeSiren(value: string) {
    this._codeSiren = value;
  }

  get codeAssociation(): string {
    return this._codeAssociation;
  }

  set codeAssociation(value: string) {
    this._codeAssociation = value;
  }
}
