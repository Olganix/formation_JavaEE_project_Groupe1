package fr.dawan.nogashi.controlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.listeners.StartListener;


//ici ca ne marcha pas encore.

@RestController
@RequestMapping("userscontrolerAngular")
public class UsersControlerAngular {
	private static final long serialVersionUID = 1L;
       
    public UsersControlerAngular() {
        super();
    }

    // Method to test the angular fetch call.
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/getUsers", method= RequestMethod.GET, produces = "application/json")
    public List<User> getUsers() 
    {
    	List<User> listUsers = new ArrayList<User>();
		
		EntityManager em = StartListener.createEntityManager();
		GenericDao dao = new GenericDao();
		
		try 
		{	
			listUsers = dao.findAll(User.class, em, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		em.close();
		
		return listUsers;
    }
}
