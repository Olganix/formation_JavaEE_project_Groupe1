package fr.dawan.nogashi.controllers;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.Association;
import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.listeners.StartListener;

/**
 * 
 * Listes des methodes :
 * 
 * getAssociationAccount
 * updateAssociationAccount
 * TODO deactivateAssociationAccount
 *
 */
@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
public class AssociationController
{
	@Autowired
	GenericDao dao;
	
	
	
	/*****************************************************************************************
	*										getAssociationAccount									 * 
	*****************************************************************************************
	* 
	* Recupere le User (Association) de la session via son id 
	*/
	@GetMapping(path="/association-account", produces = "application/json")
	public RestResponse<User> getAssociationAccount(HttpSession session)
    {
		// Check s'il y a un User dans la session
		User u = (User)session.getAttribute("user");
    	if(u==null)
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Not Connected");
		
		EntityManager em = StartListener.createEntityManager();
		
		User user = new User();
    	
    	
		EntityGraph<Buyer> graph = em.createEntityGraph(Buyer.class);
    	
    	graph.addSubgraph("historicShoppingCarts");
    	graph.addSubgraph("dietaryRestrictions");
    	
    	// Recupere l'Association a partir du User de la session et check si c'est bien cette Association qui est connectee
		try {
			user = dao.find(Association.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user==null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 5, "Error: wrong Association session information");
    	}
		
		
		em.close();
		return new RestResponse<User>(RestResponseStatus.SUCCESS, user);
    }
	
	
	
	/*****************************************************************************************
	*										updateAssociationAccount										 * 
	*****************************************************************************************
	*
	* Modifie les infos de l'Association connectee
	*/
	@PostMapping(path="/association-account/update", consumes = "application/json", produces = "application/json")
	public RestResponse<Association> updateAssociationAccount(@RequestBody Association a, HttpSession session, Locale locale, Model model)
    {
		// Check s'il y a un User dans la session
		User u = (User)session.getAttribute("user");
    	if(u==null)
    		return new RestResponse<Association>(RestResponseStatus.FAIL, null, 1, "Not Connected");
    	
		// Check si les champs obligatoires du formulaire sont null
		if(	(a==null) || 
			(a.getName()==null) || ( a.getName().trim().length() ==0) ||
			(a.getEmail()==null) || ( a.getEmail().trim().length() ==0) ||
			(a.getPassword()==null) || ( a.getPassword().trim().length() ==0) ||
			(a.getCodeSiren()==null) || ( a.getCodeSiren().trim().length() ==0) ||
			(a.getAddress()==null)
			)
			
		{
			return new RestResponse<Association>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		Association association = new Association();
		
		EntityManager em = StartListener.createEntityManager();
		
		// Recupere l'Association a partir du User de la session et check si c'est bien cette Association qui est connectee
		try {
			association = dao.find(Association.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(association==null)
    	{
    		em.close();
    		return new RestResponse<Association>(RestResponseStatus.FAIL, null, 5, "Error: wrong Association session information");
    	}
		
		// Check si le name ou l'email du User a modifier existe deja dans la BDD
		// TODO Gerer le cas ou le champ n'a pas ete modifie (expl : l'email existe deja mais il s'agit de celui du User en cours)
		User u_tmp = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "name", a.getName(), em, false);
    		if(listUsers.size()==0)
    			listUsers = dao.findNamed(User.class, "email", a.getEmail(), em, false);
			
    		if(listUsers.size()!=0)
    			u_tmp = listUsers.get(0);
    		
		} catch (Exception e) {
			u_tmp = null;
			e.printStackTrace();
		}
		
    	if(u_tmp!=null)
    	{
    		em.close();
    		return new RestResponse<Association>(RestResponseStatus.FAIL, null, 1, "Error: a User with this email or name already exists");
    	}
			
    	// Persiste l'Association a modifier dans la BDD
		try 
		{
			dao.saveOrUpdate(a, em, false);
			System.out.println("update user (merchant): "+ a.getName() +" email:"+ a.getEmail());
			
		} catch (Exception e1) {
			a = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<Association>(RestResponseStatus.SUCCESS, a);
    }

}
