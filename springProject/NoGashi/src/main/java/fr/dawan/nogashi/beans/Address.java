package fr.dawan.nogashi.beans;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Address extends DbObject {
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, length = 500)
	private String address;
	private String addressExtra;
	@Column(nullable = false)
	private String postalCode;
	@Column(nullable = false)
	private String cityName;
	@Column(nullable = false)
	private String stateName;
	
	private Double longitude = null;
	private Double latitude = null;
	
	
	public Address(String address, String addressExtra, String postalCode, String cityName, String stateName, Double longitude, Double latitude) {
		super();
		this.address = address;
		this.addressExtra = addressExtra;
		this.postalCode = postalCode;
		this.cityName = cityName;
		this.stateName = stateName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Address(Address other) {
		super();
		this.address = (other.address!= null) ? (new String(other.address)) : null;
		this.addressExtra = (other.addressExtra!= null) ? (new String(other.addressExtra)) : null;
		this.postalCode = (other.postalCode!= null) ? (new String(other.postalCode)) : null;
		this.cityName = (other.cityName!= null) ? (new String(other.cityName)) : null;
		this.stateName = (other.stateName!= null) ? (new String(other.stateName)) : null;
		this.longitude = other.longitude;
		this.latitude = other.latitude;
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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	

}
