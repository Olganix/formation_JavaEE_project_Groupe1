package fr.dawan.nogashi.beans;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

public class ProductDetail extends DbObject {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	EnumManager enumManager;
	
	
	//------------------------------
	
	public ProductDetail(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "ProductDetail [name=" + name + ", description=" + description + "]";
	}

	public ProductDetail() {
		super();
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

	public EnumManager getEnumManager() {
		return enumManager;
	}

	public void setEnumManager(EnumManager enumManager) {
		this.enumManager = enumManager;
	}
	
	
}
