package fr.dawan.nogashi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.enums.UserRole;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello world");
		
		//test hibernate-jpa.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("nogashi");
		EntityManager em = emf.createEntityManager();				//il ne peut en avoir qu'un.
		EntityTransaction et = em.getTransaction();
		
		User u = new User("testuser", "testuser@noghasi.org", UserRole.INDIVIDUAL);
		u.setPassword("todoChange");
		User u2 = new User("testuser2", "testuser@noghasi.org", UserRole.INDIVIDUAL);
		u2.setPassword("todoChange");
		
		try 
		{
			et.begin();
			
			em.persist(u);
			em.persist(u2);
			
			et.commit();
			
		} catch (Exception e) {
			
			et.rollback();					//undo if troubles
			e.printStackTrace();		
		}
		
		em.close();
		emf.close();
		
	}

}
