package fr.dawan.nogashi.controlers;

import java.net.URI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.Commerce;
import fr.dawan.nogashi.beans.Merchant;
import fr.dawan.nogashi.beans.ProductTemplate;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;
import fr.dawan.nogashi.tools.EmailTool;




@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
public class MerchantControllerAngular 
{
	@Autowired
	GenericDao dao;
	
	
	public boolean checkAllowToDoThat(HttpSession session)
	{
		User u = (User)session.getAttribute("user");
		
		System.out.println("MerchantControllerAngular.checkAllowToDoThat : "+ u);
		
		return ( (u!=null) && (u.getRole() == UserRole.MERCHANT) );
	}
	
	

	/*****************************************************************************************
	*										getCommerces									 * 
	*****************************************************************************************/
	@RequestMapping(path="/getCommerces", produces = "application/json")
	public RestResponse<List<Commerce>> getMerchants(HttpSession session)
    {
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 5, "Error: User don't be allowed on this operation");
		
		
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
	*										addCommerce										 * 
	*****************************************************************************************/
	@PostMapping(path="/addCommerce", produces = "application/json")
	//test : http://localhost:8080/nogashi/addCommerce
	public RestResponse<Commerce> addCommerce(@RequestBody Commerce c, HttpSession session, Locale locale, Model model)
    {
		System.out.println(c);
		if(!checkAllowToDoThat(session))
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User don't be allowed on this operation");
		
		
		
		if(	(c==null) || 
			(c.getName()==null) || ( c.getName().trim().length() ==0) ||
			(c.getCodeSiret()==null) || ( c.getCodeSiret().trim().length() ==0) ||
			(c.getAddress()==null) )
		{
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Not enought arguments");
		}
		
		
		EntityManager em = StartListener.createEntityManager();
		
		Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class,  ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
		
		
		
		
		
		Commerce c_tmp = null;
    	try 
    	{
    		List<Commerce> listCommerces = dao.findNamed(Commerce.class, "name", c.getName(), em, false);
    		if(listCommerces.size()==0)
    			listCommerces = dao.findNamed(Commerce.class, "codeSiret", c.getCodeSiret(), em, false);
			
    		if(listCommerces.size()!=0)
    			c_tmp = listCommerces.get(0);
    		
		} catch (Exception e) {
			c_tmp = null;
			e.printStackTrace();
		}
		
    	if(c_tmp!=null)
    	{
    		em.close();
    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Commerce allready exist");
    	}
		
    	
    	
    	
    	
    	
    	c.setMerchant(merchant);
    	
		try 
		{
			dao.saveOrUpdate(c, em, false);
			System.out.println("create commerce: "+ c.getName() +" role:"+ c.getCodeSiret());
			
		} catch (Exception e1) {
			c = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, c);
    }
	
	
	
	/*****************************************************************************************
	*										getProductsTemplates										 * 
	*****************************************************************************************/
	@RequestMapping(path="/getProductsTemplates", produces = "application/json")
	public RestResponse<List<ProductTemplate>> getProducts(HttpSession session)
    {
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<ProductTemplate>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
    	EntityManager em = StartListener.createEntityManager();
		
    	
    	System.out.println( "Test Merchant : " + ((User)session.getAttribute("user")) );
    	
    	Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class,  ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<List<ProductTemplate>>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
    	
    	
    	List<ProductTemplate> listProductsTemplates = new ArrayList<ProductTemplate>();
		
    	
    	EntityGraph<ProductTemplate> graph = em.createEntityGraph(ProductTemplate.class);
    	Subgraph<Merchant> aa = graph.addSubgraph("merchant", Merchant.class);
    	aa.addSubgraph("commerces");
    	
		try 
		{	
			listProductsTemplates = dao.findAll(ProductTemplate.class, em, true, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		System.out.println(listProductsTemplates);
		return new RestResponse<List<ProductTemplate>>(RestResponseStatus.SUCCESS, listProductsTemplates);
    }
	
	
	
	

	/*****************************************************************************************
	*										addProductTemplate								 * 
	*****************************************************************************************/
	@PostMapping(path="/addProductTemplate", produces = "application/json")
	//test : http://localhost:8080/nogashi/addProductTemplate
	public RestResponse<ProductTemplate> addProductTemplate(@RequestBody ProductTemplate pt, HttpSession session, Locale locale, Model model)
    {
		System.out.println("fiche produit : " + pt);
		
		if(!checkAllowToDoThat(session))
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		ProductTemplate pt_tmp = null;
    	try 
    	{
    		List<ProductTemplate> listProductsTemplates = dao.findNamed(ProductTemplate.class, "externalCode", pt.getExternalCode(), em, false);
    		// TODO proposer les fiches produits qui portent le même nom pour faciliter la recherche et check s'il ne s'agit pas d'une modification d'une fiche existante plutôt que d'un ajout
    		 /* if(listProductsTemplates.size()==0)
    			listProductsTemplates = dao.findNamed(ProductTemplate.class, "name", pt.getName(), em, false);
    		*/
			
    		if(listProductsTemplates.size()!=0)
    			pt_tmp = listProductsTemplates.get(0);
    		
		} catch (Exception e) {
			pt_tmp = null;
			e.printStackTrace();
		}
		
    	if(pt_tmp!=null)
    	{
    		em.close();
    		return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: Product template already exists");
    	}
		
    	
		try 
		{
			dao.saveOrUpdate(pt, em, false);
			
			System.out.println("create product template: "+ pt.getExternalCode() +" name:"+ pt.getName());
			
			
		} catch (Exception e1) {
			pt = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<ProductTemplate>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	

	/*****************************************************************************************
	*										deleteProductTemplate										 * 
	*****************************************************************************************/
	@PostMapping(path="/deleteProductTemplate", produces = "application/json")
	//test : http://localhost:8080/nogashi/
	// TODO supprimer toutes les instances de produits (Product) lors de la suppression de la fiche
	// (prévenir l'user que la suppression de la fiche entrainera la suppression des produits en vente)
	public RestResponse<ProductTemplate> deleteProductTemplate(@RequestBody ProductTemplate pt, HttpSession session, Locale locale, Model model)
    {
		System.out.println("fiche produit : " + pt);
		
		
		if(!checkAllowToDoThat(session))
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User don't be allowed on this operation");
		
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		ProductTemplate pt_tmp = null;
    	try 
    	{
    		List<ProductTemplate> listProductsTemplates = dao.findNamed(ProductTemplate.class, "externalCode", pt.getExternalCode(), em, false);
			
    		if(listProductsTemplates.size()!=0)
    			pt_tmp = listProductsTemplates.get(0);
    		
		} catch (Exception e) {
			pt_tmp = null;
			e.printStackTrace();
		}
		
    	if(pt_tmp == null)
    	{
    		em.close();
    		return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: Product template doesn't exist");
    	}
		
    	
		try 
		{
			dao.remove(pt, em, false);
			
			System.out.println("delete product template: "+ pt.getExternalCode() +" name:"+ pt.getName());
			
			
		} catch (Exception e1) {
			pt = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<ProductTemplate>(RestResponseStatus.SUCCESS, null);
    }
	
}
