package fr.dawan.nogashi.beans;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import org.springframework.stereotype.Component;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Component
public class ProductTemplate extends DbObject {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	private String name;
	@Basic(optional = false)
	private String description;
	private String externalCode;
	private boolean isWrapped; 							// est emballé => OV : isPackaged ? Todo answer

	@Basic(optional = false)
	private double price;
	@Basic(optional = false)
	private double salePrice;
	
	private Date saleTime;								// utilisation de plages horaires plutot ? Todo use  
	private Date unsoldTime;							// de meme. Todo
	private boolean timeControlStatus;
	private int maxDurationCart;

	
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Merchant merchant;
	//@OneToMany(mappedBy = "productTemplate")
	//private List<ProductDetail> productDetails;
	//private List<Commerce> commerces;
	
	
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
	
	public ProductTemplate( ProductTemplate other)
	{
		super();
		this.name = other.name;
		this.description = other.description;
		this.externalCode = other.externalCode;
		this.isWrapped = other.isWrapped;
		this.price = other.price;
		this.salePrice = other.salePrice;
		this.saleTime = other.saleTime;
	}
	//-----------------
	public ProductTemplate() {
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
	
	
	
	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Override
	public String toString() {
		return "ProductTemplate [name=" + name + ", description=" + description + ", externalCode="
				+ externalCode + ", isWrapped=" + isWrapped + ", price=" + price + ", salePrice=" + salePrice
				+ ", saleTime=" + saleTime + ", unsoldTime=" + unsoldTime + ", timeControlStatus=" + timeControlStatus
				+ ", maxDurationCart=" + maxDurationCart + "]";
	}

	
	
	
}
