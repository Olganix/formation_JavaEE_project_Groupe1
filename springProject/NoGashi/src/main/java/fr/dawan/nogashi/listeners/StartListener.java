package fr.dawan.nogashi.listeners;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartListener implements ServletContextListener 
{
	private static EntityManagerFactory emf = null;

	
    public StartListener() { }

    public void contextInitialized(ServletContextEvent sce)  
    { 
    	emf = Persistence.createEntityManagerFactory("nogashi");
    }
	
    public void contextDestroyed(ServletContextEvent sce)  
    { 
    	emf.close();
    }

    public static EntityManager createEntityManager() 
    {
    	return (emf!=null) ? emf.createEntityManager() : null;
    }
}
