package fr.dawan.nogashi.controlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.ProductTemplate;
import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;
import fr.dawan.nogashi.listeners.StartListener;



@RestController
@CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
public class ProductsTemplatesControllerAngular 
{
	private static final Logger logger = LoggerFactory.getLogger(ProductsTemplatesControllerAngular.class);
       
	@Autowired
	GenericDao dao;
	
	
	/*****************************************************************************************
	*										getProductsTemplates										 * 
	*****************************************************************************************/
	@RequestMapping(path="/getProductsTemplates", produces = "application/json")
	public RestResponse<List<ProductTemplate>> getProducts()
    {
		System.out.println("salut");
    	EntityManager em = StartListener.createEntityManager();
		
    	List<ProductTemplate> listProductsTemplates = new ArrayList<ProductTemplate>();
		
		try 
		{	
			listProductsTemplates = dao.findAll(ProductTemplate.class, em, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		System.out.println(listProductsTemplates);
		return new RestResponse<List<ProductTemplate>>(RestResponseStatus.SUCCESS, listProductsTemplates);
    }
	
	
	
	
	/*****************************************************************************************
	*										addProductTemplate										 * 
	*****************************************************************************************/
	@PostMapping(path="/addProductTemplate", produces = "application/json")
	//test : http://localhost:8080/nogashi/
	public RestResponse<ProductTemplate> addProductTemplate(@RequestBody ProductTemplate pt, User u, HttpSession session, Locale locale, Model model)
    {
		System.out.println("fiche produit : " + pt + " ; user : " + u);
		
		
		if( (u.getRole() != UserRole.MERCHANT) || (u.getRole() != UserRole.ADMIN))
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: Role no good");
		
		
		
		logger.info("Welcome home! The client locale is {}.", locale);		//todo better use log for server side.
		//request.setCharacterEncoding("UTF-8");			//todo check if need equivalent for this, todo also check crypt password + utf8 saved in bdd are not good on read again (instead of manually create it and put it in bdd).
		
		
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
	public RestResponse<ProductTemplate> deleteProductTemplate(@RequestBody ProductTemplate pt, User u, HttpSession session, Locale locale, Model model)
    {
		System.out.println("fiche produit : " + pt + " ; user : " + u);
		
		
		if( (u.getRole() != UserRole.MERCHANT) || (u.getRole() != UserRole.ADMIN))
			return new RestResponse<ProductTemplate>(RestResponseStatus.FAIL, null, 1, "Error: Role no good");
		
		
		
		logger.info("Welcome home! The client locale is {}.", locale);		//todo better use log for server side.
		//request.setCharacterEncoding("UTF-8");			//todo check if need equivalent for this, todo also check crypt password + utf8 saved in bdd are not good on read again (instead of manually create it and put it in bdd).
		
		
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
