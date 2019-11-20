package fr.dawan.nogashi.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.dawan.nogashi.beans.DbObject;

public class GenericDao 
{

	
	public <T extends DbObject> void saveOrUpdate(T elm, EntityManager em, boolean closeConnection) throws Exception
	{
		EntityTransaction et = em.getTransaction();
		
		try 
		{
			et.begin();
			
			boolean isNewEntry = (elm.getId()==0);
			if(isNewEntry)
				em.persist(elm);
			else 
				em.merge(elm);
			
			et.commit();
			
			if(isNewEntry)
				System.out.println("Nouvelle entree : "+ elm.getId());
			
		} catch (Exception e) {
			
			et.rollback();
			throw e;
			
		} finally {
			if(closeConnection)
				em.close();
		}
	}
	
	public <T extends DbObject> void remove(T elm, EntityManager em, boolean closeConnection) throws Exception
	{
		EntityTransaction et = em.getTransaction();
		
		try 
		{
			et.begin();
			
			if(elm.getId()!=0)
				em.remove(elm);
			
			et.commit();
			
		} catch (Exception e) {
			
			et.rollback();
			throw e;
			
		} finally {
			if(closeConnection)
				em.close();
		}
	}
	
	public <T extends DbObject> T find(Class<T> tClass, int id, EntityManager em, boolean closeConnection) throws Exception
	{
		if(id==0)
			return null;
				
		T elm = em.find( tClass, id);
		
		if(closeConnection)
			em.close();
	
		return elm;
	}
	
	
	public <T extends DbObject> List<T> findNamed(Class<T> tClass, String column, String name, EntityManager em, boolean closeConnection) throws Exception
	{
		TypedQuery<T> query = em.createQuery("SELECT entity from "+ tClass.getName() + " as entity where "+ column +"=:name", tClass);
		query.setParameter("name", name);
		List<T> result = query.getResultList();
		
		if(closeConnection)
			em.close();
	
		return result;
		
	}
	
	public <T extends DbObject> List<T> findAll(Class<T> tClass, EntityManager em, boolean closeConnection) throws Exception
	{		
		TypedQuery<T> query = em.createQuery("SELECT entity from "+ tClass.getName() + " as entity", tClass);
		List<T> result = query.getResultList();
		
		if(closeConnection)
			em.close();
	
		return result;
	}
	
	public <T extends DbObject> long count(Class<T> tClass, EntityManager em, boolean closeConnection) throws Exception
	{		
		Query query = em.createQuery("SELECT COUNT(entity.id) from "+ tClass.getName() + " as entity");
		//query.setHint("org.hibernate.cacheable", true);
		long result = (long)query.getSingleResult();
		
		if(closeConnection)
			em.close();
	
		return result;
	}
	
	
	public <T extends DbObject> List<T> findPartial(Class<T> tClass, EntityManager em, boolean closeConnection, int startindex, int nbElements) throws Exception
	{		
		TypedQuery<T> query = em.createQuery("SELECT entity from "+ tClass.getName() + " as entity", tClass);
		
		List<T> result = query.setFirstResult(startindex).setMaxResults(startindex + nbElements).getResultList();
		
		if(closeConnection)
			em.close();
	
		return result;
	}
}
