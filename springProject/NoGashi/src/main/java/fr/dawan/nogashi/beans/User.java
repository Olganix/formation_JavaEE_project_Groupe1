package fr.dawan.nogashi.beans;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.UserRole;

@Entity
@Component
public class User extends DbObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	private String name;
	@Basic(optional = false)
	private String email;
	@Basic(optional = false)
	private String password;
	
	@Enumerated(EnumType.ORDINAL)
	private UserRole role = UserRole.INDIVIDUAL;
	
	private String avatarFilename = "NoAvatar.jpg";						//Todo do the upload system.
	
	private String phoneNumber;											//Todo many possible ?
	private String adress;
	private String adressExtra;
	private String postalCode;
	private String cityName;
	private String stateName;
	
	
	//-----------------------------
	
	public User() {
		super();
	}
	
	public User(String name, String email, UserRole role) {
		super();
		this.name = name;
		this.email = email;
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", role=" + role + "]";
	}

	//-----------------------------
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public UserRole getRole() {
		return role;
	}


	public void setRole(UserRole role) {
		this.role = role;
	}


	public String getAvatarFilename() {
		return avatarFilename;
	}


	public void setAvatarFilename(String avatarFilename) {
		this.avatarFilename = avatarFilename;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getAdress() {
		return adress;
	}


	public void setAdress(String adress) {
		this.adress = adress;
	}


	public String getAdressExtra() {
		return adressExtra;
	}


	public void setAdressExtra(String adressExtra) {
		this.adressExtra = adressExtra;
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
