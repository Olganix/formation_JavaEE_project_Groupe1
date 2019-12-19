package fr.dawan.nogashi.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
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
	
	
	
	public <T extends DbObject> void removeAll(Class<T> clazz, EntityManager em, boolean closeConnection) {

		
		EntityTransaction et = em.getTransaction();
		
		try 
		{
			et.begin();
			
			Query query = em.createQuery("Delete FROM " + clazz.getName());
			query.executeUpdate();
			
			et.commit();
			
		} catch (Exception e) {
			
			et.rollback();
			throw e;
			
		} finally {
			if(closeConnection)
				em.close();
		}
	}
	
	
	
	public <T extends DbObject> T find(Class<T> tClass, int id, EntityManager em, EntityGraph<T> graph, boolean closeConnection) throws Exception
	{
		if(id==0)
			return null;
		
		T elm = null;
		if(graph!=null)
		{
			Map<String, Object> hints = new HashMap<String, Object>();
			hints.put("javax.persistence.loadgraph", graph);
			elm = em.find(tClass, id, hints);
		}else {
			elm = em.find( tClass, id);
		}
		
		if(closeConnection)
			em.close();
	
		return elm;
	}
	
	
	
	public <T extends DbObject> List<T> findNamed(Class<T> tClass, String column, String name, EntityManager em, boolean strictClass, EntityGraph<T> graph, boolean closeConnection) throws Exception
	{
		TypedQuery<T> query = em.createQuery("SELECT entity from "+ tClass.getName() + " as entity WHERE "+ column +"=:name "+ ((strictClass) ? ("AND TYPE(entity) = "+ tClass.getName()) : ""), tClass);
		query.setParameter("name", name);
		
		if(graph!=null)
			query.setHint("javax.persistence.loadgraph", graph);				//for loading sub class (class attribut using class).		// methode 5 : https://thoughts-on-java.org/5-ways-to-initialize-lazy-relations-and-when-to-use-them/
		
		List<T> result = query.getResultList();
		
		if(closeConnection)
			em.close();
	
		return result;
		
	}
	
	
	
	
	public <T extends DbObject> List<T> findAll(Class<T> tClass, EntityManager em, boolean strictClass, EntityGraph<T> graph, boolean closeConnection) throws Exception
	{		
		TypedQuery<T> query = em.createQuery("SELECT entity from "+ tClass.getName() + " as entity "+ ((strictClass) ? ("WHERE TYPE(entity) = "+ tClass.getName()) : ""), tClass);		//to have only User , and not Merchant and all child classes of User.
		if(graph!=null)
			query.setHint("javax.persistence.loadgraph", graph);				//for loading sub class (class attribut using class).		// methode 5 : https://thoughts-on-java.org/5-ways-to-initialize-lazy-relations-and-when-to-use-them/
		List<T> result = query.getResultList();
		
		if(closeConnection)
			em.close();
	
		return result;
	}
	
	
	
	public <T extends DbObject> long count(Class<T> tClass, EntityManager em, boolean strictClass, boolean closeConnection) throws Exception
	{		
		Query query = em.createQuery("SELECT COUNT(entity.id) from "+ tClass.getName() + " as entity "+ ((strictClass) ? ("WHERE TYPE(entity) = "+ tClass.getName()) : ""));
		long result = (long)query.getSingleResult();
		
		if(closeConnection)
			em.close();
	
		return result;
	}
	
	
	public <T extends DbObject> List<T> findPartial(Class<T> tClass, EntityManager em, int startindex, int nbElements, boolean strictClass, EntityGraph<T> graph, boolean closeConnection) throws Exception
	{		
		TypedQuery<T> query = em.createQuery("SELECT entity from "+ tClass.getName() + " as entity "+ ((strictClass) ? ("WHERE TYPE(entity) = "+ tClass.getName()) : ""), tClass);
		if(graph!=null)
			query.setHint("javax.persistence.loadgraph", graph);				//for loading sub class (class attribut using class).		// methode 5 : https://thoughts-on-java.org/5-ways-to-initialize-lazy-relations-and-when-to-use-them/
		
		List<T> result = query.setFirstResult(startindex).setMaxResults(startindex + nbElements).getResultList();
		
		if(closeConnection)
			em.close();
	
		return result;
	}
	
	//---------------------------------------

	
	
	public <T extends DbObject> void saveOrUpdate(T elm, EntityManager em) throws Exception { saveOrUpdate(elm, em, false); }
	public <T extends DbObject> void remove(T elm, EntityManager em) throws Exception { remove(elm, em, false); }
	public <T extends DbObject> void removeAll(Class<T> clazz, EntityManager em) throws Exception { removeAll(clazz, em, false); }	
	public <T extends DbObject> T find(Class<T> tClass, int id, EntityManager em, EntityGraph<T> graph) throws Exception { return find(tClass, id, em, graph, false); }
	public <T extends DbObject> T find(Class<T> tClass, int id, EntityManager em) throws Exception { return find(tClass, id, em, null, false); }
	public <T extends DbObject> List<T> findNamed(Class<T> tClass, String column, String name, EntityManager em, boolean strictClass, EntityGraph<T> graph) throws Exception { return findNamed(tClass, column, name, em, strictClass, graph, false);  }
	public <T extends DbObject> List<T> findNamed(Class<T> tClass, String column, String name, EntityManager em, boolean strictClass) throws Exception { return findNamed(tClass, column, name, em, strictClass, null, false);  }
	public <T extends DbObject> List<T> findNamed(Class<T> tClass, String column, String name, EntityManager em) throws Exception { return findNamed(tClass, column, name, em, false, null, false);  }
	public <T extends DbObject> List<T> findAll(Class<T> tClass, EntityManager em, boolean strictClass, EntityGraph<T> graph) throws Exception { return findAll(tClass, em, strictClass, graph, false); }
	public <T extends DbObject> List<T> findAll(Class<T> tClass, EntityManager em, boolean strictClass) throws Exception { return findAll(tClass, em, strictClass, null, false); }
	public <T extends DbObject> List<T> findAll(Class<T> tClass, EntityManager em) throws Exception { return findAll(tClass, em, false, null, false); }
	public <T extends DbObject> List<T> findPartial(Class<T> tClass, EntityManager em, int startindex, int nbElements, boolean strictClass, EntityGraph<T> graph) throws Exception { return findPartial(tClass, em, startindex, nbElements, strictClass, graph, false); }
	public <T extends DbObject> List<T> findPartial(Class<T> tClass, EntityManager em, int startindex, int nbElements, boolean strictClass) throws Exception { return findPartial(tClass, em, startindex, nbElements, strictClass, null, false); }
	public <T extends DbObject> List<T> findPartial(Class<T> tClass, EntityManager em, int startindex, int nbElements) throws Exception { return findPartial(tClass, em, startindex, nbElements, false, null, false); }
	
}
	
