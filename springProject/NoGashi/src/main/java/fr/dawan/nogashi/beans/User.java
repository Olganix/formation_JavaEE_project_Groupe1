package fr.dawan.nogashi.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.UserRole;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Component
public class User extends DbObject
{	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.ORDINAL)
	private UserRole role = UserRole.INDIVIDUAL;
	
	@Column(length = 500)
	private String avatarFilename = "NoAvatar.jpg";						//Todo do the upload system.
	
	private String phoneNumber;
	private String phoneNumber2;
	@OneToOne
	private Address address;	
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
	
	
	
	
	public User(String name, String email, String password, UserRole role, String phoneNumber, Address address, boolean emailValid, boolean newsletterEnabled) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.emailValid = emailValid;
		this.newsletterEnabled = newsletterEnabled;
	}
	public User(String name, String email, String password, UserRole role, String phoneNumber, Address address, boolean emailValid, boolean newsletterEnabled, String avatarFilename) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.emailValid = emailValid;
		this.newsletterEnabled = newsletterEnabled;
		this.avatarFilename = avatarFilename;
	}


	public User(String name, String password)		//for the moment of login. Notice: in case name is pseudo or email 
	{
		super();
		this.name = name;
		this.password = password;
	}

	public User(User other)
	{
		super();
		this.setId(other.getId());
		this.name = (other.name!= null) ? (new String(other.name)) : null;			//todo check if not Reference the same value. if it's the case , do = new String(other.name) and apply it everywhere there is a "other". 
		this.email = (other.email!= null) ? (new String(other.email)) : null;
		this.password = (other.password!= null) ? (new String(other.password)) : null;
		this.role = other.role;
		this.avatarFilename = (other.avatarFilename!= null) ? (new String(other.avatarFilename)) : null;
		this.phoneNumber = (other.phoneNumber!= null) ? (new String(other.phoneNumber)) : null;
		this.phoneNumber2 = (other.phoneNumber2!= null) ? (new String(other.phoneNumber2)) : null;
		this.address = (other.address!= null) ? (new Address(other.address)) : null;
		this.emailValid = other.emailValid;
		this.token = (other.token!= null) ? (new String(other.token)) : null;
		this.newsletterEnabled = other.newsletterEnabled;
	}


	





	public User(String name, String email, String password, UserRole role, String avatarFilename, String phoneNumber, String phoneNumber2,
			Address address, boolean emailValid,
			String token, boolean newsletterEnabled) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.avatarFilename = avatarFilename;
		this.phoneNumber = phoneNumber;
		this.phoneNumber2 = phoneNumber2;
		this.setAddress(address);		
		this.emailValid = emailValid;
		this.token = token;
		this.newsletterEnabled = newsletterEnabled;
	}







	@Override
	public String toString() {
		return "User [id=" + this.getId() + ",name=" + name + ", email=" + email + ", role=" + role + "]";
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

	
	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
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


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}

	


	
	

	
}
