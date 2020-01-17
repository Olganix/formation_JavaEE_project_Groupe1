package fr.dawan.nogashi.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.Association;
import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.Commerce;
import fr.dawan.nogashi.beans.Individual;
import fr.dawan.nogashi.beans.Merchant;
import fr.dawan.nogashi.beans.ProductTemplate;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.SchedulerWeek;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;
import fr.dawan.nogashi.tools.EmailTool;


//TODO: utiliser ce que l'on a fait le dernier jours du cours spring MVC , pour se debarasser de  persistence.xml (peut etre mis dans le root-context.xml (a spring bean config file)) 

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
@RequestMapping("/user")
public class UsersController 
{
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
       
	@Autowired
	GenericDao dao;
	
	
	
	/*****************************************************************************************
	*										signin											 * 
	*****************************************************************************************
	*
	* Enregistre un nouvel User
	*/
	@PostMapping(path="/signin", produces = "application/json")
	public RestResponse<User> signin(@RequestBody User u, HttpSession session, Locale locale, Model model)
    {
		System.out.println(u);
		
		// Check si tous les champs du formulaire sont null ou si le name, l'email ou le password est null
		if(	(u==null) || 
			(u.getName()==null) || ( u.getName().trim().length() ==0) ||
			(u.getEmail()==null) || ( u.getEmail().trim().length() ==0) ||
			(u.getPassword()==null) || ( u.getPassword().trim().length() ==0) )
		{
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		// Check, si l'un des 3 roles a bien ete selectionne
		if( (u.getRole() != UserRole.INDIVIDUAL) && (u.getRole() != UserRole.MERCHANT) && (u.getRole() != UserRole.ASSOCIATION))
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Role no good");
		
		
		
		logger.info("Welcome home! The client locale is {}.", locale);		//todo better use log for server side.
		//request.setCharacterEncoding("UTF-8");			//todo check if need equivalent for this, todo also check crypt password + utf8 saved in bdd are not good on read again (instead of manually create it and put it in bdd).
		
		
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le name ou l'email du User a ajouter existe deja dans la BDD
		User u_tmp = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "name", u.getName(), em);
    		if(listUsers.size()==0)
    			listUsers = dao.findNamed(User.class, "email", u.getEmail(), em);
			
    		if(listUsers.size()!=0)
    			u_tmp = listUsers.get(0);
    		
		} catch (Exception e) {
			u_tmp = null;
			e.printStackTrace();
		}
		
    	if(u_tmp!=null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: User already exists");
    	}
		
    	// Crypte le password
    	String password_crypted = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());				// Crypting the password before save in bdd.
    	u.setPassword(password_crypted);
    	
    	
    	// Create token for email validation.
    	Calendar calendar = Calendar.getInstance();
		String token = BCrypt.hashpw(u.getEmail() + calendar.get(Calendar.DAY_OF_YEAR), BCrypt.gensalt());
		System.out.println("hash: "+ u.getEmail() + calendar.get(Calendar.DAY_OF_YEAR) +" => "+ token);
		u.setToken(token);
    	//todo make a duration on validity token (ex: make a cron every 2 day 0h00, witch will set NULL on every token)
    	
		try 
		{
			switch(u.getRole())
			{
			case MERCHANT: 		dao.saveOrUpdate(new Merchant(u), em); break;
			case ASSOCIATION: 	dao.saveOrUpdate(new Association(u), em); break;
			case INDIVIDUAL: 	dao.saveOrUpdate(new Individual(u), em); break;
			case ADMIN: 
			default: 			dao.saveOrUpdate(u, em); break;
			}
			
			
			System.out.println("create login: "+ u.getName() +" role:"+ u.getRole());
			
			// Send email for token
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
			
			//Todo check des erreur sur exception.
		}
		
		em.close();
		
		return new RestResponse<User>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	
	
	/*****************************************************************************************
	*										emailValidation									 * 
	*****************************************************************************************
	*
	* Check le token de l'User pour valider l'email
	*/
	@PostMapping(path="/emailValidation", produces = "application/json")
	//test (better click from mail (fake SMTP server)): http://localhost:8080/nogashi/emailvalidation?token=XXXXXX
    public RestResponse<User> emailValidation(@RequestBody String token, HttpSession session, Locale locale, Model model)
    {
		if( (token==null) || (token.length()==0) )
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not have token");
		
		EntityManager em = StartListener.createEntityManager();
		
		
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "token", token, em);
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
			dao.saveOrUpdate(u, em);
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
	*****************************************************************************************
	*
	* Envoie le mail de validation d'email
	*/
	@PostMapping(path="/sendEmailValidation", produces = "application/json")
	//test : http://localhost:8080/nogashi/sendemailvalidation?email=aaa@toto.fr
    public RestResponse<User> sendEmailValidation(@RequestBody String email, HttpSession session, Locale locale, Model model)
    {
		//todo better check on email (rules of email) and name (not admin, root or god or ...etc see official list for that), could be done from angular, but it's may be safer to do it here
		if( (email==null)  || (email.length()==0) )
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not enought arguments");
		
		EntityManager em = StartListener.createEntityManager();
		
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "email", email, em);
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
			dao.saveOrUpdate(u, em);
			
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
			return new RestResponse<User>(RestResponseStatus.SUCCESS, null);
		else
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: on save or send email validation");
    }
	
	
	
	
	/*****************************************************************************************
	*										login											 * 
	*****************************************************************************************
	*
	* Enregistre le User dans la session
	*/
	@PostMapping(path="/login", produces = "application/json")
	public RestResponse<User> login(@RequestBody User user, HttpSession session, Locale locale, Model model)
    {
		System.out.println("login : "+ user);
		
		// Check si tous les champs du formulaire sont null ou si le name ou le password est null
		if(	(user==null) || 
				(user.getName()==null) || ( user.getName().trim().length() ==0) ||
				(user.getPassword()==null) || ( user.getPassword().trim().length() ==0) )
		{
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		
		
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le name ou l'email du User a ajouter existe bien dans la BDD
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "name", user.getName(), em);
    		if(listUsers.size()==0)
    			listUsers = dao.findNamed(User.class, "email", user.getName(), em);
    		
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
		
    	System.out.println(user.getPassword() +" match with "+ u.getPassword() +" ? => "+ (BCrypt.checkpw(user.getPassword(), u.getPassword()) ? "true" : "false") );
    	if( ! BCrypt.checkpw(user.getPassword(), u.getPassword()) )				// compare with the Crypted password saved in bdd.
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: User not Found or wrong Password");
    	}
    	
    	// Check si l'email a ete valide
    	if(!u.isEmailValid())
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, u, 2, "Error: Email no validated");
    	}
    		
    	u = new User(u);						//to avoid jackson considere Merchant as User and faild to make Json because of lists in Merchant ... WTH.
    	
    	// Enregistre le User en session
    	session.setAttribute("user", u);
    	session.setMaxInactiveInterval(60*60*24);
    	
    	System.out.println("login: "+ u.getName() +" role:"+ u.getRole());
		
		em.close();
		
		return new RestResponse<User>(RestResponseStatus.SUCCESS, u);				//todo avoid to give All user informations , because front don't need it, and it's a security issue.
    }
	
	
	
	
	
	/*****************************************************************************************
	*										isLogged										 * 
	*****************************************************************************************
	*
	* Check si un User est enregistre en session
	*/
	@GetMapping(path="/isLogged", produces = "application/json")
	//test : http://localhost:8080/nogashi/islogged
    public RestResponse<User> isLogged(HttpSession session, Locale locale, Model model)
    {
		User u = (User)session.getAttribute("user");
    	
		if(u!=null)
		{
			System.out.println("islogged: "+ u.getName() +" role:"+ u.getRole());
			
			return new RestResponse<User>(RestResponseStatus.SUCCESS, u);				//todo avoid some information to be send to front
		}else {
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Not Connected");
		}
    }
	
	
	
	/*****************************************************************************************
	*										logout											 * 
	*****************************************************************************************
	*
	* Supprime le User de la session
	*/
	@GetMapping(path="/logout", produces = "application/json")
	//test : http://localhost:8080/nogashi/logout
    public RestResponse<User> logout(HttpSession session, Locale locale, Model model)
    {
		// Check s'il y a un User dans la session
		User u = (User)session.getAttribute("user");
    	if(u==null)
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Not Connected");
    	
    	System.out.println("logout: "+ u.getName() +" role:"+ u.getRole());
    	
    	// Met le User de la session a null
    	session.setAttribute("user", null);
    	//session.invalidate();
    	
		return new RestResponse<User>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	

	/*****************************************************************************************
	*										passwordRescue									 * 
	*****************************************************************************************
	*
	* 
	*/
	@RequestMapping(path="/passwordRescue", produces = "application/json")
	//test : http://localhost:8080/nogashi/passwordRescue?email=aaa@toto.fr
    public RestResponse<User> passwordRescue(@RequestBody String email, HttpSession session, Locale locale, Model model)
    {
		if(	(email==null) || ( email.trim().length() ==0) )
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "email", email, em);
    		if(listUsers.size()!=0)
    			u = listUsers.get(0);
    		
		} catch (Exception e) {
			u = null;
			e.printStackTrace();
		}
		
    	if(u==null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: User not Found");
    	}  	
    	

    	//create token for email validation.
    	Calendar calendar = Calendar.getInstance();
		String token = BCrypt.hashpw(email + calendar.get(Calendar.DAY_OF_YEAR), BCrypt.gensalt());
		System.out.println("hash: "+ email + calendar.get(Calendar.DAY_OF_YEAR) +" => "+ token);
		u.setToken(token);
    	
    	
		try 
		{
			dao.saveOrUpdate(u, em);
			
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
					//Todo make a function for sending mail + try catch
					URI uri = new URI("http", "localhost:4200", "/passwordRescueModification", "token="+ token, null);
					mt.sendMail_html(email, "noreply@nogashi.org", "Nogashi Email modification de mot de passe", "Merci de cliquer sur le lien pour modifier votre mot de passe: <a href='"+ uri.toASCIIString() +"'>Aller vers la page de modification de mot de passe</a>");
					
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
			return new RestResponse<User>(RestResponseStatus.SUCCESS, null);
		else
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: on save or send email validation");
    }
	
	
	
	
	
	

	/*****************************************************************************************
	*										passwordRescueModification						 * 
	*****************************************************************************************
	*
	* 
	*/
	@PostMapping(path="/passwordRescueModification", produces = "application/json")
	//test : http://localhost:8080/nogashi/passwordRescueModification?password=toto&token=xxxxxxxxxxxxx
	public RestResponse<User> passwordRescueModification(@RequestBody User user, HttpSession session, Locale locale, Model model)
    {
		System.out.println(user);
		
		if(	(user==null) || 
			(user.getToken()==null) || ( user.getToken().trim().length() ==0) ||
			(user.getPassword()==null) || ( user.getPassword().trim().length() ==0) )
		{
			return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		// Recupere le User dont le token est entre en parametre
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "token", user.getToken(), em);
    		if(listUsers.size()!=0)
    			u = listUsers.get(0);
		} catch (Exception e) {
			u = null;
			e.printStackTrace();
		}
		
    	// Check si le token pour modifier le password est valide
    	if(u==null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Error: token not valid or not found");
    	}
		
    	String password_crypted = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());				// Crypting the password before save in bdd.
    	u.setPassword(password_crypted);
    	u.setToken(null);
    	
    	// Modifie le password du User qui correspond au token
		try 
		{
			dao.saveOrUpdate(u, em);
			System.out.println("password modified for login: "+ u.getName() +" role:"+ u.getRole());
			
		} catch (Exception e1) {
			u = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<User>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	********************************************************** Admin/tests functions *************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	*********************************************************************************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*****************************************************************************************
	*										getUserById									 * 
	*****************************************************************************************
	* 
	* Recupere le User de la session via son id
	* TODO: supprimer cette méthode
	*/
	@GetMapping(path="/users/{id}", produces = "application/json")
	public RestResponse<User> getUserById(@PathVariable(name="id") int id, HttpSession session)
    {
		// Check s'il y a un User dans la session
		User u = (User)session.getAttribute("user");
    	if(u==null)
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Not Connected");
    	
		
		EntityManager em = StartListener.createEntityManager();
		
		User user = new User();
    	
    	
    	EntityGraph<User> graph = em.createEntityGraph(User.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
    	// Recupere le User dont l'id est passe en parametre
		try 
		{	
			user = dao.find(User.class, id, em, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<User>(RestResponseStatus.SUCCESS, user);
    }
	
	
	
	
	
	/*****************************************************************************************
	*										getUsers										 * 
	*****************************************************************************************
	*
	* Liste tous les Users (todo supprimer)
	*/
	@GetMapping(path="/users", produces = "application/json")
	//it's a test, TODO remove this from public access, could be use only for admin.
    public RestResponse<List<User>> getUsers()
    {
    	EntityManager em = StartListener.createEntityManager();
		
    	List<User> listUsers = new ArrayList<User>();
		
		try 
		{	
			listUsers = dao.findAll(User.class, em, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<User>>(RestResponseStatus.SUCCESS, listUsers);
    }
	
	/*****************************************************************************************
	*										getMerchants									 * 
	*****************************************************************************************
	*
	* Liste tous les Merchants (todo supprimer)
	*/
	@GetMapping(path="/merchants", produces = "application/json")
	//it's a test, TODO remove this from public access, could be use only for admin.
    public RestResponse<List<Merchant>> getMerchants()
    {
    	EntityManager em = StartListener.createEntityManager();
		
    	List<Merchant> listMerchants = new ArrayList<Merchant>();
		
    	
    	
    	EntityGraph<Merchant> graph = em.createEntityGraph(Merchant.class);
    	graph.addSubgraph("commerces");
    	
    	//Subgraph<Merchant> itemGraph = graph.addSubgraph("commerces");    
    	//Map hints = new HashMap();
    	//hints.put("javax.persistence.loadgraph", graph);
    	
		try 
		{	
			//listMerchants = dao.findAll(Merchant.class, em, true, graph, false);
			listMerchants = dao.findAll(Merchant.class, em, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<Merchant>>(RestResponseStatus.SUCCESS, listMerchants);
    }
	
	
	
	

	
	// Retourne User si le User de la session est un User
	public User checkAllowToDoThat(HttpSession session, EntityManager em)
	{
		User u = (User)session.getAttribute("user");
		System.out.println("UserController.checkAllowToDoThat : "+ u);
		
		return u;
	}
	
	
	/*****************************************************************************************
	*										getCommerceById									 * 
	*****************************************************************************************
	* 
	* Recupere un Commerce via son id
	*/
	@GetMapping(path="/commerce/{id}", produces = "application/json")
	public RestResponse<Commerce> getCommerceById(@PathVariable(name="id") int id, HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est User
		User user = checkAllowToDoThat(session, em);
		if(user==null)
		{
			em.close();
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
    	// Recupere le Commerce dont l'id est passe en parametre
		Commerce commerce = null;
		try 
		{	
			commerce = dao.find(Commerce.class, id, em);
			
			SchedulerWeek sw = commerce.getSchedulerWeek();
			for(SchedulerWeek swTmp : sw.getGroup())				//Todo faire mieux pour charger les group, et eviter la merde au niveau de Jsckon
				System.out.println(swTmp);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: on getting Commerce");
		}
		
		em.close();
		return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, commerce);
    }
	
	
	
	
	/*****************************************************************************************
	*										getProductTemplateById						 * 
	*****************************************************************************************
	* 
	* Recupere un ProductTemplate via son id
	*/
	@GetMapping(path="/productTemplate/{id}", produces = "application/json")
	public RestResponse<ProductTemplate> getProductTemplateById(@PathVariable(name="id") int id, HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est User
		User user = checkAllowToDoThat(session, em);
		if(user==null)
		{
			em.close();
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		
    	// Recupere le ProductTemplate dont l'id est passe en parametre
		ProductTemplate productTemplate = null;
		try 
		{
			// Todo voir si l'on a pas besoin de graph pour les productDetails
			productTemplate = dao.find(ProductTemplate.class, id, em);
		} catch (Exception e) {
			e.printStackTrace();
		
			em.close();
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: getting ProductTemplate operation");
		}
		
		em.close();
		return new RestResponse<ProductTemplate>(RestResponseStatus.SUCCESS, productTemplate);
    }
	
	
	
    
}
