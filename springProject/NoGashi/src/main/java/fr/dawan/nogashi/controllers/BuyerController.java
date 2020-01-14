package fr.dawan.nogashi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.Commerce;
import fr.dawan.nogashi.beans.Product;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;

/**
 * 
 * Listes des methodes :
 * 
 * NB: le name des Commerces n'est pas unique
 * getCommerces: Liste tous les commerces
 * getCommerceById: Recupere un Commerce via son id 
 * 
 * getProductsByCity: Liste tous les Products d'une ville entree en parametre 
 * getProductsByCommerce: Liste les Products d'un Commerce dont l'id est entre en parametre
 * getProductById: Recupere un Product via son id
 * 
 * addProductToCart
 *
 */
@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
public class BuyerController
{
	@Autowired
	GenericDao dao;
	
	// Retourne true si le User de la session est une Association ou un Individual
	public boolean checkAllowToDoThat(HttpSession session)
	{
		User u = (User)session.getAttribute("user");
		
		System.out.println("BuyerController.checkAllowToDoThat : "+ u);
		
		return ( (u!=null) && ( (u.getRole() == UserRole.ASSOCIATION) || (u.getRole() == UserRole.INDIVIDUAL) ) );
	}
	
	
	/*****************************************************************************************
	*										getCommerces									 * 
	*****************************************************************************************
	*
	* Liste tous les commerces
	*/
	@GetMapping(path="/commerces", produces = "application/json")
	public RestResponse<List<Commerce>> getCommerces(HttpSession session)
    {
		// Check si le User de la session est Association ou Individual
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
    	EntityManager em = StartListener.createEntityManager();
		
    	List<Commerce> listCommerces = new ArrayList<Commerce>();		
    	
    	
    	EntityGraph<Commerce> graph = em.createEntityGraph(Commerce.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
    	// Recupere la liste de tous les Commerces
		try 
		{	
			listCommerces = dao.findAll(Commerce.class, em, false, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<Commerce>>(RestResponseStatus.SUCCESS, listCommerces);
    }
	
	
	/*****************************************************************************************
	*										getCommerceById									 * 
	*****************************************************************************************
	* 
	* Recupere un Commerce via son id
	* Note : le name des Commerce n'est pas unique
	*/
	@GetMapping(path="/commerces/{id}", produces = "application/json")
	public RestResponse<Commerce> getCommerceById(@PathVariable(name="id") int id, HttpSession session)
    {
		// Check si le User de la session est Association ou Individual
		if(!checkAllowToDoThat(session))
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		
		
    	EntityManager em = StartListener.createEntityManager();
		
    	Commerce commerce = new Commerce();
    	
    	
    	EntityGraph<Commerce> graph = em.createEntityGraph(Commerce.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
    	// Recupere le Commerce dont l'id est passe en parametre
		try 
		{	
			commerce = dao.find(Commerce.class, id, em, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, commerce);
    }
	
	
	
	/*****************************************************************************************
	*										getProductsByCity									     * 
	*****************************************************************************************
	*
	* Liste tous les Products d'une ville entree en parametre
	*/
	@GetMapping(path="/products/{cityName}", produces = "application/json")
	public RestResponse<List<Product>> getProducts( @PathVariable(name="cityName") String cityName, HttpSession session)
    {
		// Check si le User de la session est Association ou Individual
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
    	EntityManager em = StartListener.createEntityManager();
		
    	List<Product> listProducts = new ArrayList<Product>();	
    	
    	
    	EntityGraph<Product> graph = em.createEntityGraph(Product.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
		// Liste les Products de la cityName
    	try 
		{	
			//listProducts = dao.findBySomethingNamed(Product.class, "address", "cityName", cityName, em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<Product>>(RestResponseStatus.SUCCESS, listProducts);
    }
	
	
	
	/*****************************************************************************************
	*										getProductsByCommerce										 * 
	*****************************************************************************************
	*
	* Liste les Products d'un Commerce dont l'id est entre en parametre
	* Note : le name des Commerce n'est pas unique
	*/
	@GetMapping(path="/commerce/{id}/products", produces = "application/json")
	public RestResponse<List<Product>> getProductsByCommerce(@PathVariable(name="id") int id, HttpSession session)
    {
		// Check si le User de la session est Association ou Individual
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
    	EntityManager em = StartListener.createEntityManager();
		
    	
    	System.out.println( "Test Buyer : " + ((User)session.getAttribute("user")) );
    	
    	// Recupere le Buyer a partir du User de la session et check si c'est bien ce Buyer qui est connecte
    	Buyer buyer = null;
		try {
			buyer = dao.find(Buyer.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(buyer==null)
    	{
    		em.close();
    		return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 5, "Error: wrong Buyer session information");
    	}
    	
    	
    	List<Product> listProducts = new ArrayList<Product>();
		
    	
    	EntityGraph<Product> graph = em.createEntityGraph(Product.class);
    	/*Subgraph<Buyer> aa = graph.addSubgraph("buyer", Buyer.class);
    	aa.addSubgraph("commerces");*/
    	
    	// Recupere la liste des Products du Commerce selectionne
		try 
		{	
			//listProducts = dao.findBySomethingNamed(Product.class, "commerces", "name", commerceName, em, false, graph);
			//listProducts = dao.findNamed(Product.class, "commerce", commerceName, em, false, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		System.out.println(listProducts);
		return new RestResponse<List<Product>>(RestResponseStatus.SUCCESS, listProducts);
    }
	

	/*****************************************************************************************
	*										getProductById									 * 
	*****************************************************************************************
	* 
	* Recupere un Product via son id
	*/
	@GetMapping(path="/products/{id}", produces = "application/json")
	public RestResponse<Product> getProductById(@PathVariable(name="id") int id, HttpSession session)
    {
		// Check si le User de la session est Association ou Individual
		if(!checkAllowToDoThat(session))
			return new RestResponse<Product>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		
		
    	EntityManager em = StartListener.createEntityManager();
		
    	Product product= new Product();
    	
    	
    	EntityGraph<Product> graph = em.createEntityGraph(Product.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
    	// Recupere le Product dont l'id est passe en parametre
		try 
		{	
			product= dao.find(Product.class, id, em, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<Product>(RestResponseStatus.SUCCESS, product);
    }
	

	/*****************************************************************************************
	*										addProductToCart										 * 
	*****************************************************************************************/
	@PostMapping(path="/shopping-cart/add", produces = "application/json")
	//test : http://localhost:8080/nogashi/addProductToCart
	// Ajoute un produit au panier
	public RestResponse<Product> addProductToCart(@RequestBody Product p, HttpSession session, Locale locale, Model model)
    {
		System.out.println("produit : " + p);
		
		if(!checkAllowToDoThat(session))
			return new RestResponse<Product>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
		
		
		
		return new RestResponse<Product>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	
}
