package fr.dawan.nogashi.beanJson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.dawan.nogashi.beans.Commerce;
import fr.dawan.nogashi.beans.ProductTemplate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement
public class ProductTemplateListForAdd implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<ProductTemplate, Map<Commerce, Integer>> productTemplates_commerces_numberProduct;		// numberProduct for them witch dont have shoppingcart

	private List<ProductTemplate> productTemplates = new ArrayList<ProductTemplate>();
	private List<Commerce> commerces = new ArrayList<Commerce>();
	private List<Integer> listIndexPtEachCommerce = new ArrayList<Integer>();
	private List<Integer> listNbProductEachCommerce = new ArrayList<Integer>();
	
	
	
	public void add(ProductTemplate pt, Commerce c, Integer nbProduct)
	{
		if(!productTemplates.contains(pt))
			productTemplates.add(pt);
		Integer index = productTemplates.indexOf(pt);
		
		commerces.add(c);
		listIndexPtEachCommerce.add(index);
		listNbProductEachCommerce.add(nbProduct);
	}
	
	
	
	// ----------------------
	
	public ProductTemplateListForAdd() {
		super();
	}



	public Map<ProductTemplate, Map<Commerce, Integer>> getProductTemplates_commerces_numberProduct() {
		return productTemplates_commerces_numberProduct;
	}



	public void setProductTemplates_commerces_numberProduct(
			Map<ProductTemplate, Map<Commerce, Integer>> productTemplates_commerces_numberProduct) {
		this.productTemplates_commerces_numberProduct = productTemplates_commerces_numberProduct;
	}



	public List<ProductTemplate> getProductTemplates() {
		return productTemplates;
	}



	public void setProductTemplates(List<ProductTemplate> productTemplates) {
		this.productTemplates = productTemplates;
	}



	public List<Commerce> getCommerces() {
		return commerces;
	}



	public void setCommerces(List<Commerce> commerces) {
		this.commerces = commerces;
	}



	public List<Integer> getListIndexPtEachCommerce() {
		return listIndexPtEachCommerce;
	}



	public void setListIndexPtEachCommerce(List<Integer> listIndexPtEachCommerce) {
		this.listIndexPtEachCommerce = listIndexPtEachCommerce;
	}



	public List<Integer> getListNbProductEachCommerce() {
		return listNbProductEachCommerce;
	}



	public void setListNbProductEachCommerce(List<Integer> listNbProductEachCommerce) {
		this.listNbProductEachCommerce = listNbProductEachCommerce;
	}
	
	
}
