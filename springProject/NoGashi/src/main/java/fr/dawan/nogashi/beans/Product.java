package fr.dawan.nogashi.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.dawan.nogashi.enums.ProductStatus;


@Entity
@Component
public class Product extends ProductTemplate {
	
	private static final long serialVersionUID = 1L;
	
	@OneToOne @LazyCollection(LazyCollectionOption.FALSE)
	private ProductTemplate reference;								// Product are created from a productTemplate. (but could override value for adaptive situation, so that extends the ProductTemplate)
	@ManyToOne(cascade = CascadeType.ALL)
	private Commerce commerce;
	
	@Enumerated(EnumType.ORDINAL)
	private ProductStatus status = ProductStatus.AVAILABLE;
	@Column(length = 500)
	private String typedName = null;								// ajoute une info qui personalise le produit (par rapport au ProductTemplate), genre "cramé"
	
	@ManyToOne @XmlTransient @JsonIgnore
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
				+ getExternalCode() + ", isPackaged=" + isPackaged() + ", price=" + getPrice() + ", salePrice=" + getSalePrice()
				+ ", timeControlStatus=" + isTimeControlStatus()
				+ ", maxDurationCart=" + getMaxDurationCart() +", status=" + status + ", typedName=" + typedName + "]";
	}

	public ShoppingCartByCommerce getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCartByCommerce shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
}
