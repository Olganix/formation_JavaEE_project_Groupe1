package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends DbObject {

	private static final long serialVersionUID = 1L;

	private List<ShoppingCartByCommerce> shoppingCartByCommerces = new ArrayList<ShoppingCartByCommerce>();
	
	private Buyer buyer;
	private ShoppingCart shoppingCart;

	
	
	
	public void addShoppingCartByCommerces(ShoppingCartByCommerce sc) {
		
		if(!shoppingCartByCommerces.contains(sc))
		{
			shoppingCartByCommerces.add(sc);
			sc.setShoppingCart(this);
		}
	}
	
	public void removeShoppingCartByCommerces(ShoppingCartByCommerce sc) {
		
		if(shoppingCartByCommerces.contains(sc))
		{
			sc.setShoppingCart(null);
			shoppingCartByCommerces.remove(sc);
		}
	}
	
	//-------------------------------
	

	public ShoppingCart(Buyer buyer) {
		super();
		this.buyer = buyer;
	}

	public ShoppingCart() {
		super();
	}
	public List<ShoppingCartByCommerce> getShoppingCartByCommerces() {
		return shoppingCartByCommerces;
	}
	public void setShoppingCartByCommerces(List<ShoppingCartByCommerce> shoppingCartByCommerces) {
		this.shoppingCartByCommerces = shoppingCartByCommerces;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}


	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}


	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
}
