package fr.dawan.nogashi.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.Commerce;
import fr.dawan.nogashi.beans.Merchant;
import fr.dawan.nogashi.beans.Product;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.ShoppingCart;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
@RequestMapping("/buyer")
public class BuyerController
{
	@Autowired
	GenericDao dao;
	
	
	//TODO
	// ------------------------- /buyer

	// Todo voir la recherche
	// -Ville -> Gps -> rayon (depend de la recherche, et en fait avec juste "Ville" il est plus grand) -> ordre dans le listing, et le rayon permet juste d'eviter 70 000 affiche.


	// /commerce/{id_c}/product/{id}/reserve				// ca ne marche qu'avec l'idée de la personnalisation (non prioritaire)
	// /commerce/{id_c}/productTemplate/{id}/reserve/{quantity}		//add ou remove en fonction de la quantité. => attention au message de warning utilisateur si c'est au dessus du nombre d'elements.
	// /cart/remove/{id_pt}									// supprime du panier la ligne du producTemplate, et du coups on supprime du planier touts les products associés.
	// /cart/clear

	
	
	// Retourne Buyer si le User de la session est un Buyer et qu'il est bien dans la Bdd
	public Buyer checkAllowToDoThat(HttpSession session, EntityManager em)
	{
		User u = (User)session.getAttribute("user");
		System.out.println("BuyerController.checkAllowToDoThat : "+ u);
		
		
		Buyer buyer = null;
		if( (u!=null) && ( (u.getRole() == UserRole.INDIVIDUAL) || (u.getRole() == UserRole.ASSOCIATION)) )
		{
			// Recupere le Buyer a partir du User de la session
	    	// et aussi check si c'est bien ce Buyer qui est connecte
			try {
				buyer = dao.find(Buyer.class, ((User)session.getAttribute("user")).getId(), em);
			} catch (Exception e) {
				buyer = null;
				e.printStackTrace();
			}
		}
		return buyer;
	}
	
	
		
	/*****************************************************************************************
	*										getCommerceById									 * 
	*****************************************************************************************
	* 
	* Recupere un Commerce via son id
	* Note : le name des Commerce n'est pas unique
	*/
	@GetMapping(path="/commerce/{id}", produces = "application/json")
	public RestResponse<Commerce> getCommerceById(@PathVariable(name="id") int id, HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
    	// Recupere le Commerce dont l'id est passe en parametre
		Commerce commerce = null;
		try 
		{	
			commerce = dao.find(Commerce.class, id, em);				//todo faire en sort que seulement le bon merchant peut voir avec le bon id.
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: get commerce operation");
		}
		
		em.close();
		return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, commerce);
    }
	
	
	
	
	
	/*****************************************************************************************
	*										getProductsByCommerce							 * 
	*****************************************************************************************
	*
	* Liste les Products d'un Commerce dont l'id est entre en parametre
	* Note : le name des Commerce n'est pas unique
	*/
	@GetMapping(path="/commerce/{id_c}/products", produces = "application/json")
	public RestResponse<List<Product>> getProductsByCommerce(@PathVariable(name="id_c") int id_c, HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
    	// Recupere la liste des Products du Commerce selectionne
    	List<Product> listProducts = new ArrayList<Product>();
		try 
		{	
			Commerce c = dao.find(Commerce.class, id_c, em);
			if(c!=null)
				listProducts = dao.findBySomething(Product.class, "commerce", c, em);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 1, "Error: get products by commerce operation");
		}
		
		em.close();
		return new RestResponse<List<Product>>(RestResponseStatus.SUCCESS, listProducts);
    }
	
	
	

	/*****************************************************************************************
	*										getShoppingCart									 * 
	*****************************************************************************************
	* 
	* Recupere le shoppingCart complet du Buyer (User connecte)
	*/
	@GetMapping(path="/shoppingCart", produces = "application/json")					
	public RestResponse<ShoppingCart> getShoppingCart(HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
    	// Recupere le ShoppingCart du Buyer via son name
		List<ShoppingCart> list_sc = new ArrayList<ShoppingCart>();
		ShoppingCart sc = null;
		try 
		{	
			list_sc = dao.findBySomething(ShoppingCart.class, "buyer", buyer, em, false);
			if (list_sc.get(0)!=null)
				sc = list_sc.get(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<ShoppingCart>(RestResponseStatus.SUCCESS, sc);
    }
	
	
}
