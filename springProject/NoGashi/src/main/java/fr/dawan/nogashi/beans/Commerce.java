package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;


@Entity
@Component
public class Commerce extends DbObject {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	private String name;
	@Basic(optional = false)
	private String codeSiret;
	@Basic(optional = false)
	private String uniqueIdName;					//to make difference between 2 subway in the same city (or the same street), you add a unique subName to make them different.
	@Basic(optional = false)
	private String description;
	@Basic(optional = false)
	private Address address;
	@Basic(optional = false)
	private String schedule;						// horaires définit en string.  Todo : a week calendar + complements. + a holiday calendar.
	
	private String pictureLogo = "NoLogo.jpg";					// logo for the merchant's (or commerce's) mark
	private String pictureDescription = "NoDescription.jpg";	// real picture of commerce, or patchwork to describe the commerce.
	private boolean isOpened = false;
	
	@Basic(optional = false)
	@ManyToOne(cascade = CascadeType.ALL)
	private Merchant merchant;
	
	@OneToMany
	private List<CommerceCategory> commerceCategories = new ArrayList<CommerceCategory>();
	@OneToMany
	private List<ProductTemplate> productTemplates = new ArrayList<ProductTemplate>();
	@OneToMany(mappedBy = "commerce")
	private List<Product> products = new ArrayList<Product>();
	
	@OneToOne
	private Faq faq = null;


	
	
	
	public Commerce(String name, String codeSiret, String description, String uniqueIdName, Address address,
			String schedule, String pictureLogo, String pictureDescription, Merchant merchant) {
		super();
		this.name = name;
		this.codeSiret = codeSiret;
		this.description = description;
		this.uniqueIdName = uniqueIdName;
		this.address = address;
		this.schedule = schedule;
		this.pictureLogo = pictureLogo;
		this.pictureDescription = pictureDescription;
		this.merchant = merchant;
	}
	
	
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
	
	public void addProductTemplate(ProductTemplate pt) 
	{	
		if(!this.productTemplates.contains(pt))
			this.productTemplates.add(pt);	
	}
	public void removeProductTemplate(ProductTemplate pt) 
	{	
		if(this.productTemplates.contains(pt))
			this.productTemplates.remove(pt);	
	}
	
	
	public void addProduct(Product p) 
	{	
		if(!this.products.contains(p))
		{
			this.products.add(p);
			p.setCommerce(this);
		}
	}
	public void removeProduct(Product p) 
	{	
		if(this.products.contains(p))
		{
			p.setCommerce(null);
			this.products.remove(p);
		}
	}
	public void clearProduct() 
	{	
		for(Product p : this.products)
			p.setCommerce(null);
		
		this.products.clear();		
	}
	
	
	
	
	
	
	
	
	//----------------------------
	
	public Commerce() {
		super();
	}	

	@Override
	public String toString() {
		return "Commerce [name=" + name + ", description=" + description + ", uniqueIdName=" + uniqueIdName
				+ ", isOpened=" + isOpened + ", merchant=" + merchant + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeSiret() {
		return codeSiret;
	}

	public void setCodeSiret(String codeSiret) {
		this.codeSiret = codeSiret;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUniqueIdName() {
		return uniqueIdName;
	}

	public void setUniqueIdName(String uniqueIdName) {
		this.uniqueIdName = uniqueIdName;
	}

	public boolean isOpened() {
		return isOpened;
	}

	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getPictureLogo() {
		return pictureLogo;
	}

	public void setPictureLogo(String pictureLogo) {
		this.pictureLogo = pictureLogo;
	}

	public String getPictureDescription() {
		return pictureDescription;
	}

	public void setPictureDescription(String pictureDescription) {
		this.pictureDescription = pictureDescription;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public List<CommerceCategory> getCommerceCategories() {
		return commerceCategories;
	}

	public void setCommerceCategories(List<CommerceCategory> commerceCategories) {
		this.commerceCategories = commerceCategories;
	}

	public List<ProductTemplate> getProductTemplates() {
		return productTemplates;
	}

	public void setProductTemplates(List<ProductTemplate> productTemplates) {
		this.productTemplates = productTemplates;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Faq getFaq() {
		return faq;
	}

	public void setFaq(Faq faq) {
		this.faq = faq;
	}
	
	
	
	
	
	
}
