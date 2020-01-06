package fr.dawan.nogashi.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.stereotype.Component;

@Entity
@Component
public class CommerceCategory extends DbObject {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private EnumManager enumManager;

	
	
	public CommerceCategory(String name) {
		super();
		this.name = name;
	}

	
	
	
	//------------------------
	
	public CommerceCategory() {
		super();
	}

	@Override
	public String toString() {
		return "CommerceCategory [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EnumManager getEnumManager() {
		return enumManager;
	}

	public void setEnumManager(EnumManager enumManager) {
		this.enumManager = enumManager;
	}
	
	
}
