package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

// import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Component
@XmlRootElement
public class ProductTemplate extends DbObject {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, length = 500)
	private String name;
	
	@Column(nullable = false, length = 1000)
	private String description;
	
	private String externalCode;
	
	private boolean isPackaged; 							// est emballé 

	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private double salePrice;
	
	private boolean timeControlStatus;					// for automatic switch on hours.
	
	@XmlTransient
	@OneToOne
	private SchedulerWeek schedulerWeekForSaleAndUnsold;	// horaires pour definir les periodes / heures ou le produit pourra être vendu en promotion, et de meme pour le status invendu.  
	
	
	
	@Column(nullable = false)
	private int maxDurationCart;
	
	@Column(nullable = false)
	private String image;

	
	
	@ManyToOne @JsonIgnore @XmlTransient			//Todo mettre les JsonIgnore XmlTransient aux endroit equivalent , cf lire le typescript.
	private Merchant merchant;
	
	@ManyToMany @XmlTransient @JsonIgnore 
	private List<Commerce> commerces = new ArrayList<Commerce>();
	@OneToMany	@XmlTransient	@JsonIgnore
	private List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
	
	
	public Product createProduct(Commerce c)
	{
		return new Product(this, c);
	}
	
	
	public void addCommerces(Commerce c) {
		if(!commerces.contains(c)) {
			commerces.add(c);
			c.addProductTemplate(this);
		}
	}
	public void removeCommerces(Commerce c) {
		if(commerces.contains(c)) {
			commerces.remove(c);
			c.removeProductTemplate(this);
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
	
	
	
	
	public ProductTemplate(String name, String description, String externalCode, boolean isPackaged, double price,
			double salePrice, String image) {
		super();
		this.name = name;
		this.description = description;
		this.externalCode = externalCode;
		this.isPackaged = isPackaged;
		this.price = price;
		this.salePrice = salePrice;
		this.image = image;
	}
	
	
	
	
	public ProductTemplate(String name, String description, String externalCode, boolean isPackaged, double price,
			double salePrice, String image, SchedulerWeek schedulerWeekForSaleAndUnsold, int maxDurationCart,
			List<ProductDetail> productDetails) {
		super();
		this.name = name;
		this.description = description;
		this.externalCode = externalCode;
		this.isPackaged = isPackaged;
		this.price = price;
		this.salePrice = salePrice;
		this.image = image;
		this.schedulerWeekForSaleAndUnsold = schedulerWeekForSaleAndUnsold;
		this.maxDurationCart = maxDurationCart;
		this.productDetails = productDetails;
	}


	public ProductTemplate(String name, String description, String externalCode, boolean isPackaged, double price,
			double salePrice, SchedulerWeek schedulerWeekForSaleAndUnsold, int maxDurationCart, String image) {
		super();
		this.name = name;
		this.description = description;
		this.externalCode = externalCode;
		this.isPackaged = isPackaged;
		this.price = price;
		this.salePrice = salePrice;
		this.schedulerWeekForSaleAndUnsold = schedulerWeekForSaleAndUnsold;
		this.maxDurationCart = maxDurationCart;
		this.image = image;
	}


	public ProductTemplate( ProductTemplate other)
	{
		super();
		this.name = other.name;
		this.description = other.description;
		this.externalCode = other.externalCode;
		this.isPackaged = other.isPackaged;
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
	public boolean isPackaged() {
		return isPackaged;
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
	
	
	
	
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setPackaged(boolean isPackaged) {
		this.isPackaged = isPackaged;
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

		if(this.merchant==merchant)					//avoid infinity loops
			return;
		
		if(this.merchant!=null)
			merchant.removeProductTemplate(this);
		
		this.merchant = merchant;

		if(this.merchant!=null)
			merchant.addProductTemplate(this);
	}

	@Override
	public String toString() {
		return "ProductTemplate [name=" + name + ", description=" + description + ", externalCode="
				+ externalCode + ", isPackaged=" + isPackaged + ", price=" + price + ", salePrice=" + salePrice
				+ ", timeControlStatus=" + timeControlStatus
				+ ", maxDurationCart=" + maxDurationCart + "]";
	}

	
	
	
}
