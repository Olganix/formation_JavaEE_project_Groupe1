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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.Association;
import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.Individual;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true") 
@RequestMapping(path = "/association")
// @CrossOrigin is used to handle the request from a difference origin.
public class AssociationController
{
	@Autowired
	GenericDao dao;

	
	// Retourne Individual si le User de la session est un Individual et qu'il est bien dans la Bdd
	public Association checkAllowToDoThat(HttpSession session, EntityManager em)
	{
		User u = (User)session.getAttribute("user");
		System.out.println("AssociationController.checkAllowToDoThat : "+ u);
		
		
		Association association = null;
		if( (u!=null) && ( (u.getRole() == UserRole.ASSOCIATION)) )
		{
			// Recupere l'Association a partir du User de la session
	    	// et aussi check si c'est bien cet Individual qui est connecte
			try {
				association = dao.find(Association.class, ((User)session.getAttribute("user")).getId(), em);
			} catch (Exception e) {
				association = null;
				e.printStackTrace();
			}
		}
		return association;
	}
		
	//TODO
	// ------------------------- /association
	// /reserve												// equivalent de payement, sans creditcart, puis avec 0 euros de depensé.

	
	/*****************************************************************************************
	*										getAssociation									* 
	*****************************************************************************************
	* 
	* Recupere le User (Association) de la session via son id 
	*/
	@GetMapping(path="", produces = "application/json")
	public RestResponse<Association> getAssociation(HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Individual
		Association association = checkAllowToDoThat(session, em);
		if(association==null)
		{
			em.close();
			return new RestResponse<Association>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		EntityGraph<Buyer> graph = em.createEntityGraph(Buyer.class);
    	
    	graph.addSubgraph("historicShoppingCarts");
    	graph.addSubgraph("dietaryRestrictions");
		
		
		em.close();
		return new RestResponse<Association>(RestResponseStatus.SUCCESS, association);
    }
	
	
	
	/*****************************************************************************************
	*										updateAssociation								 * 
	******************************************************************************************
	*
	* Modifie les infos de profil de l'Association connectee
	*/
	@PostMapping(path="/update", consumes = "application/json", produces = "application/json")
	public RestResponse<Association> updateAssociation(@RequestBody Association a, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Association
		Association association = checkAllowToDoThat(session, em);
		if(association==null)
		{
			em.close();
			return new RestResponse<Association>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
	
		
		// Verifie les champs modifies du formulaire
		boolean isModifed = false;	
		if((association.getCodeSiren()==null) && (a.getCodeSiren()!=null) )					// specificité : le code siren ne se modifie qu'une seule fois.
		{
			association.setCodeSiren(a.getCodeSiren());
			isModifed = true;
		}
		
		
		if( (!association.getCodeAssociation().equals(a.getCodeAssociation())) )									// TODO regex
		{
			association.setCodeAssociation(a.getCodeAssociation());
			isModifed = true;
		}
		
		if( (a.getEmail() != null) && (!association.getEmail().equals(a.getEmail())) )									// TODO regex
		{
			association.setEmail(a.getEmail());
			isModifed = true;
		}
		
		if( (!association.getAvatarFilename().equals(a.getAvatarFilename())) )	
		{
			association.setAvatarFilename(a.getAvatarFilename());
			isModifed = true;
		}
		
		if( (!association.getPhoneNumber().equals(a.getPhoneNumber())) )												// TODO regex
		{
			association.setPhoneNumber(a.getPhoneNumber());
			isModifed = true;
		}
		if( (!association.getPhoneNumber2().equals(a.getPhoneNumber2())) )													// TODO regex
		{
			association.setPhoneNumber2(a.getPhoneNumber2());
			isModifed = true;
		}
		
		if( (a.getAddress() != null) && (!association.getAddress().equals(a.getAddress())) )	
		{
			association.setAddress(a.getAddress());
			isModifed = true;
		}
				
		if( ( association.isNewsletterEnabled() != a.isNewsletterEnabled()) )	
		{
			association.setNewsletterEnabled(a.isNewsletterEnabled());
			isModifed = true;
		}
		

		if(isModifed)
		{
			try 
			{
				dao.saveOrUpdate(association, em, false);
				System.out.println("update user (association): "+ association.getName() +" email:"+ association.getEmail());
				
			} catch (Exception e1) {
				e1.printStackTrace();
				
				em.close();
				return new RestResponse<Association>(RestResponseStatus.FAIL, null, 1, "Error: on Update Operation");
			}
		}
		
		em.close();
		
		return new RestResponse<Association>(RestResponseStatus.SUCCESS, a);
    }
	
	
	/*****************************************************************************************
	*								deactivateAssociation										* 
	*****************************************************************************************
	*
	* Desactive le compte Association (User connecte)
	* TODO conserver les ShoppingCart
	*/
	@GetMapping(path="/remove", produces = "application/json")
	public RestResponse<Association> deactivateAssociation(HttpSession session, Locale locale, Model model)
    {	
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
		
    	
    	// Supprime le User
		// TODO: Desactiver le compte au lieu de le supprimer
		try 
		{
			System.out.println(association.getName() + " account removed");
			dao.remove(association, em, false);
			
			// Met le User de la session a null
	    	session.setAttribute("user", null);
			
		} catch (Exception e1) {
			association = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<Association>(RestResponseStatus.SUCCESS, null);
    }

}
