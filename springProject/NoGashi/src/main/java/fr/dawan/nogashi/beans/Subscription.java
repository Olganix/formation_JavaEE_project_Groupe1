package fr.dawan.nogashi.beans;

public class Subscription extends DbObject {

	private static final long serialVersionUID = 1L;
	
	private Commerce commerce;
	private ProductTemplate productTemplate;
	
	
	//------------------------------
	
	public Subscription() {
		super();
	}
	
	@Override
	public String toString() {
		return "Subscription";
	}

	
	public Commerce getCommerce() {
		return commerce;
	}
	
	public void setCommerce(Commerce commerce) {
		this.commerce = commerce;
	}
	public ProductTemplate getProductTemplate() {
		return productTemplate;
	}
	public void setProductTemplate(ProductTemplate productTemplate) {
		this.productTemplate = productTemplate;
	}
	

}
