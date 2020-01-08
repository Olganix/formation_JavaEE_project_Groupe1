export class CreditCard {

  private _type: string;
  private _codeNumber: string;
  private _ownerName: string;
  private _expirationDate: Date;     // todo check if DateTime or other.
  private _codeSecurity: string;

  // --- Information to complete the java class
  // Individual individual;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }
  }


  get type(): string {
    return this._type;
  }

  set type(value: string) {
    this._type = value;
  }

  get codeNumber(): string {
    return this._codeNumber;
  }

  set codeNumber(value: string) {
    this._codeNumber = value;
  }

  get ownerName(): string {
    return this._ownerName;
  }

  set ownerName(value: string) {
    this._ownerName = value;
  }

  get expirationDate(): Date {
    return this._expirationDate;
  }

  set expirationDate(value: Date) {
    this._expirationDate = value;
  }

  get codeSecurity(): string {
    return this._codeSecurity;
  }

  set codeSecurity(value: string) {
    this._codeSecurity = value;
  }
}
