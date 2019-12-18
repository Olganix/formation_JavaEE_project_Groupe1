package fr.dawan.nogashi.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;

import org.springframework.stereotype.Component;


@Entity
@Component
public class ProductTemplate extends DbObject {

	private static final long serialVersionUID = 1L;
	
	private String name;
	//private List<productDetails> listDetails;
	private String description;
	private String externalCode;
	private boolean isWrapped; // est emballé
	private double price;
	private double salePrice;
	private Date saleTime;
	private Date unsoldTime;
	private boolean timeControlStatus;
	private int maxDurationCart;
	//private Store listStores;
	
	private String imageFilename = "NoAvatar.jpg";						//Todo do the upload system.
	

	public ProductTemplate() {
	}
	
	
	public ProductTemplate(String name, String description, String externalCode, boolean isWrapped, double price,
			double salePrice) {
		super();
		this.name = name;
		this.description = description;
		this.externalCode = externalCode;
		this.isWrapped = isWrapped;
		this.price = price;
		this.salePrice = salePrice;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
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
		return "ProductTemplate [name=" + name + ", description=" + description + ", externalCode="
				+ externalCode + ", isWrapped=" + isWrapped + ", price=" + price + ", salePrice=" + salePrice
				+ ", saleTime=" + saleTime + ", unsoldTime=" + unsoldTime + ", timeControlStatus=" + timeControlStatus
				+ ", maxDurationCart=" + maxDurationCart + "]";
	}

	
	
	
}
