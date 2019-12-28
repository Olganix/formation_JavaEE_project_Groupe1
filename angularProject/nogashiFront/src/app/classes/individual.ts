import {UserRole} from '../enum/user-role.enum';
import {Buyer} from './buyer';

export class Individual extends Buyer {

  private _codeDebitCard: string;
  private _pseudo: string;

  constructor(obj: object, codeDebitCard: string, pseudo: string, role: UserRole) {
    super(obj);
    this._codeDebitCard = codeDebitCard;
    this._pseudo = pseudo;
  }

  get codeDebitCard(): string {
    return this._codeDebitCard;
  }

  set codeDebitCard(value: string) {
    this._codeDebitCard = value;
  }

  get pseudo(): string {
    return this._pseudo;
  }

  set pseudo(value: string) {
    this._pseudo = value;
  }
}
