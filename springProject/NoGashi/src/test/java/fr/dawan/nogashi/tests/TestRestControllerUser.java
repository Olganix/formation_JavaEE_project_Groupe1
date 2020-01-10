package fr.dawan.nogashi.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.enums.UserRole;

public class TestRestControllerUser 
{
	private static final String URL = "http://localhost:8080/nogashi/";
	private RestTemplate restTemplate = new RestTemplate();

	//@Autowired			comme on est pas dans le context du server tomcat, du coups on est obligé de l'intancier old way, a la main 
	static GenericDao dao = new GenericDao();
	static EntityManagerFactory emf;
	static EntityManager em;
	
	
	User user_model = new User("aaa", "aaa@toto.fr", "toto", UserRole.INDIVIDUAL, true); 
	
	@BeforeClass
	public static void initClass()
	{
		System.out.println("initClass");
		
		
		emf = Persistence.createEntityManagerFactory("nogashi");
		em = emf.createEntityManager();
	}
	@AfterClass
	public static void destroyClass()
	{
		System.out.println("destroyClass");
		em.close();
		emf.close();
	}
	
	
	@Before
	public void init()
	{
		System.out.println("init");
		try {
			dao.removeAll(User.class, em, false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	@After
	public void after()
	{
		System.out.println("after");
		try {
			dao.removeAll(User.class, em, false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	@Test
	public void ajoutUser_failEmailValidation() 
	{
		//?name="+ user_model.getName() +"&password="+ user_model.getPassword() +"&email="+ user_model.getEmail()
		ResponseEntity<RestResponse> responseHttp = restTemplate.postForEntity(URL +"signin", new User(user_model.getName(), user_model.getEmail(), user_model.getPassword(), user_model.getRole(), user_model.isNewsletterEnabled()), RestResponse.class);
		assertEquals(HttpStatus.OK, responseHttp.getStatusCode());
		RestResponse<User> resp = responseHttp.getBody();
		assertEquals(RestResponseStatus.SUCCESS, resp.getStatus());
		
		responseHttp = restTemplate.getForEntity(URL +"login?name="+ user_model.getName() +"&password="+ user_model.getPassword(), RestResponse.class);
		assertEquals(HttpStatus.OK, responseHttp.getStatusCode());
		resp = responseHttp.getBody();
		assertEquals(RestResponseStatus.FAIL, resp.getStatus());
		assertEquals(2, resp.getErrorCode());	
	}
	
	@Test
	public void ajoutUser() 
	{
		ResponseEntity<RestResponse> responseHttp = restTemplate.getForEntity(URL +"signin?name="+ user_model.getName() +"&password="+ user_model.getPassword() +"&email="+ user_model.getEmail(), RestResponse.class);
		assertEquals(HttpStatus.OK, responseHttp.getStatusCode());
		RestResponse<User> resp = responseHttp.getBody();
		assertEquals(RestResponseStatus.SUCCESS, resp.getStatus());
		
		
		
		
		User u = null;
    	try 
    	{
    		List<User> listUsers = dao.findNamed(User.class, "name", user_model.getName(), em, false);
    		if(listUsers.size()!=0)
    			u = listUsers.get(0);
		} catch (Exception e) {
			u = null;
			e.printStackTrace();
		}
    	assertNotNull(u);
    	
    	responseHttp = restTemplate.getForEntity(URL +"emailvalidation?token="+ u.getToken(), RestResponse.class);
		assertEquals(HttpStatus.OK, responseHttp.getStatusCode());
		resp = responseHttp.getBody();
		assertEquals(RestResponseStatus.SUCCESS, resp.getStatus());
		
		
		
		
		responseHttp = restTemplate.getForEntity(URL +"login?name="+ user_model.getName() +"&password="+ user_model.getPassword(), RestResponse.class);
		assertEquals(HttpStatus.OK, responseHttp.getStatusCode());
		resp = responseHttp.getBody();
		assertEquals(RestResponseStatus.SUCCESS, resp.getStatus());
		
		
		//System.out.println(resp.getData());
		//u = (User)resp.getData();								//=> ca ne marche pas, le "Object" est gardé en LinkedHashMap (~= tableau d'objects)
		//String jsonStr = resp.getData().toString();
		
		
		ObjectMapper objMap = new ObjectMapper();
		
		try 
		{
			
			u = objMap.readValue(objMap.writeValueAsBytes(resp.getData()), User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(u);
		
		System.out.println(u);
		
		assertEquals("aaa", u.getName());
		assertEquals("aaa@toto.fr", u.getEmail());
	}
	

}
