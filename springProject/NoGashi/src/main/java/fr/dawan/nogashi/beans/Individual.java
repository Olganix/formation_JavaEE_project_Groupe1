package fr.dawan.nogashi.beans;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Individual extends Buyer {

	private static final long serialVersionUID = 1L;
	
	private CreditCard creditCard;

	
	
	//-----------------------------
	
	public Individual(CreditCard creditCard) {
		super(false);
		this.creditCard = creditCard;
	}

	public Individual(boolean autoCompletionShoppingCart) {
		super(autoCompletionShoppingCart);
	}
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
