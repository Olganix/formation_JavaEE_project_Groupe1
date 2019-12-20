package fr.dawan.nogashi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.mindrot.jbcrypt.BCrypt;

import fr.dawan.nogashi.beans.ProductTemplate;
import fr.dawan.nogashi.beans.Merchant;
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
		User a = new User("admin", "admin@noghasi.org", BCrypt.hashpw("admin", BCrypt.gensalt()), UserRole.ADMIN, true); a.setEmailValid(true);
		Merchant m = new Merchant(new User("merchantTest", "merchant@noghasi.org", BCrypt.hashpw("toto", BCrypt.gensalt()), UserRole.MERCHANT, true)); m.setEmailValid(true);
		User u = new User("userTest", "user@noghasi.org", BCrypt.hashpw("toto", BCrypt.gensalt()), UserRole.INDIVIDUAL, true); u.setEmailValid(true);
		User ass = new User("associationTest", "associationt@noghasi.org", BCrypt.hashpw("toto", BCrypt.gensalt()), UserRole.ASSOCIATION, true); ass.setEmailValid(true);
		
		ProductTemplate pt = new ProductTemplate("orange", "orange", "136511", true, 15.1, 10.1); pt.setMerchant(m);
		ProductTemplate pt2 = new ProductTemplate("banane", "banane", "136512", true, 150.2, 100.2); pt2.setMerchant(m);
		ProductTemplate pt3 = new ProductTemplate("fraise", "fraise", "136513", true, 1.3, 0.3); pt3.setMerchant(m);
	
		try
		{
			et.begin();
			
			em.persist(a);
			em.persist(m);
			em.persist(u);
			em.persist(ass);
			em.persist(pt);
			em.persist(pt2);
			em.persist(pt3);
			
			et.commit();
			
		} catch (Exception e) {
			et.rollback();					//undo if troubles
			e.printStackTrace();		
		}
		
		
	}
}
