package fr.dawan.nogashi.controlers;

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

import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.Individual;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.listeners.StartListener;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
public class IndividualController
{
	@Autowired
	GenericDao dao;

	
	/*****************************************************************************************
	*										getIndividualAccount									 * 
	*****************************************************************************************
	* 
	* Recupere le User (Individual) de la session via son id 
	*/
	@GetMapping(path="/individual-account", produces = "application/json")
	public RestResponse<User> getIndividualAccount(HttpSession session)
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
    	
    	
    	// Recupere l'Individual a partir du User de la session et check si c'est bien cet Individual qui est connecte
		try {
			user = dao.find(Individual.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user==null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 5, "Error: wrong Individual session information");
    	}
		
		
		em.close();
		return new RestResponse<User>(RestResponseStatus.SUCCESS, user);
    }
	
	
	/*****************************************************************************************
	*										updateIndividualAccount										 * 
	*****************************************************************************************
	*
	* Modifie les infos de l'Individual connecte
	*/
	@PostMapping(path="/individual-account/update", consumes = "application/json", produces = "application/json")
	public RestResponse<Individual> updateIndividualAccount(@RequestBody Individual i, HttpSession session, Locale locale, Model model)
    {
		// Check s'il y a un User dans la session
		User u = (User)session.getAttribute("user");
    	if(u==null)
    		return new RestResponse<Individual>(RestResponseStatus.FAIL, null, 1, "Not Connected");
		    	
		// Check si les champs obligatoires du formulaire sont null
		if(	(i==null) || 
			(i.getName()==null) || ( i.getName().trim().length() ==0) ||
			(i.getEmail()==null) || ( i.getEmail().trim().length() ==0) ||
			(i.getPassword()==null) || ( i.getPassword().trim().length() ==0) ||
			(i.getAddress()==null)
			)
			
		{
			return new RestResponse<Individual>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		Individual individual = new Individual();
		
		EntityManager em = StartListener.createEntityManager();
		
		// Recupere l'Individual a partir du User de la session et check si c'est bien cet Individual qui est connecte
		try {
			individual = dao.find(Individual.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(individual==null)
    	{
    		em.close();
    		return new RestResponse<Individual>(RestResponseStatus.FAIL, null, 5, "Error: wrong Individual session information");
    	}
		
		// Check si le name ou l'email du User a modifier existe deja dans la BDD
		// TODO Gerer le cas ou le champ n'a pas ete modifie (expl : l'email existe deja dans la BDD pusiqu'il s'agit de celui du User en cours)
		User u_tmp = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "name", i.getName(), em, false);
    		if(listUsers.size()==0)
    			listUsers = dao.findNamed(User.class, "email", i.getEmail(), em, false);
			
    		if(listUsers.size()!=0)
    			u_tmp = listUsers.get(0);
    		
		} catch (Exception e) {
			u_tmp = null;
			e.printStackTrace();
		}
		
    	if(u_tmp!=null)
    	{
    		em.close();
    		return new RestResponse<Individual>(RestResponseStatus.FAIL, null, 1, "Error: a User with this email or name already exists");
    	}
			
    	// Persiste l'Individual a modifier dans la BDD
		try 
		{
			dao.saveOrUpdate(i, em, false);
			System.out.println("update user (merchant): "+ i.getName() +" email:"+ i.getEmail());
			
		} catch (Exception e1) {
			i = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<Individual>(RestResponseStatus.SUCCESS, i);
    }
}
