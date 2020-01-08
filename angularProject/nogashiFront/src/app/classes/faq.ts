import {FaqQuestion} from './faq-question';
import {DietaryRestriction} from './dietary-restriction';

export class Faq {

  private _name: string;
  private _questions: FaqQuestion[];

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }


  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get questions(): FaqQuestion[] {
    return this._questions;
  }

  set questions(value: FaqQuestion[]) {
    this._questions = value;
  }
}
