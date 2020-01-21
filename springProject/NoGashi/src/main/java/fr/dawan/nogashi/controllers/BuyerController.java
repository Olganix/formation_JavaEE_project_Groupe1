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

import fr.dawan.nogashi.beanJson.ProductTemplateListForAdd;
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
	*										getProductTemplatesForAdd						* 
	*****************************************************************************************
	*
	* Liste tous les ProductTemplates , les commerce associé (utilisation de ProductTemplateListForAdd au lieu de les commerces attachéqs qui créer des boucle infini si non transient/jsonIgnore), et donne le nombre de product qui ne sont pasa dans une shopping cart.
	*/
	@GetMapping(path="/productTemplatesForAdd", produces = "application/json")
	public RestResponse<ProductTemplateListForAdd> getProductTemplatesForAdd(HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<ProductTemplateListForAdd>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
	
		
		ProductTemplateListForAdd ptla = new ProductTemplateListForAdd();
		try 
		{
			List<ProductTemplate> lpt = dao.findAll(ProductTemplate.class, em, true);
			for(ProductTemplate pt : lpt)
			{
				for(Commerce c : pt.getCommerces())
				{
					Integer nbProducts = 0; 
					for(Product p : c.getProducts())
						if(p.getShoppingCart()==null)
							nbProducts++;
					
					
					ptla.add(pt, c, nbProducts);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<ProductTemplateListForAdd>(RestResponseStatus.FAIL, null, 1, "Error: on getProductTemplatesForAdd operation");
		}
		
		em.close();
		return new RestResponse<ProductTemplateListForAdd>(RestResponseStatus.SUCCESS, ptla);
    }
	
	
	
	
	
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
	
	
	
	/****************************************************************************************
	*										getCommerceForAProductTemplate					* 
	*****************************************************************************************
	*
	* Liste tous les ProductTemplates
	*/
	@GetMapping(path="/productTemplate/{id}/commerces", produces = "application/json")
	public RestResponse<List<Commerce>> getCommerceForAProductTemplate(@PathVariable(name="id") int id,HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
	
		
    	// Recupere la liste de tous les Commerce
		List<Commerce> lc = new ArrayList<Commerce>();
		try 
		{
			ProductTemplate pt = dao.find(ProductTemplate.class, id, em);
			if(pt==null)
			{
				em.close();
				return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 1, "Error: ProductTempalte not found.");
			}
			lc = dao.findBySomething(Commerce.class, "commerces", pt, em);
			for(Commerce cTmp : lc)
				System.out.println(cTmp);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 1, "Error: on getproductTemplates operation");
		}
		
		em.close();
		return new RestResponse<List<Commerce>>(RestResponseStatus.SUCCESS, lc);
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
			{
				List<Product> lp = dao.findBySomething(Product.class, "commerce", c, em);
				for(Product p : lp)
					if(p.getShoppingCart()==null)						//on ne prend pas les product attaché a une shoppingcart , carils sont reservés.
						listProducts.add(p); 
			}
			
			
			
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
		
		
		ShoppingCart sc = buyer.getShoppingCart();
		if(sc==null)
		{
			sc = new ShoppingCart(buyer);
		}
		
		sc.calculPrice();
		
		try 
		{
			dao.saveOrUpdate(sc, em);
			dao.saveOrUpdate(buyer, em);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		em.close();
		return new RestResponse<ShoppingCart>(RestResponseStatus.SUCCESS, sc);
    }
	
	
	
	/*****************************************************************************************
	*										addProductsToShoppingCart						* 
	*****************************************************************************************
	*
	* Ajoute une ligne de producTemplate au ShoppingCart, et du coup on ajoute au ShoppingCart x products associés.
	* Puis, on retire les Products au Commerce
	* 
	*/
	@GetMapping(path="/cart/add/{id_pt}/{id_c}/{number}", produces = "application/json")
	public RestResponse<ShoppingCart> addProductsToShoppingCart(@PathVariable(name="id_pt") int id_pt, @PathVariable(name="id_c") int id_c, @PathVariable(name="number") int number, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Buyer
		Buyer buyer = checkAllowToDoThat(session, em);
		if(buyer==null)
		{
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
	
		// Recup le Commerce via l'id_c en param
		Commerce c = null;
		try {
			c = dao.find(Commerce.class, id_c, em);
		} catch (Exception e1) {
			e1.printStackTrace();
			
			em.close();
			return new RestResponse<ShoppingCart>(RestResponseStatus.FAIL, null, 1, "Error: on Commerce not found");
		}
		
		
		// Recup le ShoppingCart du buyer ou en cree un nouveau s'il n'existe pas
		ShoppingCart sc = buyer.getShoppingCart();
		if(sc==null)
			sc = new ShoppingCart(buyer);
		
		
		// Recup le ShoppingCartByCommerce du ShoppingCart ou en crée un nouveau s'il n'existe pas
		ShoppingCartByCommerce scbc = sc.getShoppingCartByCommerce(id_c);
		if(scbc==null)
			scbc = new ShoppingCartByCommerce(c, sc);
		
		try 
		{
			// Liste les products a ajouter au ShoppingCartByCommerce et calcule les totaux
			List<Product> listToAdd = new ArrayList<Product>();
			int number_tmp = number;
			for (Product p : c.getProducts())
			{
				if ((p.getReference().getId() == id_pt) && (p.getShoppingCart()==null))				//ceux attaché a une shoppingcart ne peuvent etre pris.
				{
					listToAdd.add(p);
					
					number_tmp--;
					if(number_tmp==0)
						break;
				}
			}
			
			for (Product p : listToAdd)
			{
				scbc.addProduct(p);
				c.removeProduct(p);
			}
			
			if(listToAdd.size()!=0)
			{
				scbc.calculPrice();
				sc.calculPrice();
				
				sc.setStatus(ShoppingCartStatus.IN_PROGRESS);
				scbc.setStatus(ShoppingCartStatus.IN_PROGRESS);

				
				
				System.out.println("a3");
				for (Product p : listToAdd)
				{
					p.setStatus(ProductStatus.RESERVED);
					dao.saveOrUpdate(p, em);
				}

				System.out.println("a2");
				dao.saveOrUpdate(buyer, em);
				
				System.out.println("a1");
				dao.saveOrUpdate(c, em);
				
				System.out.println("a5");
				for(ShoppingCartByCommerce scbc2 : sc.getShoppingCartByCommerces())
					dao.saveOrUpdate(scbc2, em);
				
				System.out.println("a6");
				dao.saveOrUpdate(sc, em);
			}
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
