package fr.dawan.nogashi.controlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
public class BuyerController 
{
	@Autowired
	GenericDao dao;
	
	
	public boolean checkAllowToDoThat(HttpSession session)
	{
		User u = (User)session.getAttribute("user");
		
		System.out.println("BuyerController.checkAllowToDoThat : "+ u);
		
		return ( (u!=null) && ( (u.getRole() == UserRole.ASSOCIATION) || (u.getRole() == UserRole.INDIVIDUAL) ) );
	}
	
	
	/*****************************************************************************************
	*										getCommerces									 * 
	*****************************************************************************************/
	// Liste tous les commerces
	@RequestMapping(path="/getCommerces", produces = "application/json")
	public RestResponse<List<Commerce>> getCommerces(HttpSession session)
    {
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
	*										getProducts									 * 
	*****************************************************************************************/
	// Liste tous les produits
	@RequestMapping(path="/getProducts", produces = "application/json")
	public RestResponse<List<Product>> getProducts(HttpSession session)
    {
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
    	
		try 
		{	
			listProducts = dao.findAll(Product.class, em, false, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<Product>>(RestResponseStatus.SUCCESS, listProducts);
    }
	
	
	
	/*****************************************************************************************
	*										getProductsByCommerce										 * 
	*****************************************************************************************/
	@RequestMapping(path="/getProductsByCommerce", produces = "application/json")
	public RestResponse<List<Product>> getProductsByCommerce(HttpSession session, String idCommerce)
    {
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
    	EntityManager em = StartListener.createEntityManager();
		
    	
    	System.out.println( "Test Buyer : " + ((User)session.getAttribute("user")) );
    	
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
    	
		try 
		{	
			listProducts = dao.findNamed(Product.class, "id", idCommerce, em, true, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		System.out.println(listProducts);
		return new RestResponse<List<Product>>(RestResponseStatus.SUCCESS, listProducts);
    }
	


	/*****************************************************************************************
	*										addProductToCart										 * 
	*****************************************************************************************/
	@PostMapping(path="/addProductToCart", produces = "application/json")
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
