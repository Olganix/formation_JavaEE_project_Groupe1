package fr.dawan.nogashi.beans;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.ProductStatus;


@Entity
@Component
public class Product extends ProductTemplate {
	
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	private ProductTemplate reference;								// Product are created from a productTemplate. (but could override value for adaptive situation, so that extends the ProductTemplate)
	@Basic(optional = false)
	@ManyToOne(cascade = CascadeType.ALL)
	private Commerce commerce;
	
	@Enumerated(EnumType.ORDINAL)
	private ProductStatus status = ProductStatus.AVAILABLE;
	private String typedName = null;								//Je ne me souvient plus a quoi il servait ce champ la. peut etre pour rajouter une info quand on personalise le produit, genre "cramé". Todo find/check
	
	private ShoppingCartByCommerce shoppingCart;
	
	
	
	
	
	public Product(ProductTemplate reference, Commerce commerce) {
		super(reference);
		this.reference = reference;
		this.commerce = commerce;
	}



	//----------------------------------------
	
	public Product() {
		super();
	}

	public ProductStatus getStatus() {
		return status;
	}
	public void setStatus(ProductStatus status) {
		this.status = status;
	}
	public String getTypedName() {
		return typedName;
	}
	public void setTypedName(String typedName) {
		this.typedName = typedName;
	}
	public ProductTemplate getReference() {
		return reference;
	}
	public void setReference(ProductTemplate reference) {
		this.reference = reference;
	}
	public Commerce getCommerce() {
		return commerce;
	}
	public void setCommerce(Commerce commerce) {
		this.commerce = commerce;
	}

	@Override
	public String toString() {
		return "Product [ id=" + getId() + ", name=" + getName() + ", description=" + getDescription() + ", externalCode="
				+ getExternalCode() + ", isWrapped=" + isWrapped() + ", price=" + getPrice() + ", salePrice=" + getSalePrice()
				+ ", saleTime=" + getSaleTime() + ", unsoldTime=" + getUnsoldTime() + ", timeControlStatus=" + isTimeControlStatus()
				+ ", maxDurationCart=" + getMaxDurationCart() +", status=" + status + ", typedName=" + typedName + "]";
	}

	public ShoppingCartByCommerce getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCartByCommerce shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
}
