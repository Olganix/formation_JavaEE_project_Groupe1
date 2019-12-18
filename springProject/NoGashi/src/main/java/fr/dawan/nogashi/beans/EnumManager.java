package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;


// the name "EnumManagers" could be not so good to understand, but it's about handle all possibles values.
// like all CommerceCategories, for a Merchant choice what he want, nothing else.
// it's managed by admin, and have filled bu reading the Bdd.


//todo : make it singleton , (but also have a unique table in bdd)

@Entity
@Component
public class EnumManager extends DbObject {						 

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "enumManager")
	private List<CommerceCategory> commerceCategories = new ArrayList<CommerceCategory>();
	//todo all contrainteAlimentaires / detailsProduct , etc ...
	
	
	

	
	
	public void addCommerceCategory(CommerceCategory cc) 
	{	
		if(!this.commerceCategories.contains(cc))
			this.commerceCategories.add(cc);	
	}
	public void removeCommerceCategory(CommerceCategory cc) 
	{	
		if(this.commerceCategories.contains(cc))
			this.commerceCategories.remove(cc);	
	}
	
	//--------------------------------------
	
	public EnumManager() {
		super();
	}



	public List<CommerceCategory> getCommerceCategories() {
		return commerceCategories;
	}

	public void setCommerceCategories(List<CommerceCategory> commerceCategories) {
		this.commerceCategories = commerceCategories;
	}
	
	
	
	
}
