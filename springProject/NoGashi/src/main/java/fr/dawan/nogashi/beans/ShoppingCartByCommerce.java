package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.ShoppingCartStatus;

@Entity
@Component
public class ShoppingCartByCommerce extends DbObject {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.ORDINAL)
	private ShoppingCartStatus status;

	@ManyToOne
	private Commerce commerce;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private ShoppingCart shoppingCart;
	
	@OneToMany(mappedBy = "shoppingCart")
	private List<Product> products = new ArrayList<Product>();
	
	
	
	public void addProduct(Product p) {
		if(!products.contains(p))
		{
			products.add(p);
			p.setShoppingCart(this);
		}
	}
	public void removeProduct(Product p) {
		if(products.contains(p))
		{
			p.setShoppingCart(null);
			products.remove(p);
		}
	}
	
	
	
	//-----------------------------
	
	public ShoppingCartByCommerce(Commerce commerce, ShoppingCart shoppingCart) {
		super();
		this.commerce = commerce;
		this.shoppingCart = shoppingCart;
	}

	public ShoppingCartByCommerce() {
		super();
	}

	@Override
	public String toString() {
		return "ShoppingCartByCommerce [commerce=" + commerce + ", shoppingCart=" + shoppingCart + "]";
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Commerce getCommerce() {
		return commerce;
	}

	public void setCommerce(Commerce commerce) {
		this.commerce = commerce;
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public ShoppingCartStatus getStatus() {
		return status;
	}
	public void setStatus(ShoppingCartStatus status) {
		this.status = status;
	}
	
}
