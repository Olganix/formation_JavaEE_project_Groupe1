import {Buyer} from './buyer';
import {CreditCard} from './credit-card';

export class Individual extends Buyer {

  private _creditCard: CreditCard;

  constructor(obj?: object) {
    super(obj);
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }
  }


  get creditCard(): CreditCard {
    return this._creditCard;
  }

  set creditCard(value: CreditCard) {
    this._creditCard = value;
  }
}
