package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;


@Entity
@Component
public class DietaryRestriction extends DbObject {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	
	@OneToMany
	List<ProductDetail> listBadMatch = new ArrayList<ProductDetail>();

	@ManyToOne(cascade = CascadeType.ALL)
	EnumManager enumManager;
	
	
	
	
	
	public void addBadMatch(ProductDetail pd) {
		if(!listBadMatch.contains(pd))
			listBadMatch.add(pd);
	}
	
	public void removeBadMatch(ProductDetail pd) {
		if(listBadMatch.contains(pd))
			listBadMatch.remove(pd);
	}
	
	
	
	
	
	
	
	//-------------------------------
	
	public DietaryRestriction(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	

	@Override
	public String toString() {
		return "DietaryRestriction [name=" + name + ", description=" + description + "]";
	}


	public DietaryRestriction() {
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

	public List<ProductDetail> getListBadMatch() {
		return listBadMatch;
	}

	public void setListBadMatch(List<ProductDetail> listBadMatch) {
		this.listBadMatch = listBadMatch;
	}

	public EnumManager getEnumManager() {
		return enumManager;
	}

	public void setEnumManager(EnumManager enumManager) {
		this.enumManager = enumManager;
	}
	
	
	
	
}
