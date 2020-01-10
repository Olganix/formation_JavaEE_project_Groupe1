export class FaqQuestion {

  private _question: string;
  private _answer: string;

  // --- Information to complete the java class
  // Faq faq;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }

  get question(): string {
    return this._question;
  }

  set question(value: string) {
    this._question = value;
  }

  get answer(): string {
    return this._answer;
  }

  set answer(value: string) {
    this._answer = value;
  }
}
