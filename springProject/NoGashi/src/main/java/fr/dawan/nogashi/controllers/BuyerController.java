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
import fr.dawan.nogashi.enums.ShoppingCartStatus;
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
		
		// Check si le User de la session est Buyer
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
	
	
	
	/****************************************************************************************
	*										getProductTemplates							* 
	*****************************************************************************************
	*
	* Liste tous les ProductTemplates
	*/
	@GetMapping(path="/productTemplates", produces = "application/json")
	public RestResponse<List<ProductTemplate>> getProductTemplates(HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<List<ProductTemplate>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
	
		
    	// Recupere la liste de tous les ProductTemplates
		List<ProductTemplate> listProductTemplates = new ArrayList<ProductTemplate>();
		try 
		{
			listProductTemplates = dao.findAll(ProductTemplate.class, em, true);
			for(ProductTemplate ptTmp : listProductTemplates)
				System.out.println(ptTmp);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<List<ProductTemplate>>(RestResponseStatus.FAIL, null, 1, "Error: on getproductTemplates operation");
		}
		
		em.close();
		return new RestResponse<List<ProductTemplate>>(RestResponseStatus.SUCCESS, listProductTemplates);
    }
	
	
	
	
	/*****************************************************************************************
	*										getProductsByName							 * 
	*****************************************************************************************
	*
	* Liste les Products dont le name est entre en parametre
	*/
	@GetMapping(path="/products/{name}", produces = "application/json")
	public RestResponse<List<Product>> getProductsByName(@PathVariable(name="name") String name, HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
    	// Recupere la liste des Products qui correspondent au name
    	List<Product> listProducts = new ArrayList<Product>();
		try 
		{	
			String n = name.trim();
			if(n != null)
				listProducts = dao.findNamed(Product.class, "name", n, em);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 1, "Error: get products by name operation");
		}
		
		em.close();
		return new RestResponse<List<Product>>(RestResponseStatus.SUCCESS, listProducts);
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
	*										addProductsToShoppingCart										* 
	*****************************************************************************************
	*
	* Ajoute une ligne de producTemplate au ShoppingCart, et du coup on ajoute au ShoppingCart x products associés.
	* Puis, on retire les Products au Commerce
	* 
	*/
	@GetMapping(path="/cart/add/{id_pt}/{id_c}", produces = "application/json")
	public RestResponse<ShoppingCart> addProductsToShoppingCart(@PathVariable(name="id_pt") int id_pt, @PathVariable(name="id_c") int id_c, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
	
		List<Product> listProductsToAdd = new ArrayList<Product>();
		Commerce c = null;
		ShoppingCartByCommerce scbc = null;
		
		// Recup le ShoppingCart du buyer ou en cree un nouveau s'il n'existe pas
		ShoppingCart sc = buyer.getShoppingCart();
		double totalShoppingCart = 0, totalShoppingCartByCommerce = 0;
		if (sc != null) { // sc existe
			
			// Recup le ShoppingCartByCommerce du ShoppingCart ou en crée un nouveau s'il n'existe pas
			scbc = sc.getShoppingCartByCommerce(id_c);
			if (scbc != null) { 						// scbc existe (donc, appartient au sc)
				
				totalShoppingCart = sc.getPrice();
				totalShoppingCartByCommerce = scbc.getPrice();
				c = scbc.getCommerce(); 				// Recup le Commerce lié au ShoppingCartByCommerce
				System.out.println("Recup le Commerce lié au ShoppingCartByCommerce, scbc existait : " + c);
	
			} else { // scbc n'existait pas
				
				totalShoppingCart = sc.getPrice();
				
				// Recup le Commerce via l'id_c en param
				try {
					c = dao.find(Commerce.class, id_c, em);
				} catch (Exception e1) {
					e1.printStackTrace();
					
					em.close();
					return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 1, "Error: on add ProductTemplate to ShoppingCart");
				}				
				
				scbc = new ShoppingCartByCommerce(); 	// On le cree
				scbc.setCommerce(c); 					// Attribue le commerce au ShoppingCartByCommerce
				scbc.setShoppingCart(sc); 				// On l'associe au ShoppingCart existant
				sc.addShoppingCartByCommerces(scbc); 	// Ajoute le nouveau ShoppingCartByCommerce au ShoppingCart existant
				
				System.out.println("Recup le Commerce lié au ShoppingCartByCommerce, scbc n'existait pas : " + c);
			}
			
		} else { 												// le ShoppingCart n'existait pas (donc le ShoppingCartByCommerce non plus)
			
			// Recup le Commerce via l'id_c en param
			try {
				c = dao.find(Commerce.class, id_c, em);
			} catch (Exception e1) {
				e1.printStackTrace();
				
				em.close();
				return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 1, "Error: on add ProductTemplate to ShoppingCart");
			}
			
			sc = new ShoppingCart(); 					// On le cree
			scbc = new ShoppingCartByCommerce(); 		// On cree aussi un ShoppingCartByCommerce
			scbc.setCommerce(c); 						// Attribue le commerce au ShoppingCartByCommerce
			scbc.setShoppingCart(sc); 					// On l'associe au nouveau ShoppingCart
			sc.addShoppingCartByCommerces(scbc); 		// Ajoute le nouveau ShoppingCartByCommerce au ShoppingCart existant
			
			System.out.println("Recup le Commerce lié au ShoppingCartByCommerce, sc et scbc n'existaient pas : " + c);
		}

		
		System.out.println("c.getProducts()" + c.getProducts());	
		
		
		// Liste les products a ajouter au ShoppingCartByCommerce et calcule les totaux
		for (Product p : c.getProducts()) {
			if (p.getReference().getId() == id_pt) {
				listProductsToAdd.add(p);
					
				if (p.getStatus() == ProductStatus.AVAILABLE)
					totalShoppingCartByCommerce += p.getPrice();
				else if (p.getStatus() == ProductStatus.PROMOTION)
					totalShoppingCartByCommerce += p.getSalePrice();
				else if (p.getStatus() == ProductStatus.UNSOLD)
					totalShoppingCartByCommerce += 0;
			}
		}	
		
		totalShoppingCart += totalShoppingCartByCommerce;	
		sc.setPrice(totalShoppingCart);
		scbc.setPrice(totalShoppingCartByCommerce);
		
		sc.setStatus(ShoppingCartStatus.IN_PROGRESS);
		scbc.setStatus(ShoppingCartStatus.IN_PROGRESS);
		
		
		try 
		{
			// Ajoute les products au ShoppingCartByCommerce et les retire du Commerce
			for (Product p : listProductsToAdd) {
				
				scbc.addProduct(p);			
				c.removeProduct(p);
				p.setStatus(ProductStatus.RESERVED);	
				
				dao.saveOrUpdate(c, em);
				dao.saveOrUpdate(p, em);
			}
			dao.saveOrUpdate(scbc, em);
			dao.saveOrUpdate(sc, em);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 1, "Error: on add ProductTemplate to ShoppingCart");
		}	
		
		em.close();
		return new RestResponse<ShoppingCart>(RestResponseStatus.SUCCESS, null);
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
