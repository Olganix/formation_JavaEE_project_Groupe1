package fr.dawan.nogashi.controllers;

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

import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.CreditCard;
import fr.dawan.nogashi.beans.Individual;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true") 
// @CrossOrigin is used to handle the request from a difference origin.
@RequestMapping(path = "/individual")
public class IndividualController
{
	@Autowired
	GenericDao dao;

	
	// TODO
	// ------------------------- /individual
	// /payement											// on recoit les info de la carte de credit + shoppingcart. + envois de mail et ou de notification vers l'individual, vers le merchant/commerce.

	
	// Retourne Individual si le User de la session est un Individual et qu'il est bien dans la Bdd
	public Individual checkAllowToDoThat(HttpSession session, EntityManager em)
	{
		User u = (User)session.getAttribute("user");
		System.out.println("IndividualController.checkAllowToDoThat : "+ u);
		
		
		Individual individual = null;
		if( (u!=null) && ( (u.getRole() == UserRole.INDIVIDUAL)) )
		{
			// Recupere l'Individual a partir du User de la session
	    	// et aussi check si c'est bien cet Individual qui est connecte
			try {
				individual = dao.find(Individual.class, ((User)session.getAttribute("user")).getId(), em);
			} catch (Exception e) {
				individual = null;
				e.printStackTrace();
			}
		}
		return individual;
	}
	
	/*****************************************************************************************
	*										getIndividual									 * 
	*****************************************************************************************
	* 
	* Recupere le User (Individual) de la session via son id 
	*/
	@GetMapping(path="", produces = "application/json")
	public RestResponse<Individual> getIndividual(HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Individual
		Individual individual = checkAllowToDoThat(session, em);
		if(individual==null)
		{
			em.close();
			return new RestResponse<Individual>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		EntityGraph<Buyer> graph = em.createEntityGraph(Buyer.class);
    	
    	graph.addSubgraph("historicShoppingCarts");
    	graph.addSubgraph("dietaryRestrictions");
    	
    	
		em.close();
		return new RestResponse<Individual>(RestResponseStatus.SUCCESS, individual);
    }
	
	
	/*****************************************************************************************
	*										updateIndividual										 * 
	*****************************************************************************************
	*
	* Modifie les infos de profil de l'Individual connecte
	*/
	@PostMapping(path="/update", consumes = "application/json", produces = "application/json")
	public RestResponse<Individual> updateIndividual(@RequestBody Individual i, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Individual
		Individual individual = checkAllowToDoThat(session, em);
		if(individual==null)
		{
			em.close();
			return new RestResponse<Individual>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
	
		
		// Verifie les champs modifies du formulaire
		boolean isModifed = false;
		if( (i.getEmail() != null) && (!individual.getEmail().equals(i.getEmail())) )									// TODO regex
		{
			individual.setEmail(i.getEmail());
			isModifed = true;
		}
		
		if( (!individual.getAvatarFilename().equals(i.getAvatarFilename())) )	
		{
			individual.setAvatarFilename(i.getAvatarFilename());
			isModifed = true;
		}
		
		if( (!individual.getPhoneNumber().equals(i.getPhoneNumber())) )												// TODO regex
		{
			individual.setPhoneNumber(i.getPhoneNumber());
			isModifed = true;
		}
		if( (!individual.getPhoneNumber2().equals(i.getPhoneNumber2())) )													// TODO regex
		{
			individual.setPhoneNumber2(i.getPhoneNumber2());
			isModifed = true;
		}
		
		if( (i.getAddress() != null) && (!individual.getAddress().equals(i.getAddress())) )	
		{
			individual.setAddress(i.getAddress());
			isModifed = true;
		}
		
		if( (i.getCreditCard() != null) && (!individual.getCreditCard().equals(i.getCreditCard())) )	
		{
			individual.setCreditCard(i.getCreditCard());
			isModifed = true;
		}
				
		if( ( individual.isNewsletterEnabled() != i.isNewsletterEnabled()) )	
		{
			individual.setNewsletterEnabled(i.isNewsletterEnabled());
			isModifed = true;
		}
		

		if(isModifed)
		{
			try 
			{
				dao.saveOrUpdate(individual, em, false);
				System.out.println("update user (individual): "+ individual.getName() +" email:"+ individual.getEmail());
				
			} catch (Exception e1) {
				e1.printStackTrace();
				
				em.close();
				return new RestResponse<Individual>(RestResponseStatus.FAIL, null, 1, "Error: on Update Operation");
			}
		}
		
		em.close();
		
		return new RestResponse<Individual>(RestResponseStatus.SUCCESS, i);
    }
	
	
	/*****************************************************************************************
	*								deactivateIndividual										* 
	*****************************************************************************************
	*
	* Desactive le compte Individual (User connecte)
	* TODO conserver les ShoppingCart
	*/
	@GetMapping(path="/remove", produces = "application/json")
	public RestResponse<Individual> deactivateIndividual(HttpSession session, Locale locale, Model model)
    {	
		EntityManager em = StartListener.createEntityManager();
		
		
		// Recupere l'Individual a partir du User de la session et check si c'est bien cet Individual qui est connecte
		Individual individual = new Individual();
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
		
    	
    	// Supprime le User
		// TODO: Desactiver le compte au lieu de le supprimer
		try 
		{
			System.out.println(individual.getName() + " account removed");
			dao.remove(individual, em, false);
			
			// Met le User de la session a null
	    	session.setAttribute("user", null);
			
		} catch (Exception e1) {
			individual = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<Individual>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	

	
	
	
	
	
	
	
	
	
	/*********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	*********************************************************************************************************************************************/
	
	
	/*****************************************************************************************
	*										getCreditCard									 * 
	*****************************************************************************************
	* 
	* Recuperation de la credit card pour preremplir la page de payement (si il en a une).
	*/
	@GetMapping(path="/creditCard", produces = "application/json")
	public RestResponse<CreditCard> getCreditCard (HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Individual
		Individual individual = checkAllowToDoThat(session, em);
		if(individual==null)
		{
			em.close();
			return new RestResponse<CreditCard>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		CreditCard creditCard = individual.getCreditCard(); // Recupere la creditCard de l'individual
		
		em.close();
		return new RestResponse<CreditCard>(RestResponseStatus.SUCCESS, creditCard);
    }
	
	
	
	/*****************************************************************************************
	*										updateCreditCard										 * 
	*****************************************************************************************
	*
	* Sauvegarde de la carte de crédit
	*/
	@PostMapping(path="/creditCard/update", consumes = "application/json", produces = "application/json")
	public RestResponse<CreditCard> updateCreditCard(@RequestBody CreditCard cc, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Individual
		Individual individual = checkAllowToDoThat(session, em);
		if(individual==null)
		{
			em.close();
			return new RestResponse<CreditCard>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		CreditCard creditCard = individual.getCreditCard(); // recupere les infos de la creditCard de l'individual s'il y en a		
		
		
		
		// Verifie les champs modifies du formulaire de CreditCard
		boolean isModifed = false;
		
		if ( (cc.getType() != null) && (!creditCard.getType().equals(cc.getType())) )									// TODO regex
		{
			creditCard.setType(cc.getType());
			isModifed = true;
		}
		
		if ( (cc.getOwnerName() != null) && (!creditCard.getOwnerName().equals(cc.getOwnerName())) )									// TODO regex
		{
			creditCard.setOwnerName(cc.getOwnerName());
			isModifed = true;
		}
		
		if ( (cc.getCodeNumber() != null) && (!creditCard.getCodeNumber().equals(cc.getCodeNumber())) )									// TODO regex
		{
			creditCard.setCodeNumber(cc.getCodeNumber());
			isModifed = true;
		}
		
		if ( (cc.getExpirationDate() != null) && (!creditCard.getExpirationDate().equals(cc.getExpirationDate())) )									// TODO regex
		{
			creditCard.setExpirationDate(cc.getExpirationDate());
			isModifed = true;
		}
		
		if ( (cc.getCodeSecurity() != null) && (!creditCard.getCodeSecurity().equals(cc.getCodeSecurity())) )									// TODO regex
		{
			creditCard.setCodeSecurity(cc.getCodeSecurity());
			isModifed = true;
		}
		
		
		if ( (creditCard.getType() == null) || (creditCard.getOwnerName() == null) ||
				(creditCard.getCodeNumber() == null) || (creditCard.getCodeSecurity() == null) ||
				(creditCard.getExpirationDate() == null) ) {
			
			em.close();
			return new RestResponse<CreditCard>(RestResponseStatus.FAIL, null, 1, "Error: on creditCard Update : all fields must be filled");
		}
		

		if(isModifed)
		{
			try 
			{
				dao.saveOrUpdate(creditCard, em);
				System.out.println("update creditCard (individual): "+ creditCard.getOwnerName() );
				
			} catch (Exception e1) {
				e1.printStackTrace();
				
				em.close();
				return new RestResponse<CreditCard>(RestResponseStatus.FAIL, null, 1, "Error: on Update CreditCard Operation");
			}
		}
		
		em.close();
		
		return new RestResponse<CreditCard>(RestResponseStatus.SUCCESS, cc);
    }
	
	
	
}
