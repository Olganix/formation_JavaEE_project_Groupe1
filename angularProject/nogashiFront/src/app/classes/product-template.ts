import {ProductDetail} from './product-detail';

export class ProductTemplate {

  private _id: number;
  private _name: string;
  private _description: string;
  private _externalCode: string;
  private _isPackaged: boolean;
  private _price: number;
  private _salePrice: number;
  private _saleTime = new Date();
  private _unsoldTime = new Date();
  private _timeControlStatus: boolean;
  private _maxDurationCart: number;
  private _imageFilename = 'NoAvatar.jpg'; // Todo do the upload system.
  // TODO liste de ProductDetail et de Commerces;


  constructor(obj?: object) {                         // json/object => class with functions
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }
  }

  toHttpObject() {                                    // class with functions => json/object
    return {id: this._id, name: this._name, description: this._description, externalCode: this._externalCode, isPackaged: this._isPackaged, price: this._price, salePrice: this._salePrice, saleTime: this._saleTime, unsoldTime: this._unsoldTime, timeControlStatus: this._timeControlStatus, maxDurationCart: this._maxDurationCart, imageFilename: this._imageFilename };
  }

  setAddProductTemplate(name: string, description: string, price: number, isPackaged: boolean) {
    this._name = name;
    this._description = description;
    this._price = price;
    this._isPackaged = isPackaged;
  }




  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
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

  get externalCode(): string {
    return this._externalCode;
  }

  set externalCode(value: string) {
    this._externalCode = value;
  }

  get isPackaged(): boolean {
    return this._isPackaged;
  }

  set isPackaged(value: boolean) {
    this._isPackaged = value;
  }

  get price(): number {
    return this._price;
  }

  set price(value: number) {
    this._price = value;
  }

  get salePrice(): number {
    return this._salePrice;
  }

  set salePrice(value: number) {
    this._salePrice = value;
  }

  get saleTime(): Date {
    return this._saleTime;
  }

  set saleTime(value: Date) {
    this._saleTime = value;
  }

  get unsoldTime(): Date {
    return this._unsoldTime;
  }

  set unsoldTime(value: Date) {
    this._unsoldTime = value;
  }

  get timeControlStatus(): boolean {
    return this._timeControlStatus;
  }

  set timeControlStatus(value: boolean) {
    this._timeControlStatus = value;
  }

  get maxDurationCart(): number {
    return this._maxDurationCart;
  }

  set maxDurationCart(value: number) {
    this._maxDurationCart = value;
  }

  get imageFilename(): string {
    return this._imageFilename;
  }

  set imageFilename(value: string) {
    this._imageFilename = value;
  }
}
