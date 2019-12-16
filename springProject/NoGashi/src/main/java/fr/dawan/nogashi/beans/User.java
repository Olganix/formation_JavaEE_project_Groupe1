package fr.dawan.nogashi.beans;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.UserRole;

@Entity
@Component
public class User extends DbObject
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
	private String address;
	private String addressExtra;
	private String postalCode;
	private String cityName;
	private String stateName;
	
	private boolean emailValid = false;
	private String token = null;												//for secure operations like email validation.
	
	private boolean newsletterEnabled = false;
	
	//-----------------------------
	
	public User() {
		super();
	}
	
	
	
	
	
	
	
	public User(String name, String email, String password, UserRole role, boolean newsletterEnabled)		//for the moment of inscription.
	{
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.newsletterEnabled = newsletterEnabled;
	}



	




	public User(String name, String email, String password, UserRole role, String avatarFilename, String phoneNumber,
			String adress, String adressExtra, String postalCode, String cityName, String stateName, boolean emailValid,
			String token, boolean newsletterEnabled) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.avatarFilename = avatarFilename;
		this.phoneNumber = phoneNumber;
		this.address = adress;
		this.addressExtra = adressExtra;
		this.postalCode = postalCode;
		this.cityName = cityName;
		this.stateName = stateName;
		this.emailValid = emailValid;
		this.token = token;
		this.newsletterEnabled = newsletterEnabled;
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

	public boolean isEmailValid() {
		return emailValid;
	}

	public void setEmailValid(boolean emailValid) {
		this.emailValid = emailValid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isNewsletterEnabled() {
		return newsletterEnabled;
	}

	public void setNewsletterEnabled(boolean newletterEnabled) {
		this.newsletterEnabled = newletterEnabled;
	}

	


	
	

	
}
