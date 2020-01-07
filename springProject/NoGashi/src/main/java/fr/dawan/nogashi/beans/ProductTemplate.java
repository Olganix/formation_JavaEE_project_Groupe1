package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	@OneToOne
	private SchedulerWeek schedulerWeekForSaleAndUnsold;	// horaires pour definir les periodes / heures ou le produit pourra être vendu en promotion, et de meme pour le status invendu.  
	
	
	
	@Column(nullable = false)
	private int maxDurationCart;

	@ManyToOne
	private Merchant merchant;
	@ManyToMany
	private List<Commerce> commerces = new ArrayList<Commerce>();
	@OneToMany
	private List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
	
	
	
	
	
	public void addCommerces(Commerce c) {
		if(!commerces.contains(c)) {
			c.addProductTemplate(this);
			commerces.add(c);
		}
	}
	public void removeCommerces(Commerce c) {
		if(commerces.contains(c)) {
			c.removeProductTemplate(this);
			commerces.remove(c);
		}
	}

	public void addProductDetails(ProductDetail p) {
		if(!productDetails.contains(p))
			productDetails.add(p);
	}
	public void removeProductDetails(ProductDetail p) {
		if(productDetails.contains(p))
			productDetails.remove(p);
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
	
	public ProductTemplate( ProductTemplate other)
	{
		super();
		this.name = other.name;
		this.description = other.description;
		this.externalCode = other.externalCode;
		this.isWrapped = other.isWrapped;
		this.price = other.price;
		this.salePrice = other.salePrice;
		this.schedulerWeekForSaleAndUnsold = other.schedulerWeekForSaleAndUnsold;
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
	
	public SchedulerWeek getSchedulerWeekForSaleAndUnsold() {
		return schedulerWeekForSaleAndUnsold;
	}

	public void setSchedulerWeekForSaleAndUnsold(SchedulerWeek schedulerWeekForSaleAndUnsold) {
		this.schedulerWeekForSaleAndUnsold = schedulerWeekForSaleAndUnsold;
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
	
	
	
	
	
	public List<Commerce> getCommerces() {
		return commerces;
	}

	public void setCommerces(List<Commerce> commerces) {
		this.commerces = commerces;
	}

	public List<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetail> productDetails) {
		this.productDetails = productDetails;
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
				+ ", timeControlStatus=" + timeControlStatus
				+ ", maxDurationCart=" + maxDurationCart + "]";
	}

	
	
	
}
