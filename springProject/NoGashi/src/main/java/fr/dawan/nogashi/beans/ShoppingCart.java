package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.dawan.nogashi.enums.ShoppingCartStatus;

@Entity
@Component
public class ShoppingCart extends DbObject {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.ORDINAL)
	private ShoppingCartStatus status;
	
	private double price;
	
	
	@ManyToOne @XmlTransient @JsonIgnore
	private Buyer buyer;
	
	@OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)  @LazyCollection(LazyCollectionOption.FALSE)	
	private List<ShoppingCartByCommerce> shoppingCartByCommerces = new ArrayList<ShoppingCartByCommerce>();
	
	
	
	
	
	public ShoppingCartByCommerce getShoppingCartByCommerce(Commerce c) {
		
		for(ShoppingCartByCommerce sc : shoppingCartByCommerces)
			if(sc.getCommerce()==c)
				return sc;
		
		return null;
	}
	
	public ShoppingCartByCommerce getShoppingCartByCommerce(int id) {
		
		for(ShoppingCartByCommerce sc : shoppingCartByCommerces)
			if(sc.getCommerce().getId()==id)
				return sc;
		return null;
	}
	
	
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
	
	
	public void calculPrice(){
		
		this.price = 0;
		for(ShoppingCartByCommerce scbc : shoppingCartByCommerces)
		{
			scbc.calculPrice();
			this.price += scbc.getPrice();
		}
	}
	
	
	//-------------------------------
	

	public ShoppingCart(Buyer buyer) {
		super();
		this.buyer = buyer;
		if(this.buyer!=null)
			this.buyer.setShoppingCart(this);
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
		if(this.buyer == buyer)
			return;
		
		if(this.buyer!=null)
			this.buyer.setShoppingCart(null);
		
		this.buyer = buyer;
		
		if(this.buyer!=null)
			this.buyer.setShoppingCart(this);
	}
	public ShoppingCartStatus getStatus() {
		return status;
	}
	public void setStatus(ShoppingCartStatus status) {
		this.status = status;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}	
	
}
