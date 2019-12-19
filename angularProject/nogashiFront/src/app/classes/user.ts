import {Address} from './address';


export class User {

  private _id: string;

  private _name: string;
  private _email: string;
  private _password: string;
  private _role = 'INDIVIDUAL';                       // INDIVIDUAL, MERCHANT, ASSOCIATION, ADMIN
  private _avatarFilename = 'NoAvatar.jpg';

  private _phoneNumber: string;
  private _phoneNumber2: string;
  private _address: Address;

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
  setLogin(name: string, password: string) {
    this._name = name;
    this._password = password;
  }


  toHttpObject_signin(): any {
    return {name: this._name, email: this._email, password: this._password, role: this._role, newsletterEnabled: this._newsletterEnabled };
  }
  toHttpObject_login(): any {
    return {name: this._name, password: this._password };
  }
  toHttpObject_passwordRescueModification(): any {
    return {token: this._token, password: this._password };
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


  set address(value: Address) {
    this._address = value;
  }
  get address(): Address {
    return this._address;
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

  set id(value: string) {
    this._id = value;
  }
}
