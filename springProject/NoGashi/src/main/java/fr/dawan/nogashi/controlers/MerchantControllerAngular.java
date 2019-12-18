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
		return ( (u!=null) && (u.getRole() == UserRole.MERCHANT) );
	}
	
	
	/*****************************************************************************************
	*										addCommerce										 * 
	*****************************************************************************************/
	@PostMapping(path="/addCommerce", produces = "application/json")
	//test : http://localhost:8080/nogashi/addCommerce
	public RestResponse<Commerce> signin(@RequestBody Commerce c, HttpSession session, Locale locale, Model model)
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
		
    	
    	
    	
    	
    	Merchant merchant = null;
		try {
			merchant = dao.find(Merchant.class,  ((User)session.getAttribute("user")).getId() , em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(merchant==null)
    	{
    		em.close();
    		return new RestResponse<Commerce>(RestResponseStatus.FAIL, null, 1, "Error: wrong Merchant session information");
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
	*										getCommerces										 * 
	*****************************************************************************************/
	@RequestMapping(path="/getCommerces", produces = "application/json")
	public RestResponse<List<Commerce>> getMerchants(HttpSession session)
    {
		if(!checkAllowToDoThat(session))
			return new RestResponse<List<Commerce>>(RestResponseStatus.FAIL, null, 5, "Error: User don't be allowed on this operation");
		
		
    	EntityManager em = StartListener.createEntityManager();
		
    	List<Commerce> listCommerces = new ArrayList<Commerce>();
		
    	
    	
    	EntityGraph<Commerce> graph = em.createEntityGraph(Commerce.class);
    	graph.addSubgraph("commerceCategories");
    	graph.addSubgraph("productTemplates");
    	graph.addSubgraph("products");
    	
    	
		try 
		{	
			listCommerces = dao.findAll(Commerce.class, em, false, graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<Commerce>>(RestResponseStatus.SUCCESS, listCommerces);
    }
	
}
