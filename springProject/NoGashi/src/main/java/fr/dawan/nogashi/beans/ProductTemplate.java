package fr.dawan.nogashi.beans;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Joffrey
 * Fiche produit
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Component
public class ProductTemplate extends DbObject {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	private String name;
	private String description;
	private String externalCode;
	private boolean isWrapped; 							// est emballé => OV : isPackaged ? Todo answer
	private float price;
	private float salePrice;
	private Date saleTime;
	private Date unsoldTime;
	private boolean timeControlStatus;
	private int maxDurationCart;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Merchant merchant;
	//@OneToMany(mappedBy = "productTemplate")
	//private List<ProductDetail> productDetails;
	//private List<Commerce> commerces;
	
	
	
	
	
	
	
	public ProductTemplate(int id, String name, String description, String externalCode, boolean isWrapped, float price,
			float salePrice, Date saleTime) {
		super();
		this.name = name;
		this.description = description;
		this.externalCode = externalCode;
		this.isWrapped = isWrapped;
		this.price = price;
		this.salePrice = salePrice;
		this.saleTime = saleTime;
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
	
	//--------------------------------------------
	
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
