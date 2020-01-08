import {User} from './user';
import {UserRole} from '../enum/user-role.enum';

export class Buyer extends User {

  private _autoCompletionShoppingCart: boolean;

  // --- Information to complete the java class
  // List<DietaryRestriction> dietaryRestrictions
  // List<Subscription> subscriptions;
  // ShoppingCart shoppingCart
  // List<ShoppingCart> historicShoppingCarts


  constructor(obj?: object) {
    super(obj);
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regle le soucis, avec un Pip dans le service.
    }
    this.autoCompletionShoppingCart = true;
  }

  /*
  setAll(name: string, password: string, email: string, role: UserRole, newsletterEnabled: boolean, autoCompletionShoppingCart: boolean) {
    this.name = name;
    this.password = password;
    this.email = email;
    this.role = role;
    this.newsletterEnabled = newsletterEnabled;
    this._autoCompletionShoppingCart = autoCompletionShoppingCart;
  }
  toHttpObject(): any {
    const user = super.toHttpObject();
    user.autoCompletionShoppingCart = this._autoCompletionShoppingCart;
    return user;
  }
  */

  get autoCompletionShoppingCart(): boolean {
    return this._autoCompletionShoppingCart;
  }

  set autoCompletionShoppingCart(value: boolean) {
    this._autoCompletionShoppingCart = value;
  }
}
