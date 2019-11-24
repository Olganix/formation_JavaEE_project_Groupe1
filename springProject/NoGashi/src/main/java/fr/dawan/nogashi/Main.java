package fr.dawan.nogashi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.enums.UserRole;

public class Main 
{
	public static EntityManager em;
	public static EntityTransaction et;
	
	
	public static void main(String[] args) 
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("nogashi");
		em = emf.createEntityManager();
		et = em.getTransaction();
		
		
		setupDataBase();
		
		em.close();
		emf.close();	
	}

	
	public static void setupDataBase()
	{
		User a = new User("admin", "admin@noghasi.org", "admin", UserRole.ADMIN, false);
		User m = new User("merchantTest", "merchant@noghasi.org", "toto", UserRole.MERCHANT, false);
		User u = new User("userTest", "user@noghasi.org", "toto", UserRole.INDIVIDUAL, false);
		User ass = new User("associationTest", "associationt@noghasi.org", "toto", UserRole.ASSOCIATION, false);
		
		try
		{
			et.begin();
			
			em.persist(a);
			em.persist(m);
			em.persist(u);
			em.persist(ass);
			
			et.commit();
			
		} catch (Exception e) {
			et.rollback();					//undo if troubles
			e.printStackTrace();		
		}
		
		
	}
}
