package fr.dawan.nogashi.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.nogashi.daos.GenericDao;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")                           // @CrossOrigin is used to handle the request from a difference origin.
public class AssociationController extends BuyerController
{
	@Autowired
	GenericDao dao;

}
