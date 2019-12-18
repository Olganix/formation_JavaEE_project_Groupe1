package fr.dawan.nogashi.beans;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.ProductStatus;

/**
 * 
 * @author Joffrey
 * Instance d'un produit
 */
@Entity
@Component
public class Product extends ProductTemplate {

	private static final long serialVersionUID = 1L;
	private ProductStatus status;
	private String typedName;

	public Product() {
	
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

	@Override
	public String toString() {
		return "Product [status=" + status + ", typedName=" + typedName + "]";
	}
	
	

}
