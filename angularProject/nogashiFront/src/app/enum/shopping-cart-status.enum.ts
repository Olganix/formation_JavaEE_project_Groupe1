export enum ShoppingCartStatus {
  IN_PROGRESS, 			// En cours: l'utilisateur est en train de remplir son panier. Les produits sont reserves
  PAID, 					// Payee : la commande est payee et en cours de preparation cote Merchant
  PREPARED, 				// Preparee : la commande est prete a etre retiree par le Buyer
  CONCLUDED  				// Conclue : la commande a ete retire (ou pas mais delai depasse). La commande passe en historique
}
