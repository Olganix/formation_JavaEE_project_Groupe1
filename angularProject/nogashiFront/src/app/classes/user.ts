

export class User {

  private _id: string;

  private _name: string;
  private _email: string;
  private _password: string;
  private _role = 'INDIVIDUAL';                       // INDIVIDUAL, MERCHANT, ASSOCIATION, ADMIN
  private _avatarFilename = 'NoAvatar.jpg';

  private _phoneNumber: string;
  private _phoneNumber2: string;
  private _address: string;
  private _addressExtra: string;
  private _postalCode: string;
  private _cityName: string;
  private _stateName: string;

  private _emailValid = false;
  private _token: string = null;												// for security operations

  private _newsletterEnabled = false;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }
  }

  setSignin(name: string, password: string, email: string, role: string, newsletterEnabled: boolean) {
    this._name = name;
    this._password = password;
    this._email = email;
    this._role = role;
    this._newsletterEnabled = newsletterEnabled;
  }

  toHttpObject_signin(): any {
    return {id: this._id, name: this._name, email: this._email, password: this._password, role: this._role, newsletterEnabled: this._newsletterEnabled };
  }


  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get role(): string {
    return this._role;
  }

  set role(value: string) {
    this._role = value;
  }

  get avatarFilename(): string {
    return this._avatarFilename;
  }

  set avatarFilename(value: string) {
    this._avatarFilename = value;
  }

  get phoneNumber(): string {
    return this._phoneNumber;
  }

  set phoneNumber(value: string) {
    this._phoneNumber = value;
  }

  get phoneNumber2(): string {
    return this._phoneNumber2;
  }

  set phoneNumber2(value: string) {
    this._phoneNumber2 = value;
  }

  get address(): string {
    return this._address;
  }

  set address(value: string) {
    this._address = value;
  }

  get addressExtra(): string {
    return this._addressExtra;
  }

  set addressExtra(value: string) {
    this._addressExtra = value;
  }

  get postalCode(): string {
    return this._postalCode;
  }

  set postalCode(value: string) {
    this._postalCode = value;
  }

  get cityName(): string {
    return this._cityName;
  }

  set cityName(value: string) {
    this._cityName = value;
  }

  get stateName(): string {
    return this._stateName;
  }

  set stateName(value: string) {
    this._stateName = value;
  }

  get emailValid(): boolean {
    return this._emailValid;
  }

  set emailValid(value: boolean) {
    this._emailValid = value;
  }

  get token(): string {
    return this._token;
  }

  set token(value: string) {
    this._token = value;
  }

  get newsletterEnabled(): boolean {
    return this._newsletterEnabled;
  }

  set newsletterEnabled(value: boolean) {
    this._newsletterEnabled = value;
  }


  get id(): string {
    return this._id;
  }
}