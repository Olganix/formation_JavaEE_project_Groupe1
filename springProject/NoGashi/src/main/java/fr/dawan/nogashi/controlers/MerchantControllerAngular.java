package fr.dawan.nogashi.controlers;

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




@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
public class MerchantControllerAngular 
{
	@Autowired
	GenericDao dao;
	
	// Retourne true si le User de la session est un Merchant
	public boolean checkAllowToDoThat(HttpSession session)
	{
		User u = (User)session.getAttribute("user");
		
		System.out.println("MerchantControllerAngular.checkAllowToDoThat : "+ u);
		
		return ( (u!=null) && (u.getRole() == UserRole.MERCHANT) );
	}
	
	

	/*****************************************************************************************
	*										getMyCommerces									 * 
	*****************************************************************************************
	* 
	* Liste les commerces du Merchant (User connecté)
	*/
	@GetMapping(path="/my-commerces", produces = "application/json")
	public RestResponse<List<Commerce>> getMyCommerces(HttpSession session)
    {
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		
		
    	EntityManager em = StartListener.createEntityManager();
		
    	List<Commerce> listCommerces = new ArrayList<Commerce>();
    	
    	
    	EntityGraph<Commerce> graph = em.createEntityGraph(Commerce.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
    	// Récupère le Merchant à partir du User de la session et check si c'est bien ce Merchant qui est connecté
		Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
    	
    	// Récupère la liste des Commerce du Merchant
		try 
		{	
			//listCommerces = dao.findAll(Commerce.class, em, false, graph);
			// TODO Reechercher avec l'id du Merchant plutôt que le name
			listCommerces = dao.findNamed(Commerce.class, "merchant", merchant.getName(), em, false, graph);
			//listCommerces = dao.findRestricted(Merchant.class, Commerce.class, em, false, graph);
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
	* Récupère un Commerce via son id
	*/
	@GetMapping(path="/my-commerces/{id}", produces = "application/json")
	public RestResponse<Commerce> getCommerceById(@PathVariable(name="id") int id, HttpSession session)
    {
		// Check si le User de la session est Merchant
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
    	
    	// Récupère le Commerce dont l'id est passé en paramètre
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
	*										addCommerce										 * 
	*****************************************************************************************
	*
	* Ajoute un nouveau Commerce pour le Merchant (User connecté)
	*/
	@PostMapping(path="/my-commerces/add", consumes = "application/json", produces = "application/json")
	public RestResponse<Commerce> addCommerce(@RequestBody Commerce c, HttpSession session, Locale locale, Model model)
    {
		System.out.println(c);
		
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		
		
		// Check si tous les champs du formulaire sont null ou si le nom, le codeSiret ou l'adresse est null
		if(	(c==null) || 
			(c.getName()==null) || ( c.getName().trim().length() ==0) ||
			(c.getCodeSiret()==null) || ( c.getCodeSiret().trim().length() ==0) ||
			(c.getAddress()==null) )
		{
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		
		EntityManager em = StartListener.createEntityManager();
		
		// Récupère le Merchant à partir du User de la session et check si c'est bien ce Merchant qui est connecté
		Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
			
		
		// Check si le name ou le codeSiret du Commerce à ajouter existe déjà dans la BDD
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
    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Commerce already exists");
    	}
			
    	
    	// Attribue le Merchant connecté au Commerce à ajouter
    	c.setMerchant(merchant);
    	
    	// Persiste le Commerce à ajouter dans la BDD
		try 
		{
			dao.saveOrUpdate(c, em, false);
			System.out.println("create commerce: "+ c.getName() +" siret:"+ c.getCodeSiret());
			
		} catch (Exception e1) {
			c = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, c);
    }
	
	
	
	/*****************************************************************************************
	*										removeCommerce										* 
	*****************************************************************************************
	*
	* Supprime un Commerce du Merchant (User connecté) récupéré via son id
	* TODO supprimer toutes les instances de produits liées au Commerce lors de la suppression de la fiche
	*/
	@GetMapping(path="/my-commerces/remove/{id}", produces = "application/json")
	// TODO Front: prévenir que la suppression de la fiche entrainera la suppression des produits en vente
	public RestResponse<Commerce> removeCommerce(@PathVariable(name="id") int id, HttpSession session, Locale locale, Model model)
    {
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		// Récupère le Merchant à partir du User de la session et check si c'est bien ce Merchant qui est connecté
		Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
			
		
		// Check si l'id du Commerce à supprimer existe dans la BDD	
		Commerce c = null;
    	try 
    	{
    		Commerce commerce = dao.find(Commerce.class, id, em);
    		if(commerce!=null)
    			c = commerce;
    		
		} catch (Exception e) {
			c = null;
			e.printStackTrace();
		}
		
    	if(c == null)
    	{
    		em.close();
    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Commerce doesn't exist");
    	}
		
    	
    	// Supprime le Commerce
		try 
		{
			dao.remove(c, em, false);
			
		} catch (Exception e1) {
			c = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	/*****************************************************************************************
	*										getProductTemplates										 * 
	*****************************************************************************************
	*
	* Liste les ProductTemplates du Merchant (User connecté)
	* Autre solution: mettre l'id du Merchant (User de session) en PathVariable
	*/
	@GetMapping(path="/my-product-templates", produces = "application/json")
	public RestResponse<List<ProductTemplate>> getProductTemplates(HttpSession session)
    {
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<ProductTemplate>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
    	EntityManager em = StartListener.createEntityManager();
		
    	
    	System.out.println( "Test Merchant : " + ((User)session.getAttribute("user")) );
    	
    	// Récupère le Merchant à partir du User de la session et check si c'est bien ce Merchant qui est connecté
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
		
    	
//    	EntityGraph<ProductTemplate> graph = em.createEntityGraph(ProductTemplate.class);
//    	Subgraph<Merchant> aa = graph.addSubgraph("merchant", Merchant.class);
//    	aa.addSubgraph("commerces");
    	
    	// Récupère la liste de tous les ProductTemplate
		try 
		{	
			//listProductsTemplates = dao.findAll(ProductTemplate.class, em, true);
			// TODO Reechercher avec l'id du Merchant plutôt que le name
			listProductsTemplates = dao.findNamed(ProductTemplate.class, "merchant", merchant.getName(), em, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		System.out.println(listProductsTemplates);
		return new RestResponse<List<ProductTemplate>>(RestResponseStatus.SUCCESS, listProductsTemplates);
    }
	
	
	
	/*****************************************************************************************
	*										getProductTemplateById									 * 
	*****************************************************************************************
	* 
	* Récupère un ProductTemplate via son id
	*/
	@GetMapping(path="/my-product-templates/{id}", produces = "application/json")
	public RestResponse<ProductTemplate> getProductTemplateById(@PathVariable(name="id") int id, HttpSession session)
    {
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		
		
    	EntityManager em = StartListener.createEntityManager();
		
    	ProductTemplate productTemplate = new ProductTemplate();
    	
    	
    	EntityGraph<ProductTemplate> graph = em.createEntityGraph(ProductTemplate.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
    	// Récupère le ProductTemplate dont l'id est passé en paramètre
		try 
		{	
			productTemplate = dao.find(ProductTemplate.class, id, em, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<ProductTemplate>(RestResponseStatus.SUCCESS, productTemplate);
    }
	
	

	/*****************************************************************************************
	*										addProductTemplate								 * 
	*****************************************************************************************
	*
	* Ajoute un nouveau ProductTemplate pour le Merchant (User connecté)
	*/
	@PostMapping(path="/my-product-templates/add", consumes = "application/json", produces = "application/json")
	public RestResponse<ProductTemplate> addProductTemplate(@RequestBody ProductTemplate pt, HttpSession session, Locale locale, Model model, boolean modif)
    {
		System.out.println("fiche produit : " + pt);
		
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
		// Check si tous les champs du formulaire sont null ou si le name, l'externalCode ou le prix est null
		if(	(pt==null) || 
			(pt.getName()==null) || ( pt.getName().trim().length() ==0) ||
			(pt.getExternalCode()==null) || ( pt.getExternalCode().trim().length() ==0) ||
			(Double.valueOf(pt.getPrice())==null) )
		{
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		EntityManager em = StartListener.createEntityManager();
		
		
		// Récupère le Merchant à partir du User de la session et check si c'est bien ce Merchant qui est connecté
		Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
				
		
		// Check si l'externalCode ou le name existe déjà et qu'il ne s'agit pas d'une modification
		ProductTemplate pt_tmp = null;
    	try 
    	{
    		List<ProductTemplate> listProductsTemplates = dao.findNamed(ProductTemplate.class, "externalCode", pt.getExternalCode(), em, false);
    		if(listProductsTemplates.size()==0)
    			listProductsTemplates = dao.findNamed(ProductTemplate.class, "name", pt.getName(), em, false);
			
    		if(listProductsTemplates.size()!=0)
    			pt_tmp = listProductsTemplates.get(0);
    		
		} catch (Exception e) {
			pt_tmp = null;
			e.printStackTrace();
		}
		
    	// TODO Front: prévenir que le produit existe déjà et demander s'il s'agit d'une modification
    	if( (pt_tmp!=null) && (!modif) )
    	{
    		em.close();
    		return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Operation canceled: Product template already exists");	
    	}
		
    	
    	// Attribue le Merchant connecté au Commerce à ajouter
    	pt.setMerchant(merchant);
    	
    	// Persiste le Commerce à ajouter dans la BDD
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
	*										removeProductTemplate										* 
	*****************************************************************************************
	*
	* Supprime un ProductTemplate du Merchant (User connecté) récupéré via son id
	* TODO supprimer toutes les instances de produits lors de la suppression de la fiche
	*/
	@GetMapping(path="/product-template/remove/{id}", produces = "application/json")
	// TODO Front: prévenir que la suppression de la fiche entrainera la suppression des produits en vente
	public RestResponse<ProductTemplate> removeProductTemplate(@PathVariable(name="id") int id, HttpSession session, Locale locale, Model model)
    {
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		// Récupère le Merchant à partir du User de la session et check si c'est bien ce Merchant qui est connecté
		Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
			
		
		// Check si l'id du ProductTemplate à supprimer existe dans la BDD	
		ProductTemplate pt = null;
    	try 
    	{
    		ProductTemplate productTemplate = dao.find(ProductTemplate.class, id, em);
    		if(productTemplate!=null)
    			pt = productTemplate;
    		
		} catch (Exception e) {
			pt = null;
			e.printStackTrace();
		}
		
    	if(pt == null)
    	{
    		em.close();
    		return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: Product template doesn't exist");
    	}
		
    	
    	// Supprime le ProductTemplate
		try 
		{
			dao.remove(pt, em, false);
			
		} catch (Exception e1) {
			pt = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<ProductTemplate>(RestResponseStatus.SUCCESS, null);
    }
	
}
