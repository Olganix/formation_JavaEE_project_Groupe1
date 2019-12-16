package fr.dawan.nogashi.controlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.beans.RestResponse;
import fr.dawan.nogashi.beans.TchatMessage;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.RestResponseStatus;
import fr.dawan.nogashi.listeners.StartListener;


@RestController
@CrossOrigin(origins="http://localhost")                           // @CrossOrigin is used to handle the request from a difference origin.
public class TchatControllerAngular 
{
	private static final Logger logger = LoggerFactory.getLogger(TchatControllerAngular.class);
       
	@Autowired
	GenericDao dao;
	
	
	
	
	/*****************************************************************************************
	*										getMsg										 * 
	*****************************************************************************************/
	@RequestMapping(path="/getMsg", produces = "application/json")
	//test : http://localhost:8080/nogashi/getMsg
	public RestResponse<List<TchatMessage>> getMsg()
    {
    	EntityManager em = StartListener.createEntityManager();
		
    	List<TchatMessage> listTchatMsg = new ArrayList<TchatMessage>();
		
		try 
		{	
			listTchatMsg = dao.findAll(TchatMessage.class, em, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		return new RestResponse<List<TchatMessage>>(RestResponseStatus.SUCCESS, listTchatMsg);
    }
	
	
	
	/*****************************************************************************************
	*										addMsg										 * 
	*****************************************************************************************/
	@RequestMapping(path="/addMsg", produces = "application/json")
	//test : http://localhost:8080/nogashi/addMsg?messageContent=Salut les gens!
	public RestResponse<List<TchatMessage>> addMsg(@PathParam("messageContent") String messageContent)
    {
    	EntityManager em = StartListener.createEntityManager();
		
    	TchatMessage tchatMsg = new TchatMessage(messageContent, LocalDateTime.now());
    	List<TchatMessage> listTchatMsg = new ArrayList<TchatMessage>();
		
		try
		{	
			dao.saveOrUpdate(tchatMsg, em, false);
			listTchatMsg = dao.findAll(TchatMessage.class, em, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		em.close();
		return new RestResponse<List<TchatMessage>>(RestResponseStatus.SUCCESS, listTchatMsg);
    }
    
}
