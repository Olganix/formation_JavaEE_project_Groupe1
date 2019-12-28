import {User} from './user';

export class Merchant extends User {

  private _codeSiren: string;
  private _codeIBAN: string;
  private _codeBic: string;
  // TODO list de Commerces and list d'Employees

  constructor(obj: object, codeSiren: string, codeIBAN: string, codeBic: string) {
    super(obj);
    this._codeSiren = codeSiren;
    this._codeIBAN = codeIBAN;
    this._codeBic = codeBic;
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
