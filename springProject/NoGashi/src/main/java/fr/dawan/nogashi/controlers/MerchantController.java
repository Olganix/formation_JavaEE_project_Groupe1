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
public class MerchantController 
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
	*										getMerchantAccount									 * 
	*****************************************************************************************
	* 
	* Recupere le User (Merchant) de la session via son id 
	*/
	@GetMapping(path="/merchant-account", produces = "application/json")
	public RestResponse<User> getMerchantAccount(HttpSession session)
    {
		// Check s'il y a un User dans la session
		User u = (User)session.getAttribute("user");
    	if(u==null)
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 1, "Not Connected");
		
		EntityManager em = StartListener.createEntityManager();
		
		User user = new User();
    	
    	
    	//EntityGraph<User> graph = em.createEntityGraph(User.class);
    	/*
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	*/
    	
    	// Recupere le Merchant a partir du User de la session et check si c'est bien ce Merchant qui est connecte
		try {
			user = dao.find(Merchant.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user==null)
    	{
    		em.close();
    		return new RestResponse<User>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
		
		
		em.close();
		return new RestResponse<User>(RestResponseStatus.SUCCESS, user);
    }
	
	
	
	/*****************************************************************************************
	*										updateMerchantAccount										 * 
	*****************************************************************************************
	*
	* Modifie les infos du Merchant connecte
	*/
	@PostMapping(path="/merchant-account/update", consumes = "application/json", produces = "application/json")
	public RestResponse<Merchant> updateMerchantAccount(@RequestBody Merchant m, HttpSession session, Locale locale, Model model)
    {
		// Check s'il y a un User dans la session
		User u = (User)session.getAttribute("user");
    	if(u==null)
    		return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 1, "Not Connected");
    	
		// Check si les champs obligatoires du formulaire sont null
		if(	(m==null) || 
			(m.getName()==null) || ( m.getName().trim().length() ==0) ||
			(m.getEmail()==null) || ( m.getEmail().trim().length() ==0) ||
			(m.getPassword()==null) || ( m.getPassword().trim().length() ==0) ||
			(m.getCodeSiren() ==null) || ( m.getCodeSiren().trim().length() ==0) ||
			(m.getAddress()==null)
			)
			
		{
			return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		Merchant merchant = new Merchant();
		
		EntityManager em = StartListener.createEntityManager();
		
		// Recupere le Merchant a partir du User de la session et check si c'est bien ce Merchant qui est connecte
		try {
			merchant = dao.find(Merchant.class, ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 5, "Error: wrong Merchant session information");
    	}
		
		// Check si le name ou l'email du User a modifier existe deja dans la BDD
		// TODO Gerer le cas ou le champ n'a pas ete modifie (expl : l'email existe deja mais il s'agit de celui du User en cours)
		User u_tmp = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "name", m.getName(), em, false);
    		if(listUsers.size()==0)
    			listUsers = dao.findNamed(User.class, "email", m.getEmail(), em, false);
			
    		if(listUsers.size()!=0)
    			u_tmp = listUsers.get(0);
    		
		} catch (Exception e) {
			u_tmp = null;
			e.printStackTrace();
		}
		
    	if(u_tmp!=null)
    	{
    		em.close();
    		return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 1, "Error: a User with this email or name already exists");
    	}
			
    	// Persiste le Merchant a modifier dans la BDD
		try 
		{
			dao.saveOrUpdate(m, em, false);
			System.out.println("update user (merchant): "+ m.getName() +" email:"+ m.getEmail());
			
		} catch (Exception e1) {
			m = null;
			e1.printStackTrace();
		}
		
		em.close();
		
		return new RestResponse<Merchant>(RestResponseStatus.SUCCESS, m);
    }
	
	

	/*****************************************************************************************
	*										getMyCommerces									 * 
	*****************************************************************************************
	* 
	* Liste les commerces du Merchant (User connecte)
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
    	
    	// Recupere le Merchant a partir du User de la session et check si c'est bien ce Merchant qui est connecte
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
		
    	// Recupere la liste des Commerce du Merchant via son name
		try 
		{	
			listCommerces = dao.findBySomethingNamed(Commerce.class, "merchant", "name", merchant.getName(), em, false, graph);
			for(Commerce cTmp : listCommerces)
				System.out.println(cTmp);
			
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
	*										addCommerce										 * 
	*****************************************************************************************
	*
	* Ajoute un nouveau Commerce pour le Merchant (User connecte)
	*/
	@PostMapping(path="/my-commerces/add", consumes = "application/json", produces = "application/json")
	public RestResponse<Commerce> addCommerce(@RequestBody Commerce c, HttpSession session, Locale locale, Model model)
    {
		System.out.println(c);
		
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		
		
		// Check si tous les champs du formulaire sont null ou si le name, le codeSiret ou l'adresse est null
		if(	(c==null) || 
			(c.getName()==null) || ( c.getName().trim().length() ==0) ||
			(c.getCodeSiret()==null) || ( c.getCodeSiret().trim().length() ==0) ||
			(c.getAddress()==null) )
		{
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		
		EntityManager em = StartListener.createEntityManager();
		
		// Recupere le Merchant a partir du User de la session et check si c'est bien ce Merchant qui est connecte
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
			
		
		// Check si le name ou le codeSiret du Commerce a ajouter existe deja dans la BDD
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
			
    	
    	// Attribue le Merchant connecte au Commerce a ajouter
    	c.setMerchant(merchant);
    	
    	// Persiste le Commerce a ajouter dans la BDD
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
	* Supprime un Commerce du Merchant (User connecte) recupere via son id
	* TODO supprimer toutes les instances de produits liees au Commerce lors de la suppression de la fiche
	*/
	@GetMapping(path="/my-commerces/remove/{id}", produces = "application/json")
	// TODO Front: prevenir que la suppression de la fiche entrainera la suppression des produits en vente
	public RestResponse<Commerce> removeCommerce(@PathVariable(name="id") int id, HttpSession session, Locale locale, Model model)
    {
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		// Recupere le Merchant a partir du User de la session et check si c'est bien ce Merchant qui est connecte
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
			
		
		// Check si l'id du Commerce a supprimer existe dans la BDD	
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
	* Liste les ProductTemplates du Merchant (User connecte)
	*/
	@GetMapping(path="/my-product-templates", produces = "application/json")
	public RestResponse<List<ProductTemplate>> getProductTemplates(HttpSession session)
    {
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<ProductTemplate>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
    	EntityManager em = StartListener.createEntityManager();
		
    	
    	// Recupere le Merchant a partir du User de la session et check si c'est bien ce Merchant qui est connecte
    	Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class,  ((User)session.getAttribute("user")).getId() , em);
			System.out.println(merchant);
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
    	
    	// Recupere la liste des ProductTemplates du Merchant via son name
		try 
		{	
			listProductsTemplates = dao.findBySomethingNamed(ProductTemplate.class, "merchant", "name", merchant.getName(), em);
			for(ProductTemplate ptTmp : listProductsTemplates)
				System.out.println(ptTmp);
			
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
	* Recupere un ProductTemplate via son id
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
    	
    	// Recupere le ProductTemplate dont l'id est passe en parametre
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
	* Ajoute un nouveau ProductTemplate pour le Merchant (User connecte)
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
		
		
		// Recupere le Merchant a partir du User de la session et check si c'est bien ce Merchant qui est connecte
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
				
		
		// Check si l'externalCode ou le name existe deja et qu'il ne s'agit pas d'une modification
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
		
    	// TODO Front: prevenir que le produit existe deja et demander s'il s'agit d'une modification
    	if( (pt_tmp!=null) && (!modif) )
    	{
    		em.close();
    		return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Operation canceled: Product template already exists");	
    	}
		
    	
    	// Attribue le Merchant connecte au Commerce a ajouter
    	pt.setMerchant(merchant);
    	
    	// Persiste le Commerce a ajouter dans la BDD
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
	* Supprime un ProductTemplate du Merchant (User connecte) recupere via son id
	* TODO supprimer toutes les instances de produits lors de la suppression de la fiche
	*/
	@GetMapping(path="/my-product-templates/remove/{id}", produces = "application/json")
	// TODO Front: prevenir que la suppression de la fiche entrainera la suppression des produits en vente
	public RestResponse<ProductTemplate> removeProductTemplate(@PathVariable(name="id") int id, HttpSession session, Locale locale, Model model)
    {
		// Check si le User de la session est Merchant
		if(!checkAllowToDoThat(session))
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to do this operation");
		
		
		EntityManager em = StartListener.createEntityManager();
		
		
		// Recupere le Merchant a partir du User de la session et check si c'est bien ce Merchant qui est connecte
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
			
		
		// Check si l'id du ProductTemplate a supprimer existe dans la BDD	
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
