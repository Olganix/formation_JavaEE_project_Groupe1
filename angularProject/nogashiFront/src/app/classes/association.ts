import {UserRole} from '../enum/user-role.enum';
import {Buyer} from './buyer';

export class Association extends Buyer {

  private _codeSiren: string;
  private _codeAssociation: string;
  private _userRole: UserRole;

  constructor(obj: object, codeSiren: string, codeAssociation: string, userRole: UserRole) {
    super(obj);
    this._codeSiren = codeSiren;
    this._codeAssociation = codeAssociation;
    this._userRole = userRole;
  }

  get codeSiren(): string {
    return this._codeSiren;
  }

  set codeSiren(value: string) {
    this._codeSiren = value;
  }

  get codeAssociation(): string {
    return this._codeAssociation;
  }

  set codeAssociation(value: string) {
    this._codeAssociation = value;
  }

  get userRole(): UserRole {
    return this._userRole;
  }

  set userRole(value: UserRole) {
    this._userRole = value;
  }
}
