package fr.dawan.nogashi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.Commerce;
import fr.dawan.nogashi.beans.Product;
import fr.dawan.nogashi.beans.ProductTemplate;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.ShoppingCart;
import fr.dawan.nogashi.beans.ShoppingCartByCommerce;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.ProductStatus;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
@RequestMapping(path = "/buyer")
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
			commerce = dao.find(Commerce.class, id, "buyer", buyer, em);
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: get commerce operation");
		}
		
		em.close();
		return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, commerce);
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
	*										getProductTemplateById						 * 
	*****************************************************************************************
	* 
	* Recupere un ProductTemplate via son id
	*/
	@GetMapping(path="/productTemplate/{id}", produces = "application/json")
	public RestResponse<ProductTemplate> getProductTemplateById(@PathVariable(name="id") int id, HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	**********************************************************************************************************************************************
	*********************************************************************************************************************************************/
	
	

	
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
	
	
	/*****************************************************************************************
	*										removeProductTemplate										* 
	*****************************************************************************************
	*
	* Supprime un ProductTemplate du ShoppingCartByCommerce (User connecte) recupere via son id
	* TODO supprimer toutes les instances de produits lors de la suppression de la ligne de productTemplate
	* Puis, ajoute les Products au Commerce
	* 
	*/
	// Supprime une ligne de producTemplate du ShoppingCart, et du coup on supprime du ShoppingCart tous les products associés.
	// Puis, on ajoute les Products au Commerce
	@GetMapping(path="/cart/remove/{id_pt}/{id_c}", produces = "application/json")
	public RestResponse<ShoppingCart> removeProductTemplate(@PathVariable(name="id_pt") int id_pt, @PathVariable(name="id_c") int id_c, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		// Recup le ShoppingCart du buyer
		ShoppingCart sc = buyer.getShoppingCart();
		if (sc == null) {
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 1, "Error: ShoppingCart does not exist");	
		}
		
		ShoppingCartByCommerce scbc = sc.getShoppingCartByCommerce(id_c);
		
		
		List<Product> listToDelete = new ArrayList<Product>();
		for (Product p : scbc.getProducts())
			if (p.getReference().getId() == id_pt)
				listToDelete.add(p);

		
		try 
		{
			for (Product p : listToDelete) {
				
				scbc.removeProduct(p);
				scbc.getCommerce().addProduct(p);
				p.setStatus(ProductStatus.AVAILABLE); // TODO: voir quel statut pour les Products (en fonction de l'heure ?)	
				
				dao.saveOrUpdate(scbc.getCommerce(), em);
				dao.saveOrUpdate(p, em);
			}
			dao.saveOrUpdate(scbc, em);
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 1, "Error: on remove ProductTemplate from ShoppingCart");
		}
		
		em.close();
		return new RestResponse<ShoppingCart>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	/*****************************************************************************************
	*										clearShoppingCart										* 
	*****************************************************************************************
	*
	* Supprime un ShoppingCart du Buyer (User connecte)
	* Et remet les Products dans le Commerce
	* TODO: voir sous quel statut les Products sont ajoutes (en fonction de l'heure ?)
	* 
	*/
	@GetMapping(path="/cart/clear", produces = "application/json")
	public RestResponse<ShoppingCart> clearShoppingCart(HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		// Recup le ShoppingCart du buyer
		ShoppingCart sc = buyer.getShoppingCart();
		if (sc == null) {
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 1, "Error: ShoppingCart does not exist");	
		}
		
		
		try 
		{	
			buyer.setShoppingCart(null); // Met le ShoppingCart du Buyer  à null
			
			// Pour chaque ShoppingCartByCommerce du ShoppingCart :
			// ajoute les produits au Commerce
			// et met à null les Commerce et ShoppingCart du ShoppingCartByCommerce
			for(ShoppingCartByCommerce scbc : sc.getShoppingCartByCommerces()) {
				
				Commerce c = scbc.getCommerce();
				for (Product p : scbc.getProducts())
					c.addProduct(p);
				
				scbc.setShoppingCart(null);
				scbc.setCommerce(null);
				
				
				dao.remove(scbc, em);
			}
				
			
			// Supprime le ShoppingCart de la BDD
			dao.remove(sc, em);
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 1, "Error: on clear ShoppingCart");
		}
		
		em.close();
		return new RestResponse<ShoppingCart>(RestResponseStatus.SUCCESS, null);
    }
	
}
