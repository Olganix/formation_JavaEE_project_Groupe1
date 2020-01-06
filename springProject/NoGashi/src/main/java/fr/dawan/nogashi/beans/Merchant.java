package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Merchant extends User {

	private static final long serialVersionUID = 1L;

	private String codeSiren;
	private String codeIBAN;
	private String codeBic;
	
	
	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
	List<Commerce> commerces = new ArrayList<Commerce>();
	
	@OneToMany(mappedBy = "merchant")
	List<ProductTemplate> productTemplates = new ArrayList<ProductTemplate>();
	
	
	public Merchant(Merchant other) {
		super((User)other);
		this.codeSiren = (other.codeSiren!= null) ? (new String(other.codeSiren)) : null;
		this.codeIBAN = (other.codeIBAN!= null) ? (new String(other.codeIBAN)) : null;
		this.codeBic = (other.codeBic!= null) ? (new String(other.codeBic)) : null;
	}
	
	public Merchant(User other) {
		super(other);
	}
	
	
	public void addCommerces(Commerce c) {
		
		if(!this.commerces.contains(c))
		{
			this.commerces.add(c);
			c.setMerchant(this);	
		}	
	}
	
	public void removeCommerces(Commerce c) 
	{	
		if(this.commerces.contains(c))
		{
			c.setMerchant(null);
			this.commerces.remove(c);	
		}	
	}
	
	/*
	public void addProductTemplate(ProductTemplate pt) {
		
		if(!this.productTemplates.contains(pt))
		{
			this.productTemplates.add(pt);
			pt.setMerchant(this);	
		}	
	}
	public void removeProductTemplate(ProductTemplate pt) 
	{	
		if(this.productTemplates.contains(pt))
		{
			pt.setMerchant(null);
			this.productTemplates.remove(pt);	
		}	
	}
	*/
	

	
	//--------------------------------------------
	
	public Merchant() {
		super();
	}
	
	public String getCodeSiren() {
		return codeSiren;
	}
	public void setCodeSiren(String codeSiren) {
		this.codeSiren = codeSiren;
	}
	public String getCodeIBAN() {
		return codeIBAN;
	}
	public void setCodeIBAN(String codeIBAN) {
		this.codeIBAN = codeIBAN;
	}
	public String getCodeBic() {
		return codeBic;
	}
	public void setCodeBic(String codeBic) {
		this.codeBic = codeBic;
	}
	
	public List<Commerce> getCommerces() {
		return commerces;
	}
	public void setCommerces(List<Commerce> commerces) {
		this.commerces = commerces;
	}

	/*
	public List<ProductTemplate> getProductTemplates() {
		return productTemplates;
	}

	public void setProductTemplates(List<ProductTemplate> productTemplates) {
		this.productTemplates = productTemplates;
	}
	*/
	
	
	
}
