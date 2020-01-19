import {Address} from './address';
import {SchedulerWeek} from './scheduler-week';

export class Commerce {

  private _id: number;
  private _name: string;

  private _codeSiret: string;
  private _uniqueIdName: string;
  private _description: string;

  private _address: Address;
  private _schedulerWeek: SchedulerWeek;

  private _pictureLogo: string;
  private _pictureDescription: string;
  private _isOpened: boolean;

  // --- Information to complete the java class
  // Merchant merchant;
  // List<CommerceCategory> commerceCategories
  // List<ProductTemplate> productTemplates
  // List<Product> products
  // List<ShoppingCartByCommerce> shoppingCartByCommerces
  // Faq faq = null;


  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }


    /* tslint:disable:no-string-literal */
    if ( (obj !== undefined) && (obj !== null) ) {

      if ((obj.hasOwnProperty('schedulerWeek')) && (obj['schedulerWeek'] !== null)) {
        this._schedulerWeek = new SchedulerWeek(obj['schedulerWeek']);
      }

      if ((obj.hasOwnProperty('address')) && (obj['address'] !== null)) {
        this._address = new Address(obj['address']);
      }
    }
  }


  setAddCommerce(id: number, name: string, codeSiret: string, uniqueIdName: string, description: string, address: Address, pictureLogo: string, pictureDescription: string, isOpened: boolean) {
    this._id = id;
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


  get schedulerWeek(): SchedulerWeek {
    return this._schedulerWeek;
  }

  set schedulerWeek(value: SchedulerWeek) {
    this._schedulerWeek = value;
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
