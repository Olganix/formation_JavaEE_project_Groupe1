export class Address {

  private _id: string;
  private _address: string;
  private _addressExtra: string;
  private _postalCode: string;
  private _cityName: string;
  private _stateName: string;
  private _longitude: number;
  private _latitude: number;


  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }
  }


  get address(): string {
    return this._address;
  }

  set address(value: string) {
    this._address = value;
  }

  get addressExtra(): string {
    return this._addressExtra;
  }

  set addressExtra(value: string) {
    this._addressExtra = value;
  }

  get postalCode(): string {
    return this._postalCode;
  }

  set postalCode(value: string) {
    this._postalCode = value;
  }

  get cityName(): string {
    return this._cityName;
  }

  set cityName(value: string) {
    this._cityName = value;
  }

  get stateName(): string {
    return this._stateName;
  }

  set stateName(value: string) {
    this._stateName = value;
  }

  get longitude(): number {
    return this._longitude;
  }

  set longitude(value: number) {
    this._longitude = value;
  }

  get latitude(): number {
    return this._latitude;
  }

  set latitude(value: number) {
    this._latitude = value;
  }


  get id(): string {
    return this._id;
  }

  set id(value: string) {
    this._id = value;
  }
}
