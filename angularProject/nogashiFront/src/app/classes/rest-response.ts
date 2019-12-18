

export class RestResponse {

  private _status: string;                   // SUCCESS, FAIL
  private _data: any = null;
  private _errorCode = -1;													      // for warning and error
  private _errormessage = '';											      // for specific informations.


  constructor(obj?: object) {
    if ((obj !== undefined) && (obj !== null)) {
      Object.assign(this, obj);                 // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }
  }


  get status(): string {
    return this._status;
  }

  set status(value: string) {
    this._status = value;
  }

  get data(): any {
    return this._data;
  }

  set data(value: any) {
    this._data = value;
  }

  get errorCode(): number {
    return this._errorCode;
  }

  set errorCode(value: number) {
    this._errorCode = value;
  }

  get errormessage(): string {
    return this._errormessage;
  }

  set errormessage(value: string) {
    this._errormessage = value;
  }
}
