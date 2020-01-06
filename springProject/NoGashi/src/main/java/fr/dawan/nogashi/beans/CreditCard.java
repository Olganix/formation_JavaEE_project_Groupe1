package fr.dawan.nogashi.beans;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;

import org.springframework.stereotype.Component;


@Entity
@Component
public class CreditCard extends DbObject {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	private String type;						// mastercard, american express.
	@Basic(optional = false)
	private String codeNumber;					//todo trouver un moyen pour eviter la lecture dans la bdd des informations de carte banquaire.
	@Basic(optional = false)
	private String ownerName;
	@Basic(optional = false)
	private Date expirationDate;
	@Basic(optional = false)
	private String codeSecurity;				// on back of credit card.
	
	
	
	
	//-------------------------------
	
	public CreditCard(String type, String codeNumber, String ownerName, Date expirationDate, String codeSecurity) {
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
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getCodeSecurity() {
		return codeSecurity;
	}
	public void setCodeSecurity(String codeSecurity) {
		this.codeSecurity = codeSecurity;
	}

}
