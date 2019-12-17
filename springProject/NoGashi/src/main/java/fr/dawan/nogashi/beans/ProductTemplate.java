package fr.dawan.nogashi.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Joffrey
 * Fiche produit
 *
 */
@Entity
@Component
public class ProductTemplate extends DbObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	private int id;
	private String name;
	//private List<productDetails> listDetails;
	private String description;
	private String externalCode;
	private boolean isWrapped; // est emballé
	private float price;
	private float salePrice;
	private Date saleTime;
	private Date unsoldTime;
	private boolean timeControlStatus;
	private int maxDurationCart;
	//private Store listStores;
	
	private String imageFilename = "NoAvatar.jpg";						//Todo do the upload system.
	

	public ProductTemplate() {
	}
	public ProductTemplate(int id, String name, String description, String externalCode, boolean isWrapped, float price,
			float salePrice, Date saleTime) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.externalCode = externalCode;
		this.isWrapped = isWrapped;
		this.price = price;
		this.salePrice = salePrice;
		this.saleTime = saleTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExternalCode() {
		return externalCode;
	}
	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}
	public boolean isWrapped() {
		return isWrapped;
	}
	public void setWrapped(boolean isWrapped) {
		this.isWrapped = isWrapped;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}
	public Date getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
	public Date getUnsoldTime() {
		return unsoldTime;
	}
	public void setUnsoldTime(Date unsoldTime) {
		this.unsoldTime = unsoldTime;
	}
	public boolean isTimeControlStatus() {
		return timeControlStatus;
	}
	public void setTimeControlStatus(boolean timeControlStatus) {
		this.timeControlStatus = timeControlStatus;
	}
	public int getMaxDurationCart() {
		return maxDurationCart;
	}
	public void setMaxDurationCart(int maxDurationCart) {
		this.maxDurationCart = maxDurationCart;
	}
	
	@Override
	public String toString() {
		return "ProductTemplate [id=" + id + ", name=" + name + ", description=" + description + ", externalCode="
				+ externalCode + ", isWrapped=" + isWrapped + ", price=" + price + ", salePrice=" + salePrice
				+ ", saleTime=" + saleTime + ", unsoldTime=" + unsoldTime + ", timeControlStatus=" + timeControlStatus
				+ ", maxDurationCart=" + maxDurationCart + "]";
	}

	
	
	
}
