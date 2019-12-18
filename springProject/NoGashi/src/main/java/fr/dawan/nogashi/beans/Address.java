package fr.dawan.nogashi.beans;

import javax.persistence.Basic;
import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Address extends DbObject {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	private String address;
	private String addressExtra;
	@Basic(optional = false)
	private String postalCode;
	@Basic(optional = false)
	private String cityName;
	@Basic(optional = false)
	private String stateName;
	
	
	
	
	public Address(String address, String addressExtra, String postalCode, String cityName, String stateName) {
		super();
		this.address = address;
		this.addressExtra = addressExtra;
		this.postalCode = postalCode;
		this.cityName = cityName;
		this.stateName = stateName;
	}

	public Address(Address other) {
		super();
		this.address = (other.address!= null) ? (new String(other.address)) : null;
		this.addressExtra = (other.addressExtra!= null) ? (new String(other.addressExtra)) : null;
		this.postalCode = (other.postalCode!= null) ? (new String(other.postalCode)) : null;
		this.cityName = (other.cityName!= null) ? (new String(other.cityName)) : null;
		this.stateName = (other.stateName!= null) ? (new String(other.stateName)) : null;
	}
	
	//-------------------------------
	
	
	
	

	@Override
	public String toString() {
		return "Address [address=" + address + ", addressExtra=" + addressExtra + ", postalCode=" + postalCode
				+ ", cityName=" + cityName + ", stateName=" + stateName + "]";
	}

	public Address() {
		super();
	}

	public String getAddress() {
		return address;
	}


	public void setAddress(String adress) {
		this.address = adress;
	}


	public String getAddressExtra() {
		return addressExtra;
	}


	public void setAddressExtra(String adressExtra) {
		this.addressExtra = adressExtra;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	

}
