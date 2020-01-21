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
public class ShoppingCartByCommerce extends DbObject {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.ORDINAL)
	private ShoppingCartStatus status;
	
	private double price;

	@ManyToOne  @LazyCollection(LazyCollectionOption.FALSE)
	private Commerce commerce;
	
	@ManyToOne @XmlTransient @JsonIgnore
	private ShoppingCart shoppingCart;
	
	@OneToMany(mappedBy = "shoppingCart") @LazyCollection(LazyCollectionOption.FALSE)	
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
			products.remove(p);
			p.setShoppingCart(null);
		}
	}
	
	public void calculPrice(){
		
		this.price = 0;
		for(Product p : products)
		{
			// todo en fonction de l'heure (+ offset) passer les elements en Promotion ou Unsold
			
			/*
			switch(p.getStatus())				//en fait ca ne marche pas , car on est en reserved ... bon cheating time. TODO faire mieux.
			{
			case AVAILABLE: 	this.price += p.getPrice(); break;
			case PROMOTION: 	this.price += p.getSalePrice(); break;
			case UNSOLD: 		this.price += 0; break;
			}
			*/
			this.price += p.getSalePrice();
		}
	}
	
	
	
	
	//-----------------------------
	
	public ShoppingCartByCommerce(Commerce commerce, ShoppingCart shoppingCart) {
		super();
		this.commerce = commerce;
		if(this.commerce!=null)
			this.commerce.addShoppingCartByCommerces(this);
		
		this.shoppingCart = shoppingCart;
		
		if(this.shoppingCart!=null)
			this.shoppingCart.addShoppingCartByCommerces(this);
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
		if(this.commerce == commerce)
			return;
		
		if(this.commerce!=null)
			this.commerce.addShoppingCartByCommerces(null);
		
		this.commerce = commerce;
		
		if(this.commerce!=null)
			this.commerce.addShoppingCartByCommerces(this);
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		if(this.shoppingCart == shoppingCart)
			return;
		
		if(this.shoppingCart!=null)
			this.shoppingCart.addShoppingCartByCommerces(null);
		
		this.shoppingCart = shoppingCart;
		
		if(this.shoppingCart!=null)
			this.shoppingCart.addShoppingCartByCommerces(this);
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
