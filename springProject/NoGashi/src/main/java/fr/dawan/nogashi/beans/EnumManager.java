package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;


// the name "EnumManagers" could be not so good to understand, but it's about handle all possibles values.
// like all CommerceCategories, for a Merchant choice what he want, nothing else.
// it's managed by admin, and have filled bu reading the Bdd.


// TODO : make it singleton , (but also have a unique table in bdd)

@Entity
@Component
public class EnumManager extends DbObject {						 

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "enumManager")
	private List<CommerceCategory> commerceCategories = new ArrayList<CommerceCategory>();
	@OneToMany(mappedBy = "enumManager")
	private List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
	@OneToMany(mappedBy = "enumManager")
	private List<DietaryRestriction> dietaryRestrictions = new ArrayList<DietaryRestriction>();

	private long timeOffset;
	
	
	public void addCommerceCategory(CommerceCategory cc) 
	{	
		if(!this.commerceCategories.contains(cc))
		{
			this.commerceCategories.add(cc);
			cc.setEnumManager(this);
		}
	}
	public void removeCommerceCategory(CommerceCategory cc) 
	{	
		if(this.commerceCategories.contains(cc))
		{
			cc.setEnumManager(null);
			this.commerceCategories.remove(cc);
		}
	}
	
	public void addProductDetail(ProductDetail pd) 
	{	
		if(!this.productDetails.contains(pd))
		{
			this.productDetails.add(pd);
			pd.setEnumManager(this);
		}
	}
	public void removeProductDetail(ProductDetail pd) 
	{	
		if(this.productDetails.contains(pd))
		{
			pd.setEnumManager(null);
			this.productDetails.remove(pd);
		}
	}
	
	public void addDietaryRestriction(DietaryRestriction dr) 
	{	
		if(!this.dietaryRestrictions.contains(dr))
		{
			this.dietaryRestrictions.add(dr);
			dr.setEnumManager(this);
		}
	}
	public void removeDietaryRestriction(DietaryRestriction dr) 
	{	
		if(this.dietaryRestrictions.contains(dr))
		{
			dr.setEnumManager(null);
			this.dietaryRestrictions.remove(dr);
		}
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
	public List<ProductDetail> getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(List<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}
	public List<DietaryRestriction> getDietaryRestrictions() {
		return dietaryRestrictions;
	}
	public void setDietaryRestrictions(List<DietaryRestriction> dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}
	public long getTimeOffset() {
		return timeOffset;
	}
	public void setTimeOffset(long timeOffset) {
		this.timeOffset = timeOffset;
	}
	
	
	
}
