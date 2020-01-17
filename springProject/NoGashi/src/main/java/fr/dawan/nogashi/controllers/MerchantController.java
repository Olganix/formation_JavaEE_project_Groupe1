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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.Commerce;
import fr.dawan.nogashi.beans.Merchant;
import fr.dawan.nogashi.beans.Product;
import fr.dawan.nogashi.beans.ProductTemplate;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.SchedulerWeek;
import fr.dawan.nogashi.beans.ShoppingCartByCommerce;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;


///commerce/{id_c}/carts									// ShoppingCartByCommerce pas encore payé, ca permet de filtrer les products reservé
// /commerce/{id_c}/cart/{id}
// /commerce/{id_c}/commands								// ShoppingCartByCommerce payée du jours
// /commerce/{id_c}/commands/historic						// historic ShoppingCartByCommerce payée (avant aujourd'hui).
// /commerce/{id_c}/command/{id}
// A--- /commerce/{id_c}/command/{id}/bill					// demande la facture. => directement en front a partir de l'url precedente.


// quand une commande est payée => envois un mail au merchant (ou si possible le commerce) . plsu tard si il reste du temsp , (donc on ne va pas faire) notifications : créer une table , reference a un utilisaterur + message (quand recup message , suprression de le ligne) + process recuperation url /merchant/notifications.
// quand on cherche la liste des produits (par example un utilisateur qui regarde des trucs), on met a jours les status de promotion et invendu.
// pour le "OU NOUS TROUVER" => fichier statique (idée d'un cache possible) avec nom du commerce + position Gps.



@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
@RequestMapping(path = "/merchant")
public class MerchantController 
{
	@Autowired
	GenericDao dao;
	
	// Retourne Merchant si le User de la session est un Merchant et qu'il est bien dans la Bdd
	public Merchant checkAllowToDoThat(HttpSession session, EntityManager em)
	{
		User u = (User)session.getAttribute("user");
		System.out.println("MerchantControllerAngular.checkAllowToDoThat : "+ u);
		
		
		Merchant merchant = null;
		
		if( (u!=null) && (u.getRole() == UserRole.MERCHANT) )
		{
			// Recupere le Merchant a partir du User de la session
	    	// et aussi check si c'est bien ce Merchant qui est connecte
			try {
				merchant = dao.find(Merchant.class, ((User)session.getAttribute("user")).getId(), em);
			} catch (Exception e) {
				merchant = null;
				e.printStackTrace();										// todo si il reste du temps : remplacer les printStack partout par le logger (plus propre, sinon on se fait engeuler par sonarCube).
			}
		}
		return merchant;
	}
	
	
	
	/*****************************************************************************************
	*										getMerchant										 * 
	*****************************************************************************************
	* 
	* Recupere le User (Merchant) de la session via son id 
	*/
	@GetMapping(path="", produces = "application/json")
	public RestResponse<Merchant> getMerchant(HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		em.close();
		return new RestResponse<Merchant>(RestResponseStatus.SUCCESS, merchant);
    }
	
	
	
	
	
	
	
	
	/*****************************************************************************************
	*										updateMerchant									 * 
	*****************************************************************************************
	*
	* Modifie les infos du Merchant connecte
	*/
	@PostMapping(path="/update", consumes = "application/json", produces = "application/json")
	public RestResponse<Merchant> updateMerchant(@RequestBody Merchant m, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		// Verifie les champs modifies du formulaire
		boolean isModifed = false;
		if((merchant.getCodeSiren()==null) && (m.getCodeSiren()!=null) )					// specificité : le code siren ne se modifie qu'une seule fois.
		{
			merchant.setCodeSiren(m.getCodeSiren());
			isModifed = true;
		}
		

		if( (m.getEmail() != null) && (!merchant.getEmail().equals(m.getEmail())) )									// TODO regex
		{
			merchant.setEmail(m.getEmail());
			isModifed = true;
		}
		
		if( (!merchant.getAvatarFilename().equals(m.getAvatarFilename())) )	
		{
			merchant.setAvatarFilename(m.getAvatarFilename());
			isModifed = true;
		}
		
		if( (!merchant.getPhoneNumber().equals(m.getPhoneNumber())) )												// TODO regex
		{
			merchant.setPhoneNumber(m.getPhoneNumber());
			isModifed = true;
		}
		if( (!merchant.getPhoneNumber2().equals(m.getPhoneNumber2())) )													// TODO regex
		{
			merchant.setPhoneNumber2(m.getPhoneNumber2());
			isModifed = true;
		}
		
		if( (m.getAddress() != null) && (!merchant.getAddress().equals(m.getAddress())) )	
		{
			merchant.setAddress(m.getAddress());
			isModifed = true;
		}
		
		if( (m.getCodeIBAN() != null) && (!merchant.getCodeIBAN().equals(m.getCodeIBAN())) )									// TODO regex
		{
			merchant.setCodeIBAN(m.getCodeIBAN());
			isModifed = true;
		}
		if( (m.getCodeBic() != null) && (!merchant.getCodeBic().equals(m.getCodeBic())) )									// TODO regex
		{
			merchant.setCodeBic(m.getCodeBic());
			isModifed = true;
		}
		
		// Exemple
		//if( (m.getCodeSiren() != null) && (merchant.getCodeSiren().equals(m.getCodeSiren())) )				// attention a ce que l'on veut que ce soit expres a null (si il veut supprimer l'info)
		//{
		//	merchant.setCodeSiren(m.getCodeSiren());
		//	isModifed = true;
		//}
		
		
		
		if(isModifed)
		{
			try 
			{
				dao.saveOrUpdate(merchant, em, false);
				System.out.println("update user (merchant): "+ merchant.getName() +" email:"+ merchant.getEmail());
				
			} catch (Exception e1) {
				e1.printStackTrace();
				
				em.close();
				return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 1, "Error: on Update Operation");
			}
		}
		
		em.close();
		return new RestResponse<Merchant>(RestResponseStatus.SUCCESS, null);
    }
	
	
	
	/*****************************************************************************************
	*								deactivateMerchant										* 
	*****************************************************************************************
	*
	* Desactive le compte Merchant (User connecte)
	* TODO NE PAS FAIRE CETTE FONCTION DE SUITE.
	*/
	@GetMapping(path="/remove", produces = "application/json")
	public RestResponse<Merchant> deactivateMerchant(HttpSession session, Locale locale, Model model)
    {	
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		// TODO faire une function de suppression de un commerce, faire un for.
		// TODO faire une function qui supprime un ProductTemplate, faire un for.
		//		-detachement des commerces, detachement des products (il n'y a plus reference) detachement du merchant.
		
    	
    	// Supprime le Merchant
		try 
		{
			System.out.println(merchant.getName() + " account removed");
			dao.remove(merchant, em, false);
			
			// Met le User de la session a null, pour logout.
	    	session.setAttribute("user", null);
	    	
		} catch (Exception e1) {
			e1.printStackTrace();
			
			em.close();
			return new RestResponse<Merchant>(RestResponseStatus.FAIL, null, 1, "Error: on Remove Operation");
		}
		
		em.close();
		return new RestResponse<Merchant>(RestResponseStatus.SUCCESS, null);
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
	*										getCommerces									 * 
	*****************************************************************************************
	* 
	* Liste les commerces du Merchant (User connecte)
	*/
	@GetMapping(path="/commerces", produces = "application/json")					// Pour Joffrey , c'est l'exemple a suivre : note : pas pour le test.
	public RestResponse<List<Commerce>> getCommerces(HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
    	/*
    	EntityGraph<Commerce> graph = em.createEntityGraph(Commerce.class);				// todo supprimer test.
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");	
    	*/
		
    	// Recupere la liste des Commerce du Merchant via son name
		List<Commerce> listCommerces = new ArrayList<Commerce>();
		try 
		{	
			listCommerces = merchant.getCommerces();
			for(Commerce cTmp : listCommerces)
			{
				System.out.println(cTmp);
				
				SchedulerWeek sw = cTmp.getSchedulerWeek();
				for(SchedulerWeek swTmp : sw.getGroup())				//Todo faire mieux pour charger les group, et eviter la merde au niveau de Jsckon
					System.out.println(swTmp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<Commerce>>(RestResponseStatus.SUCCESS, listCommerces);
    }
	
	
	
	
	
	/*****************************************************************************************
	*										addCommerce										 * 
	*****************************************************************************************
	*
	* Ajoute un nouveau Commerce pour le Merchant (User connecte)
	*/
	@PostMapping(path="/commerce/addOrUdapte", consumes = "application/json", produces = "application/json")
	public RestResponse<Commerce> addCommerce(@RequestBody Commerce c, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		// Check si tous les champs du formulaire sont null ou si le name, le codeSiret ou l'adresse est null
		if(	(c==null) || 
			(c.getName()==null) || ( c.getName().trim().length() ==0) ||
			(c.getCodeSiret()==null) || ( c.getCodeSiret().trim().length() ==0) ||
			(c.getAddress()==null) )
		{
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}

		
		
		
		
		Commerce c_bdd = null;
		boolean isUnique = true;
		try 
    	{
			if(c.getId()==0)						// c'est un add que l'on fait.
			{
				// Check si le codeSiret du Commerce existe deja dans la BDD
	    		List<Commerce> listCommerces = dao.findNamed(Commerce.class, "codeSiret", c.getCodeSiret(), em);
	    		if(listCommerces.size()!=0)
	    			c_bdd = listCommerces.get(0);
		    	
	    		if(c_bdd!=null)
		    	{
		    		em.close();
		    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Commerce already exists");
		    	}
	    		
	    		
	    		//check si name + uniqueIdName est completement unique, ca aide l'utilisateur a s'y retrouver.
	    		listCommerces = dao.findNamed_Double(Commerce.class, "name", c.getName(), "uniqueIdName", c.getUniqueIdName(), em);
	    		if(listCommerces.size()!=0)
	    			isUnique = false;
	    		
				
				c_bdd = c;
				// Attribue le Merchant connecte au Commerce a ajouter
				c_bdd.setMerchant(merchant);
				
			}else{									// update
				
				c_bdd = dao.find(Commerce.class, c.getId(), "merchant", merchant, em);
				if(c_bdd==null)
		    	{
		    		em.close();
		    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Commerce not exists");
		    	}
				
				
				
				c_bdd.setCodeSiret(c.getCodeSiret());
				// Todo completer en s'inspirant updateMerchantAccount
			}
		
			
			// Persiste le Commerce a ajouter dans la BDD
			System.out.println("commerce : "+ c_bdd.getName() +" siret:"+ c_bdd.getCodeSiret() +" "+ (((c.getId()==0)) ? "created" : "updated"));
			dao.saveOrUpdate(c_bdd, em, false);
			
			
			
    	} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: add or update fail");
		}
		
		
		em.close();
		if(isUnique)
			return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, c_bdd);
		else
			return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, c_bdd, 6, "Warning: Commerce name + uniqueIdName not unique, and taht could add confusion for users.");
    }
	
	
	
	/*****************************************************************************************
	*										removeCommerce									* 
	*****************************************************************************************
	*
	* Supprime un Commerce du Merchant (User connecte) recupere via son id
	*/
	@GetMapping(path="/commerce/{id}/remove", produces = "application/json")
	// TODO Front: prevenir que la suppression de la fiche entrainera la suppression des produits en vente
	public RestResponse<Commerce> removeCommerce(@PathVariable(name="id") int id, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		try 
		{
			Commerce c = dao.find(Commerce.class, id, "merchant", merchant, em);				//check si le merchant a le droit de supprimer ce commerce.			
			if(c!=null)
			{
				c.setMerchant(null);				// detacher le merchant
				
				
				// supprimer les products qui ne sont pas affecté a un des shoppingCarts.
				List<Product> listToDelete = new ArrayList<Product>();
				for(Product p : c.getProducts())
				{
					if(p.getShoppingCart() != null)
					{
						p.getShoppingCart().removeProduct(p);
						p.getShoppingCart().setCommerce(null);
						listToDelete.add(p);
					}else {
						dao.remove(p, em);											// si le commerce n'existe plus il faut detruire le product.
					}
				}
				
				for(Product p : listToDelete)
					p.removeCommerces(c);
				
				for(ShoppingCartByCommerce scbc : c.getShoppingCartByCommerces())
					scbc.setCommerce(null);
				
				
				// Supprime le Commerce
				dao.remove(Commerce.class, id, em, false);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			
			em.close();
    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: Remove Fail");
		}
		
		em.close();
		return new RestResponse<Commerce>(RestResponseStatus.SUCCESS, null);
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
	*										getMyProductTemplates							* 
	*****************************************************************************************
	*
	* Liste les ProductTemplates du Merchant (User connecte)
	*/
	@GetMapping(path="/productTemplates", produces = "application/json")
	public RestResponse<List<ProductTemplate>> getProductTemplates(HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<List<ProductTemplate>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
	
		
    	// Recupere la liste des ProductTemplates du Merchant via son name
		List<ProductTemplate> listProductTemplates = new ArrayList<ProductTemplate>();
		try 
		{
			listProductTemplates = merchant.getProductTemplates();
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
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		
    	// Recupere le ProductTemplate dont l'id est passe en parametre
		ProductTemplate productTemplate = null;
		try 
		{
			// Todo voir si l'on a pas besoin de graph pour les productDetails
			productTemplate = dao.find(ProductTemplate.class, id, "merchant", merchant, em);
		} catch (Exception e) {
			e.printStackTrace();
		
			em.close();
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: getting ProductTemplate operation");
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
	@PostMapping(path="/productTemplate/addOrUpdate", consumes = "application/json", produces = "application/json")
	public RestResponse<ProductTemplate> addProductTemplate(@RequestBody ProductTemplate pt, HttpSession session, Locale locale, Model model, boolean modif)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
		
		
		// Check si tous les champs du formulaire sont null ou si le name, l'externalCode ou le prix est null
		if(	(pt==null) || 
			(pt.getName()==null) || ( pt.getName().trim().length() ==0) ||
			(Double.valueOf(pt.getPrice())==null) )													//TODO replacer les double pour Double dans les beans pour eviter les exception sur les null.
			//TODO completer
		{
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: Not enough arguments");
		}
		
		
		
		ProductTemplate pt_bdd = null;
		boolean isUnique = true;
		try 
		{
			if(pt.getId()==0)						// c'est un add que l'on fait.
			{
	    		//check si name est completement unique, ca aide l'utilisateur a s'y retrouver.
	    		List<ProductTemplate> lpt = dao.findNamedBySomething(ProductTemplate.class, "name", pt.getName(), "merchant", merchant, em);
	    		if(lpt.size()!=0)
	    			isUnique = false;
	    		
				
	    		pt_bdd = pt;
				// Attribue le Merchant connecte au ProductTemplate a ajouter
	    		pt_bdd.setMerchant(merchant);
				
			}else{									// update
				
				pt_bdd = dao.find(ProductTemplate.class, pt.getId(), "merchant", merchant, em);
				if(pt_bdd==null)
		    	{
		    		em.close();
		    		return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: ProductTemplate not exists");
		    	}
				
				
				
				pt_bdd.setDescription(pt.getDescription());
				// Todo completer en s'inspirant updateMerchantAccount
			}
		
			
			// Persiste le Commerce a ajouter dans la BDD
			System.out.println("productTemplate: "+ pt_bdd.getExternalCode() +" name:"+ pt_bdd.getName() +" "+ (((pt.getId()==0)) ? "created" : "updated"));
			dao.saveOrUpdate(pt_bdd, em, false);
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
			
			em.close();
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: on add or update ProductTemplate");
		}
		
		em.close();
		if(isUnique)
			return new RestResponse<ProductTemplate>(RestResponseStatus.SUCCESS, pt_bdd);
		else
			return new RestResponse<ProductTemplate>(RestResponseStatus.SUCCESS, pt_bdd, 6, "Warning: ProductTemplate name not unique, and that could add confusion for users.");
    }
	
	
	
	

	/*****************************************************************************************
	*										removeProductTemplate										* 
	*****************************************************************************************
	*
	* Supprime un ProductTemplate du Merchant (User connecte) recupere via son id
	* TODO supprimer toutes les instances de produits lors de la suppression de la fiche
	*/
	@GetMapping(path="/productTemplate/{id}/remove", produces = "application/json")
	public RestResponse<ProductTemplate> removeProductTemplate(@PathVariable(name="id") int id, HttpSession session, Locale locale, Model model)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
	
		
		try 
		{
			ProductTemplate pt = dao.find(ProductTemplate.class, id, "merchant", merchant, em);
			if(pt!=null)
			{
				merchant.removeProductTemplate(pt);
				for(Commerce c : pt.getCommerces())
					pt.removeCommerces(c);
				
				dao.saveOrUpdate(merchant, em);							//note: commerces sont en cascade all dans Merchant, donc normalement les Commerces sont aussi mise a jours. 
				
				//Todo supprimer seulement les products qui ne sont pas associés a une shoppingcart
				
				// Supprime le ProductTemplate
				dao.remove(ProductTemplate.class, id, em, false);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		
			em.close();
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: on remove ProductTemplate");
		}
		
		em.close();
		return new RestResponse<ProductTemplate>(RestResponseStatus.SUCCESS, null);
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
	* Todo doc
	*/
	@GetMapping(path="/commerce/{id_c}/products", produces = "application/json")
	public RestResponse<List<Product>> getProductsByCommerce(@PathVariable(name = "id_c") int id_c, HttpSession session)
    {
		EntityManager em = StartListener.createEntityManager();
		
		// Check si le User de la session est Merchant
		Merchant merchant = checkAllowToDoThat(session, em);
		if(merchant==null)
		{
			em.close();
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 5, "Error: User is not allowed to perform this operation");
		}
	
		List<Product> listProducts = new ArrayList<Product>();
		try 
		{	
			Commerce c = dao.find(Commerce.class, id_c, "merchant", merchant, em);
			if(c==null)
			{
				em.close();
				return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 1, "Error: commerce not found operation");
			}
			
			listProducts = dao.findBySomething(Product.class, "commerce", c, em); // TODO exclure les Products des ShoppingCart
			
		} catch (Exception e) {
			e.printStackTrace();
			
			em.close();
			return new RestResponse<List<Product>>(RestResponseStatus.FAIL, null, 1, "Error: getting products by commerce operation");
		}
		
		em.close();
		return new RestResponse<List<Product>>(RestResponseStatus.SUCCESS, listProducts);
    }
	
	//TODO 
	// /commerce/{id_c}/product/{id}						// voir le produit seul (different du productTemplate) (non prioritaire)
	// /commerce/{id_c}/product/{id}/remove					//  (non prioritaire)
	// /commerce/{id_c}/product/{id}/{status}				// passage : "promotion" -> "unsold" -> "destroyed" ?.
	// /commerce/{id_c}/product/addOrUpdate					// personnalisation du product par rapport au productTemplate (non prioritaire), ex : "brulé", "carbonnisé"
	// /commerce/{id_c}/productTemplate/{id}/{quantity}		// specifit la quantité voulu de product (pour un productTemplate données), MAIS cela ne concerne pas les products reservé dans les panier des utilisateurs (PAS TOUCHE).
	
}
