package fr.dawan.nogashi.beans;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Individual extends Buyer {

	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private CreditCard creditCard;

	
	
	//-----------------------------
	
	public Individual(CreditCard creditCard) {
		super(false);
		this.creditCard = creditCard;
	}

	public Individual() {
		super(false);
	}
	public Individual(User user) {
		super(user);
	}

	public Individual(User user, CreditCard creditCard) {
		super(user);
		this.creditCard = creditCard;
	}
	
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
