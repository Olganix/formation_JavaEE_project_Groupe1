package fr.dawan.nogashi.controlers;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    	
    	
    	//EntityGraph<User> graph = em.createEntityGraph(User.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
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
}
