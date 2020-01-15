package fr.dawan.nogashi.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;


@Entity
@Component
public class CreditCard extends DbObject {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String type;						// TODO faire une enum: mastercard, american express.
	@Column(nullable = false)
	private String codeNumber;					//todo trouver un moyen pour eviter la lecture dans la bdd des informations de carte banquaire.
	@Column(nullable = false)
	private String ownerName;
	@Column(nullable = false)
	private String expirationDate;
	@Column(nullable = false)
	private String codeSecurity;				// on back of credit card.
	
	@OneToOne
	private Individual individual; 
	
	//-------------------------------
	
	public CreditCard(String type, String codeNumber, String ownerName, String expirationDate, String codeSecurity) {
		super();
		this.type = type;
		this.codeNumber = codeNumber;
		this.ownerName = ownerName;
		this.expirationDate = expirationDate;
		this.codeSecurity = codeSecurity;
	}

	public CreditCard() {
		super();
	}
	
	@Override
	public String toString() {
		return "CreditCard [type=" + type + ", ownerName=" + ownerName + ", expirationDate=" + expirationDate + "]";
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCodeNumber() {
		return codeNumber;
	}
	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getCodeSecurity() {
		return codeSecurity;
	}
	public void setCodeSecurity(String codeSecurity) {
		this.codeSecurity = codeSecurity;
	}

}
