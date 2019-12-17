package fr.dawan.nogashi.controlers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.hibernate.annotations.Parameter;
import org.jboss.logging.Param;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;
import fr.dawan.nogashi.tools.EmailTool;


//TODO: utiliser ce que l'on a fait le dernier jours du cours spring MVC , pour se debarasser de  persistence.xml (peut etre mis dans le root-context.xml (a spring bean config file)) 
//Todo rename controler => controller 


@RestController
@CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
public class UsersControlerAngular 
{
	private static final Logger logger = LoggerFactory.getLogger(UsersControlerAngular.class);
       
	@Autowired
	GenericDao dao;
	
	
	
	/*****************************************************************************************
	*										SignIn											 * 
	*****************************************************************************************/
	@PostMapping(path="/signin", produces = "application/json")
	//test : http://localhost:8080/nogashi/signin?name=aaa&password=toto&email=aaa@toto.fr&role=INDIVIDUAL&newsletterEnabled=1
	public RestResponse<User> signin(@RequestBody User u, HttpSession session, Locale locale, Model model)
    {
		System.out.println(u);
		
		
		if( (u.getRole() != UserRole.INDIVIDUAL) && (u.getRole() != UserRole.MERCHANT) && (u.getRole() != UserRole.ASSOCIATION))
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Role no good");
		
		
		
		logger.info("Welcome home! The client locale is {}.", locale);		//todo better use log for server side.
		//request.setCharacterEncoding("UTF-8");			//todo check if need equivalent for this, todo also check crypt password + utf8 saved in bdd are not good on read again (instead of manually create it and put it in bdd).
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		User u_tmp = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "name", u.getName(), em, false);
    		if(listUsers.size()==0)
    			listUsers = dao.findNamed(User.class, "email", u.getEmail(), em, false);
			
    		if(listUsers.size()!=0)
    			u_tmp = listUsers.get(0);
    		
		} catch (Exception e) {
			u_tmp = null;
			e.printStackTrace();
		}
		
    	if(u_tmp!=null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: User allready exist");
    	}
		
    	
    	String password_crypted = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());				// Crypting the password before save in bdd.
    	u.setPassword(password_crypted);
    	
    	
    	//create token for email validation.
    	Calendar calendar = Calendar.getInstance();
		String token = BCrypt.hashpw(u.getEmail() + calendar.get(Calendar.DAY_OF_YEAR), BCrypt.gensalt());
		System.out.println("hash: "+ u.getEmail() + calendar.get(Calendar.DAY_OF_YEAR) +" => "+ token);
		u.setToken(token);
    	//todo make a duration on validity token (ex: make a cron every 2 day 0h00, witch will set NULL on every token)
    	
		try 
		{
			dao.saveOrUpdate(u, em, false);
			
			System.out.println("create login: "+ u.getName() +" role:"+ u.getRole());
			
			//send email for token.
			{
				Properties prop = new Properties();
				prop.put("mail.smtp.host", "localhost");
				prop.put("mail.smtp.port", "25");
				
				EmailTool mt = new EmailTool(prop, null);
				try
				{
					//todo put email exp in properties
					
					//mt.sendMail_html(email, "noreply@nogashi.org", "Nogashi Email de Validation", "Merci de cliquer sur le lien pour valider votre adresse mail : <a href='http://localhost:8080/nogashi/emailvalidation?token="+ URLEncoder.encode(token, "UTF-8") +"'>Je valide mon adresse email</a>");
					URI uri = new URI("http", "localhost:4200", "/emailValidation", "token="+ token, null);
					mt.sendMail_html(u.getEmail(), "noreply@nogashi.org", "Nogashi Email de Validation", "Merci de cliquer sur le lien pour valider votre adresse mail : <a href='"+ uri.toASCIIString() +"'>Je valide mon adresse email</a>");
					
				}catch(MessagingException e){
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			u = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<User>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	
	
	/*****************************************************************************************
	*										EmailValidation									 * 
	*****************************************************************************************/
	@PostMapping(path="/emailvalidation", produces = "application/json")
	//test (better click from mail (fake SMTP server)): http://localhost:8080/nogashi/emailvalidation?token=XXXXXX
    public RestResponse<User> emailvalidation(@RequestBody String token, HttpSession session, Locale locale, Model model)
    {
		if( (token==null) || (token.length()==0) )
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not have token");
		
		EntityManager em = StartListener.createEntityManager();
		
		
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "token", token, em, false);
    		if(listUsers.size()!=0)
    			u = listUsers.get(0);
		} catch (Exception e) {
			u = null;
			e.printStackTrace();
		}
		
    	if(u==null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: token not valid or not found");
    	}
		
    	u.setEmailValid(true);
    	u.setToken(null);
    	
		try 
		{
			dao.saveOrUpdate(u, em, false);
			System.out.println("email validated for login: "+ u.getName() +" role:"+ u.getRole());
			
		} catch (Exception e1) {
			u = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<User>(RestResponseStatus.SUCCESS, null);
    }
	
	
	/*****************************************************************************************
	*										sendEmailValidation								 * 
	*****************************************************************************************/
	@RequestMapping(path="/sendemailvalidation", produces = "application/json")
	//test : http://localhost:8080/nogashi/sendemailvalidation?email=aaa@toto.fr
    public RestResponse<User> sendEmailValidation(@PathParam("email") String email, HttpSession session, Locale locale, Model model)
    {
		//todo better check on email (rules of email) and name (not admin, root or god or ...etc see official list for that), could be done from angular, but it's may be safer to do it here
		if( (email==null)  || (email.length()==0) )
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not enought arguments");
		
		EntityManager em = StartListener.createEntityManager();
		
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "email", email, em, false);
    		if(listUsers.size()!=0)
    			u = listUsers.get(0);
    		
		} catch (Exception e) {
			u = null;
			e.printStackTrace();
		}
		
    	if(u==null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: unknow Email");
    	}
		
    	//create token for email validation.
    	Calendar calendar = Calendar.getInstance();
		String token = BCrypt.hashpw(email + calendar.get(Calendar.DAY_OF_YEAR), BCrypt.gensalt());
		System.out.println("hash: "+ email + calendar.get(Calendar.DAY_OF_YEAR) +" => "+ token);
		u.setToken(token);
    	
    	
		try 
		{
			dao.saveOrUpdate(u, em, false);
			
			System.out.println("try send emailValidation login: "+ u.getName() +" role:"+ u.getRole());
			
			//send email for token.
			{
				Properties prop = new Properties();
				prop.put("mail.smtp.host", "localhost");
				prop.put("mail.smtp.port", "25");
				
				EmailTool mt = new EmailTool(prop, null);
				try
				{
					//todo put email exp in properties
					
					//mt.sendMail_html(email, "noreply@nogashi.org", "Nogashi Email de Validation", "Merci de cliquer sur le lien pour valider votre adresse mail : <a href='http://localhost:8080/nogashi/emailvalidation?token="+ URLEncoder.encode(token, "UTF-8") +"'>Je valide mon adresse email</a>");
					URI uri = new URI("http", "localhost:4200", "/emailValidation", "token="+ token, null);
					mt.sendMail_html(email, "noreply@nogashi.org", "Nogashi Email de Validation", "Merci de cliquer sur le lien pour valider votre adresse mail : <a href='"+ uri.toASCIIString() +"'>Je valide mon adresse email</a>");
					
				}catch(MessagingException e){
					u = null;
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			u = null;
			e1.printStackTrace();
		}
		
		
		em.close();
		
		if(u != null)
			return new RestResponse<User>(RestResponseStatus.SUCCESS, u);				//todo avoid some information to be send to front
		else
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: on save or send email validation");
    }
	
	
	
	
	/*****************************************************************************************
	*										Login											 * 
	*****************************************************************************************/
	@RequestMapping(path="/login", produces = "application/json")
	//test : http://localhost:8080/nogashi/login?name=aaa&password=toto
	//test : http://localhost:8080/nogashi/login?name=aaa@toto.fr&password=toto
    public RestResponse<User> login(@PathParam("name") String name, @PathParam("password") String password, HttpSession session, Locale locale, Model model)
    {
		if( (name==null) || (name.length()==0) ||(password==null) || (password.length()==0) )
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not enought arguments");
		
		//request.setCharacterEncoding("UTF-8");			//todo check if need equivalent for this, todo also check crypt password + utf8 saved in bdd are not good on read again (instead of manually create it and put it in bdd).
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "name", name, em, false);
    		if(listUsers.size()==0)
    			listUsers = dao.findNamed(User.class, "email", name, em, false);
    		
    		if(listUsers.size()!=0)
    			u = listUsers.get(0);
    		
		} catch (Exception e) {
			u = null;
			e.printStackTrace();
		}
		
    	if(u==null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: User not Found or wrong Password");
    	}
		
    	System.out.println(password +" match with "+ u.getPassword() +" ? => "+ (BCrypt.checkpw(password, u.getPassword()) ? "true" : "false") );
    	if( ! BCrypt.checkpw(password, u.getPassword()) )				// compare with the Crypted password saved in bdd.
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: User not Found or wrong Password");
    	}
    	
    	if(!u.isEmailValid())
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, u, 2, "Error: Email no validated");
    	}
    		
    	
    	
    	session.setAttribute("user", u);
    	session.setMaxInactiveInterval(60*60*24);
    	
    	System.out.println("login: "+ u.getName() +" role:"+ u.getRole());
		
		em.close();
		
		return new RestResponse<User>(RestResponseStatus.SUCCESS, u);				//todo avoid some information to be send to front
    }
	
	
	
	
	
	/*****************************************************************************************
	*										isLoged											 * 
	*****************************************************************************************/
	@RequestMapping(path="/isloged", produces = "application/json")
	//test : http://localhost:8080/nogashi/isloged
    public RestResponse<User> isloged(HttpSession session, Locale locale, Model model)
    {
		User u = (User)session.getAttribute("user");
    	
		if(u!=null)
		{
			System.out.println("isloged: "+ u.getName() +" role:"+ u.getRole());
			
			return new RestResponse<User>(RestResponseStatus.SUCCESS, u);				//todo avoid some information to be send to front
		}else {
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Not Connected");
		}
    }
	
	
	
	/*****************************************************************************************
	*										Logout											 * 
	*****************************************************************************************/
	@RequestMapping(path="/logout", produces = "application/json")
	//test : http://localhost:8080/nogashi/logout
    public RestResponse<User> logout(HttpSession session, Locale locale, Model model)
    {
		User u = (User)session.getAttribute("user");
    	if(u==null)
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Not Connected");
    	
    	System.out.println("logout: "+ u.getName() +" role:"+ u.getRole());
    	
    	session.setAttribute("user", null);
    	//session.invalidate();
    	
		return new RestResponse<User>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	
	
	
	
	
	/*****************************************************************************************
	*										getUsers										 * 
	*****************************************************************************************/
	@RequestMapping(path="/getUsers", produces = "application/json")
	//it's a test, TODO remove this from public access, could be use only for admin.
    public RestResponse<List<User>> getUsers()
    {
    	EntityManager em = StartListener.createEntityManager();
		
    	List<User> listUsers = new ArrayList<User>();
		
		try 
		{	
			listUsers = dao.findAll(User.class, em, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<User>>(RestResponseStatus.SUCCESS, listUsers);
    }
    
}
