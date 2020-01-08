
export class CommerceCategory {

  private _name: string;
  private _description: string;

  // --- Information to complete the java class
  // enumManager: EnumManager;


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


  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }
}
