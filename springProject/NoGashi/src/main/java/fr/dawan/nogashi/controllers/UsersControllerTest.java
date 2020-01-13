package fr.dawan.nogashi.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.listeners.StartListener;
import fr.dawan.nogashi.tools.Common;


/**
 * Controller de test
 * TODO supprimer
 * @author Admin stagiaire
 *
 */
@WebServlet("/userscontroler")
public class UsersControllerTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public UsersControllerTest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<User> listUsers = new ArrayList<User>();
		
		EntityManager em = StartListener.createEntityManager();
		GenericDao dao = new GenericDao();
		
		String id_str = (String) request.getParameter("id");
		Integer id = Common.parseToInt(id_str);
		
		
		System.out.println("******************* id :"+ id);
		
		try 
		{	
			if(id==null)
			{
				listUsers = dao.findAll(User.class, em);				//a cause du true, on a des soucis sur le sysout (on a aussi un soucis dans le resulta du find )=> du coups Eager. mais si l'on veut etre en lazy , il faudrait mettre false.
			}else {
				User u = dao.find(User.class, id, em);
				if(u!=null)
					listUsers.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(listUsers!=null)
		{
			for(User u : listUsers)
				System.out.println(u);
		}
		
		request.setAttribute("listUsers", listUsers);
		
		request.getRequestDispatcher("/WEB-INF/Users.jsp").forward(request, response);
		
		em.close();
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
