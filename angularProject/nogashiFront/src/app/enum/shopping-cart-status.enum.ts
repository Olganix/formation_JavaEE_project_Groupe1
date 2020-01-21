import {SchedulerWeekType} from './scheduler-week-type.enum';

export enum ShoppingCartStatus {
  IN_PROGRESS, 			// En cours: l'utilisateur est en train de remplir son panier. Les produits sont reserves
  PAID, 					// Payee : la commande est payee et en cours de preparation cote Merchant
  PREPARED, 				// Preparee : la commande est prete a etre retiree par le Buyer
  CONCLUDED  				// Conclue : la commande a ete retire (ou pas mais delai depasse). La commande passe en historique
}



export function ShoppingCartStatus_toDisplayString(status: ShoppingCartStatus): string {
  switch (status) {
    case ShoppingCartStatus.IN_PROGRESS:    return 'IN_PROGRESS';
    case ShoppingCartStatus.PAID:           return 'PAID';
    case ShoppingCartStatus.PREPARED:       return 'PREPARED';
    case ShoppingCartStatus.CONCLUDED:      return 'CONCLUDED';
  }
  return '';
}

export function ShoppingCartStatus_parseFromDisplay(status: string): ShoppingCartStatus {

  switch (status) {
    case 'IN_PROGRESS':   return ShoppingCartStatus.IN_PROGRESS;
    case 'PAID':          return ShoppingCartStatus.PAID;
    case 'PREPARED':      return ShoppingCartStatus.PREPARED;
    case 'CONCLUDED':     return ShoppingCartStatus.CONCLUDED;
  }
  return null;
}
