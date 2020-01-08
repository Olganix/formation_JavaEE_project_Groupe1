import {User} from './user';

export class Merchant extends User {

  private _codeSiren: string;
  private _codeIBAN: string;
  private _codeBic: string;

  // --- Information to complete the java class
  // List<Commerce> commerces
  // List<ProductTemplate> productTemplates

  constructor(obj?: object) {
    super(obj);
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }

  get codeSiren(): string {
    return this._codeSiren;
  }

  set codeSiren(value: string) {
    this._codeSiren = value;
  }

  get codeIBAN(): string {
    return this._codeIBAN;
  }

  set codeIBAN(value: string) {
    this._codeIBAN = value;
  }

  get codeBic(): string {
    return this._codeBic;
  }

  set codeBic(value: string) {
    this._codeBic = value;
  }
}
