package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.stereotype.Component;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Component
public class ProductTemplate extends DbObject {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	private String externalCode;
	private boolean isWrapped; 							// est emballé => OV : isPackaged ? Todo answer

	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private double salePrice;
	
	private boolean timeControlStatus;					// for automatic switch on hours.
	private Date saleTime;								// utilisation de plages horaires plutot ? Todo use  SchedulerWeek pour les deux 
	private Date unsoldTime;							// Note: je ne le change pas de suite car il est en cours de dev. mais ca serait bien de le faire rapidement.
	//@OneToOne
	//private SchedulerWeek schedulerWeekForSaleAndUnsold;	// horaires pour definir les periodes / heures ou le produit pourra être vendu en promotion, et de meme pour le status invendu.  
	
	
	
	@Column(nullable = false)
	private int maxDurationCart;

	@ManyToOne(cascade = CascadeType.ALL)
	private Merchant merchant;
	@ManyToMany(mappedBy = "productTemplates")
	private List<Commerce> commerces = new ArrayList<Commerce>();
	@OneToMany
	private List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
	
	
	
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
