package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Component
public class Buyer extends User {

	private static final long serialVersionUID = 1L;

	private boolean autoCompletionShoppingCart;
	
	@OneToMany
	private List<DietaryRestriction> dietaryRestrictions = new ArrayList<DietaryRestriction>();
	@OneToMany(mappedBy = "buyer")
	private List<Subscription> subscriptions = new ArrayList<Subscription>();
	
	@OneToOne(mappedBy = "buyer")
	private ShoppingCart shoppingCart;
	@OneToMany(mappedBy = "buyer")
	private List<ShoppingCart> historicShoppingCarts;
	
	
	//-------------------------------------
		
	
	public void addDietaryRestrictions(DietaryRestriction dr) {
		if(!dietaryRestrictions.contains(dr))
			dietaryRestrictions.add(dr);
	}
	public void removeDietaryRestrictions(DietaryRestriction dr) {
		if(dietaryRestrictions.contains(dr))
			dietaryRestrictions.remove(dr);
	}
	
	public void addSubscriptions(Subscription s) {
		if(!subscriptions.contains(s))
		{
			subscriptions.add(s);
			s.setBuyer(this);
		}
	}
	public void removeSubscriptions(Subscription s) {
		if(subscriptions.contains(s))
		{
			s.setBuyer(null);
			subscriptions.remove(s);
		}
	}
	
	
	
	
	
	//------------------------------
	
	@Override
	public String toString() {
		return "Buyer [ "+ toString() + " autoCompletionShoppingCart=" + autoCompletionShoppingCart +"]";
	}
	
	public Buyer(boolean autoCompletionShoppingCart) {
		super();
		this.autoCompletionShoppingCart = autoCompletionShoppingCart;
	}
	
	public Buyer() {
		super();
	}

	public List<DietaryRestriction> getDietaryRestrictions() {
		return dietaryRestrictions;
	}

	public void setDietaryRestrictions(List<DietaryRestriction> dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public boolean isAutoCompletionShoppingCart() {
		return autoCompletionShoppingCart;
	}

	public void setAutoCompletionShoppingCart(boolean autoCompletionShoppingCart) {
		this.autoCompletionShoppingCart = autoCompletionShoppingCart;
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public List<ShoppingCart> getHistoricShoppingCarts() {
		return historicShoppingCarts;
	}
	public void setHistoricShoppingCarts(List<ShoppingCart> historicShoppingCarts) {
		this.historicShoppingCarts = historicShoppingCarts;
	}
}
