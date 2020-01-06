package fr.dawan.nogashi.beans;

public class Subscription extends DbObject {

	private static final long serialVersionUID = 1L;
	
	
	private Buyer buyer;
	private Commerce commerce;
	private ProductTemplate productTemplate;
	
	
	//------------------------------
	
	public Subscription() {
		super();
	}
	public Subscription(Buyer buyer, Commerce commerce, ProductTemplate productTemplate) {
		super();
		this.buyer = buyer;
		this.commerce = commerce;
		this.productTemplate = productTemplate;
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
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
}
