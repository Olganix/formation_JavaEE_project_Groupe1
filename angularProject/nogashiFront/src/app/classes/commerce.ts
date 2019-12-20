import {Address} from './address';

export class Commerce {


  private _id: string;
  private _name: string;

  private _codeSiret: string;
  private _uniqueIdName: string;
  private _description: string;

  private _address: Address;
  private _schedule: string;      // todo  schedule (horaire) as a list of Classe

  private _pictureLogo: string;
  private _pictureDescription: string;
  private _isOpened: boolean;

  // todo list productTemplate, list of Commmere theory and the merchand.




  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }
  }


  setAddCommerce(name: string, codeSiret: string, uniqueIdName: string, description: string, address: Address, pictureLogo: string, pictureDescription: string, isOpened: boolean) {
    this._name = name;
    this._codeSiret = codeSiret;
    this._uniqueIdName = uniqueIdName;
    this._description = description;
    this._address = address;
    this._pictureLogo = pictureLogo;
    this._pictureDescription = pictureDescription;
    this._isOpened = isOpened;
  }

  toHttpObject() {
    return {id: this._id, name: this._name, codeSiret: this._codeSiret, uniqueIdName: this._uniqueIdName, description: this._description, address: this._address.toHttpObject(), pictureLogo: this._pictureLogo, pictureDescription: this._pictureDescription, isOpened: this._isOpened };
  }

  get id(): string {
    return this._id;
  }

  set id(value: string) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get codeSiret(): string {
    return this._codeSiret;
  }

  set codeSiret(value: string) {
    this._codeSiret = value;
  }

  get uniqueIdName(): string {
    return this._uniqueIdName;
  }

  set uniqueIdName(value: string) {
    this._uniqueIdName = value;
  }

  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }

  get address(): Address {
    return this._address;
  }

  set address(value: Address) {
    this._address = value;
  }

  get schedule(): string {
    return this._schedule;
  }

  set schedule(value: string) {
    this._schedule = value;
  }

  get pictureLogo(): string {
    return this._pictureLogo;
  }

  set pictureLogo(value: string) {
    this._pictureLogo = value;
  }

  get pictureDescription(): string {
    return this._pictureDescription;
  }

  set pictureDescription(value: string) {
    this._pictureDescription = value;
  }

  get isOpened(): boolean {
    return this._isOpened;
  }

  set isOpened(value: boolean) {
    this._isOpened = value;
  }
}
