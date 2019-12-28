import {ProductTemplate} from './product-template';
import {Commerce} from './commerce';
import {ProductStatus} from '../enum/product-status.enum';

export class Product extends ProductTemplate {

  private _reference: string;
  private _commerce: Commerce;
  private _status: ProductStatus;
  private _typedName: string;

  constructor(obj: object, reference: string, commerce: Commerce, status: ProductStatus, typedName: string) {
    super(obj);
    this._reference = reference;
    this._commerce = commerce;
    this._status = status;
    this._typedName = typedName;
  }

  get reference(): string {
    return this._reference;
  }

  set reference(value: string) {
    this._reference = value;
  }

  get commerce(): Commerce {
    return this._commerce;
  }

  set commerce(value: Commerce) {
    this._commerce = value;
  }

  get status(): ProductStatus {
    return this._status;
  }

  set status(value: ProductStatus) {
    this._status = value;
  }

  get typedName(): string {
    return this._typedName;
  }

  set typedName(value: string) {
    this._typedName = value;
  }
}
