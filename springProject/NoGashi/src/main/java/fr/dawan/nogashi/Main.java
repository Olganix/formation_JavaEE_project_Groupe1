package fr.dawan.nogashi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.mindrot.jbcrypt.BCrypt;

import fr.dawan.nogashi.beans.ProductTemplate;
import fr.dawan.nogashi.beans.Address;
import fr.dawan.nogashi.beans.Association;
import fr.dawan.nogashi.beans.Commerce;
import fr.dawan.nogashi.beans.CreditCard;
import fr.dawan.nogashi.beans.Individual;
import fr.dawan.nogashi.beans.Merchant;
import fr.dawan.nogashi.beans.Product;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
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
		
		// decommenter, puis recommenter, pour eviter d'avoir x fois les elements
		setupDataBase();
		
		//TestGetProductTemplateFromMerchant();
		
		em.close();
		emf.close();	
	}

	public static void TestGetProductTemplateFromMerchant()	// ex: pour Joffrey, recuperation des productTemplates appartenant a un merchant :
	{
		GenericDao dao = new GenericDao();
		
		String name = "Big Fernand";
		
		try {
			 
			// Ajoute l'address
			EntityGraph<Merchant> graph = em.createEntityGraph(Merchant.class);
	    	graph.addSubgraph("address");
			
	    	// Recup le Merchant via le name
	    	Merchant mBf = null;
			List<Merchant> lmBf = dao.findNamed(Merchant.class, "name", name, em, true, graph);
			if(lmBf.size()!=0)
				mBf = lmBf.get(0);
			
			if(mBf==null)
			{
				System.out.println("Fail recuperation de "+ name);
				return;
			}
			
			
			//test premiere function
			System.out.println("Test1 recuperation des productTemplate de "+ mBf +" :");
		
			List<ProductTemplate> listpt = dao.findBySomething(ProductTemplate.class, "merchant", mBf, em);
			for(ProductTemplate ptTmp : listpt)
				System.out.println(ptTmp);
			
			// _________________________
			
			//test deuxieme function
			System.out.println("Test2 recuperation des productTemplate de "+ name +" :");
			
			listpt = dao.findBySomethingNamed(ProductTemplate.class, "merchant", "name", name, em);
			for(ProductTemplate ptTmp : listpt)
				System.out.println(ptTmp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	
	public static void setupDataBase()
	{
		GenericDao dao = new GenericDao();
		try {
			List<User> luAd = (List<User>) dao.findNamed(User.class, "name", "Admin", em, true);
			System.out.println("User attendu pour tester le remplissage de la bdd :"+ ((luAd.size()!=0) ? luAd.get(0) : "null"));		//todo remove this print.
			
			if(luAd.size()!=0)
				return;
		} catch (Exception e1) {
			e1.printStackTrace();
		}



		System.out.println("------------------------- setupDataBase Start -------------------------");
		
		List<Merchant> lm = new ArrayList<Merchant>();
		List<Individual> li = new ArrayList<Individual>();
		List<Association> la = new ArrayList<Association>();
		
		User a = 					new User("Admin", "admin@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), 					UserRole.ADMIN, true); a.setEmailValid(true); a.setAvatarFilename("admin.jpg");
		Merchant m = new Merchant(new User("Merchant", "merchant@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.03.03.03.03", 	new Address("59, Rue Merchant", "", "59000", "Lille", "France"), true , true, "merchant.jpg"), "362 521 879 00030", "FR12 1234 1234 1234 1234 59", "12346"); lm.add(m);
		Individual u = new Individual(	new User("User", "user@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.01.01.01.01", new Address("59, Rue User", "", "59000", "Lille", "France"), true , true, "user.jpg"), 	new CreditCard("MasterCard", "4539 1593 1309 2658", "User", "04/23", "092"));	li.add(u);
		Association ass = new Association(	new User("Association", "associationt@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "02.02.02.02.02", 	new Address("59, Rue Association", "Au fond a Gauche", "59000", "Lille", "France"), true , true, "association.jpg"), "362 521 880", "W751212507");	la.add(ass);
		li.add(new Individual(	new User("Anonymous", "anonymous@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, null, null, true , true), null));
		
		
		li.add(new Individual(	new User("Anaïs Despins", "anais.despins@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.56.78.43.93", new Address("9, boulevard d'Alsace", "", "59000", "Lille", "France"), true , true, "F_0001.jpg"), 	new CreditCard("Visa", "4539 1593 1309 2658", "Anaïs Despins", "04/23", "092"))); li.get(li.size()-1).setPhoneNumber2("06.77.77.77.16"); 
		li.add(new Individual(	new User("Éléonore Asselin", "eleonore.asselin@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.88.05.42.57", 	new Address("29, Chemin Challet", "", "59000", "Lille", "France"), true , true, "F_0046.jpg"), 		new CreditCard("MasterCard", "4929 2911 9298 7563", "Éléonore Asselin", "03/25", "572")));
		li.add(new Individual(	new User("Elita Quenneville", "elita.quenneville@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.40.10.61.98",	new Address("27, Chemin Challet", "", "59000", "Lille", "France"), true , true, "F_0010.jpg"), 		new CreditCard("Visa", "5392 1701 5112 0249", "Elita Quenneville", "03/21", "018")));
		li.add(new Individual(	new User("Geoffrey Verreau", "geoffrey.verreau@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.80.40.11.26", 	new Address("8, Avenue des Tuileries", "", "59113", "Seclin", "France"), true , true, "H_0017.jpg"), new CreditCard("MasterCard", "5163 6444 7073 0919", "Geoffrey Verreau", "02/24", "833")));
		li.add(new Individual(	new User("Delmar Dumont", "delmar.dumont@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.45.59.69.12", 			new Address("66, Rue St Ferréol", "", "59113", "Seclin", "France"), true , true, "H_0004.jpg"),		new CreditCard("Visa", "4532 8751 8101 7868", "Delmar Dumont", "08/24", "820")));
		li.add(new Individual(	new User("Nathalie Gabriaux", "nathalie.gabriaux@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.62.58.77.00",	new Address("13, rue Gouin de Beauchesne", "", "59680", "Wattignies", "France"), true , true, "F_0007.jpg"), new CreditCard("MasterCard", "4929 5131 5748 7382", "Nathalie Gabriaux", "06/23", "590")));
		li.add(new Individual(	new User("Charles Margand", "charles.margand@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.65.89.96.02", 		new Address("13, rue Gouin de Beauchesne", "", "59680", "Wattignies", "France"), true , true, "H_0002.jpg"), new CreditCard("Visa", "4556 7288 3650 6226", "Charles Margand", "12/22", "095")));
		li.add(new Individual(	new User("Belisarda De La Vergne", "belisarda.de.la.vergne@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.48.23.29.74", new Address("55, boulevard Aristide Briand", "", "59650", "Villeneuve d'Ascq", "France"), true , true, "F_0040.jpg"), new CreditCard("MasterCard", "5469 4131 0379 1255", "Belisarda De La Vergne", "12/25", "892")));
		li.add(new Individual(	new User("Gilles Collin", "gilles.collin@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.82.19.05.37", 			new Address("34, Rue de la Pompe", "", "59700", "Marcq-en-Baroeul", "France"), true , true, "H_0029.jpg"), new CreditCard("Visa", "5545 8830 0947 7027", "Gilles Collin", "02/24", "784")));
		
		
		

		
		
		//todo Position Gps de tous les association et commerce.
		
		//ex code siret 362 521 879 00034
		//ex code siren 362 521 879
		//ex code RNA (Association) W751212517
		//ex IBAN FR12 1234 1234 1234 1234 12
		//ex BIC 12345
		la.add(new Association(	new User("La Tente des Glaneurs", "contact@latentedesglaneurs.fr", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "06.81.56.21.28", 	new Address("165 boulevard de la liberté", "Jean-Loup Lemaire", "59000", "Lille", "France"), true , true, "LaTentedesGlaneurs.jpg"), "362 521 879", "W751212517") );
		la.add(new Association(	new User("Les Gars'pilleurs", "lesgarspilleurs@gmail.com", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "06.22.99.87.92",	 			new Address("", "", "59000", "Lille", "France"), true , true, "LesGarspilleurs.png"), "362 521 878", "W751212518") );
		la.add(new Association(	new User("Zéro-Gâchis", "retail.zero@gachis.com", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "(+33) 2 40 75 04 95", 				new Address("6, av. Marcelin Berthelot", "Bâtiment Le Newton", "44800", "Saint-Herblain", "France"), true , true, "ZeroGachis.png"), "362 521 877", "W751212519") );
		la.add(new Association(	new User("Banques Alimentaire du Nord", "ba590@banquealimentaire.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "03 20 93 93 93", new Address("Port Fluvial de Lille", "2ème rue - Bâtiment A", "59000", "Lille", "France"), true , true, "LogoBanqueAlimentaire59.png"), "362 521 876", "W751212520") );
		
		
		
		Merchant mBC =  new Merchant(new User("BASILIC & CO Développement", "contact@basilic-and-co.com", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "04.75.45.96.78", 	new Address("80, Rue Nationale", "", "59800", "Lille", "France"), true , true, "basilic-and-co-pizza.png"), "362 521 879 00034", "FR12 1234 1234 1234 1234 12", "12345");	lm.add(mBC); 
		
		//todo corriger apres tous ce qu'il y a apres
		Merchant mDj =  new Merchant(new User("Daily-juicery", "thedailyjuicery@gmail.com", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.20.30.79.73", 	new Address("380 Rue Léon Gambetta", "", "59800", "Lille", "France"), true , true), "362 521 879 00034", "FR12 1234 1234 1234 1234 12", "12345");	lm.add(mDj); 
		Merchant mBm =  new Merchant(new User("Boulangerie-Mathieu", "boulangerie.mathieu@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.20.55.66.57", 	new Address("82 Rue du Molinel", "", "59800", "Lille", "France"), true , true), "795 335 793 00027", "FR12 1234 1234 1234 1234 11", "12344");	lm.add(mBm); 
		Merchant mP =  	new Merchant(new User("Paul", "paul@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.20.57.40.99", 	new Address("19 Place Général de Gaulle", "", "59800", "Lille", "France"), true , true), "403 052 111 00420", "FR12 1234 1234 1234 1234 10", "12343");	lm.add(mP); 
		Merchant mBf =  new Merchant(new User("Big Fernand", "big.fernand@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.20.94.77.767", 	new Address("107 r Esquermoise", "", "59800", "Lille", "France"), true , true), "830 134 458 00017", "FR12 1234 1234 1234 1234 09", "12342");	lm.add(mBf);
		
		Commerce mBC_c1 = new Commerce("Basilic&Co", "362 521 879 00033", "Nationale");	mBC_c1.setMerchant(mBC);		// https://www.basilic-and-co.com/restaurant-pizzeria-lille
		Commerce mDj_c1 = new Commerce("Daily-juicery", "362 521 879 00032", "Gambetta");	mDj_c1.setMerchant(mDj);
		Commerce mBm_c1 = new Commerce("Boulangerie-Mathieu", "795 335 793 00027", "Molinel");	mBm_c1.setMerchant(mBm);
		Commerce mP_c1 = new Commerce("Paul", "403 052 111 00420", "Grand Place");	mP_c1.setMerchant(mP);
		Commerce mBf_c1 = new Commerce("Big Fernand", "830 134 458 00017", "Vieux Lille");	mBf_c1.setMerchant(mBf);
		Commerce mBf_c2 = new Commerce("Big Fernand", "830 134 458 00017", "Faidherbe");	mBf_c2.setMerchant(mBf);
		
		mBC.addProductTemplate(new ProductTemplate("Pizza Savoyarde", "31 cm<br>Crème fraîche, mozzarella artisanale française, lardons fumés, Reblochon de Savoie AOP et fondue d’oignons maison", "0000001", true, 14.90, 10.43));
		mBC.addProductTemplate(new ProductTemplate("Pizza 4 Fromages Des Alpes", "31 cm<br>Sauce tomate BIO maison, mozzarella artisanale française, Tome des Bauges AOP, Bleu du Vercors AOP, Reblochon de Savoie AOP et origan. ", "0000001", true, 14.90, 10.43));
		mBC.addProductTemplate(new ProductTemplate("Pizza Bourguigonne", "31 cm<br>Sauce tomate BIO maison, mozzarella artisanale française, bœuf haché BIO, fondue d’oignons maison, filet de crème et origan", "0000001", true, 15.90, 11.33));
		mBC.addProductTemplate(new ProductTemplate("Pizza Baugienne", "31 cm<br>Crème fraîche, mozzarella artisanale française, champignons frais, lardons fumés, Tome des Bauges AOP et ciboulette fraîche.", "0000001", true, 15.90, 11.33));
		mDj.addProductTemplate(new ProductTemplate("Green 5,0 – Jus Detox 100% Naturel", "Concombre - Pomme - Chou kale -Céleri - Laitue - Menthe - Citron vert<br>Allié minceur - Vitamines C  et K1 - Contribue au renforcement des os", "0000001", true, 7.0, 4.9));
		mDj.addProductTemplate(new ProductTemplate("Roots 1,2 – Jus Detox 100% Naturel", "Orange - Pomme - Carotte - Betterave -  Gingembre - Citron vert<br>Aide à protéger des maladies - Energisant - Vitamines C - Aide la circulation du sang", "0000001", true, 7, 4.9));
		
		mBm.addProductTemplate(new ProductTemplate("Pain Aux Fromages", "Farine : type 65 label rouge, emmental, mimolette", "0000001", true, 1.50, 0.99));
		mBm.addProductTemplate(new ProductTemplate("Pain Aux Olives", "Farine : type 65 label rouge, olives vertes, herbes de Provence, huile d’olive", "0000001", true, 1.45, 0.99));
		mBm.addProductTemplate(new ProductTemplate("Pain Au Chorizo", "Farine : type 65 label rouge, chorizo, épices mexicaines.", "0000001", true, 1.40, 0.99));
		mBm.addProductTemplate(new ProductTemplate("Pain Tradition", "Farine : t65 label rouge, levain liquide de blé", "0000001", true, 1.35, 0.99));
		mBm.addProductTemplate(new ProductTemplate("Pain Segles (sans blé)", "Farine : bio seigle type 130 sur meule de pierre, levain dur de seigle.", "0000001", true, 1.85, 1.25));
		
		//todo : deplacer les ingredients de la description vers les productDetails
		mP.addProductTemplate(new ProductTemplate("Le Fromage Blanc Miel Et Granola", "Finissez votre déjeuner sur une note sucrée avec ce dessert gourmand. Un fromage blanc accompagné d'un granola enrichi de cranberries, raisins secs et d'amandes effilées<br>Gluten – blé – lait – fruits à coque – noix - sulfite", "0000001", true, 3.82, 2.67));
		mP.addProductTemplate(new ProductTemplate("Le Riz Au Lait Mangue", "Un dessert délicieusement crémeux accompagné de mangues. Ce riz au lait est composé de compote mangue citron vert, de riz au lait de coco et de graines de chia.<br>Gluten – avoine – lait – fruit à coque - amande", "0000001", true, 4.12, 2.88));		
		mP.addProductTemplate(new ProductTemplate("La Salade Chévre, Miel Et Abricot", "Notre salade marie le sucré du miel avec le salé du chèvre et le croquant des noix. Elle est idéale pour les végétariens.<br>Une salade composée de roquette, haricots verts, chèvre, tomates, croûtons, noix, abricots secs et miel.<br>Gluten – blé – lait – fruits à coque – noix - sulfite", "0000001", true, 8.20, 5.74));
		mP.addProductTemplate(new ProductTemplate("Quiche Lorraine", "On ne la présente plus, la quiche Lorraine est une recette traditionnelle ! A l'origine, la cuisson de la quiche Lorraine était un moment de partage convivial. Aujourd'hui, c'est toujours le cas, en entrée ou en plat, chaude ou froide, elle est indémodable.<br>Nos quiches Lorraine sont cuisinées à base de pâte feuilletée pur beurre, lait, crème fraîche, ufs, sel, lardons fumés, et fromages râpés.Sans colorant ni conservateur.<br>Gluten – blé – oeufs – lait", "0000001", true, 5.80, 4.00));
		mP.addProductTemplate(new ProductTemplate("La Salade Paul", "L'emblématique salade PAUL, généreuse et gourmande, est composée de filets de poulet, croûtons aillés et d'une sauce caesar.<br>Une salade composée d'un mélange de salade, radis, carotte, poulet, croutons aillés, fromages râpés, copeaux de parmesan, ciboulette ciselée, et d'une sauce caesar.<br>Gluten – blé – oeufs – orge – poisson – soja – lait – moutarde ", "0000001", true, 5.60, 3.92));
		mP.addProductTemplate(new ProductTemplate("La Sandwitch Viennois Mixte Crudités", "Le viennois mixte crudités substitue le jambon fromage crudité. Un pain viennois tendre, moelleux et un sandwich plus généreux pour le plus grand plaisir des gourmands.<br>Un pain viennois compsoé de jambon blanc de qualité supérieur, d'emmental, de salade et de la fraicheur d'une tartinade nature.<br>Gluten – blé – seigle – œufs - lait", "0000001", true, 5.60, 3.92));

		mBf.addProductTemplate(new ProductTemplate("Le Pierrot", "Haut de cuisse de poulet fermier des Landes marinée Label Rouge, Sainte-Maure-de-Touraine (fromage de chèvre AOP), de la riquette poivrée et notre sauce mayonnaise maison aux olives, tomates et basilic + Frites maison (patates lavées, épluchées, coupées sur place) ou salade + Breuvage", "0000001", true, 16.0, 11.2));
		mBf.addProductTemplate(new ProductTemplate("Le Lucien (Végétarien)", "Gros Champignon de Paname avec de la Tomme de Savoie au lait cru fondue, des tomates séchées, des oignons confits, de la ciboulette ciselée et notre sauce mayonnaise maison délicatement sucrée + Frites maison (patates lavées, épluchées, coupées sur place) ou salade + Breuvage + Dessert artisanal", "0000001", true, 16.0, 11.2));
		
		/*
		for (ProductTemplate pt : mBC.getProductTemplates()) {
			new Product(pt, mBC_c1);
		}
		*/
		
		//todo complete en regardant les menus en ligne. 
		//Todo aussi mettre les url pour les retrouver facilement. 
		//Todo chopper les images des produits.
		
		for(Merchant mTmp : lm)
			for(Commerce cTmp : mTmp.getCommerces())
				for(ProductTemplate ptTmp : mTmp.getProductTemplates())
					cTmp.addProductTemplate(ptTmp);
		
		
		//todo reglers encore des soucis : tables de relations par ex entre les productTemplate et Commerce , ou encore le INDIVIDUAL_id dans les CreditCard n'est pas remplit.
		
		try
		{
			et.begin();
			
			em.persist(a);
			
			for(Individual i : li)
			{
				if(i.getAddress()!=null)
					em.persist(i.getAddress());				//because of the OneToONe, witch make the follow error if there isn't Adress saved : "Caused by: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : fr.dawan.nogashi.beans.Individual.address -> fr.dawan.nogashi.beans.Address"
				
				if(i.getCreditCard()!=null)
					em.persist(i.getCreditCard());			//same
				
				em.persist(i);
			}
			
			for(Association assTmp : la)
			{
				if(assTmp.getAddress()!=null)
					em.persist(assTmp.getAddress());
				em.persist(assTmp);
			}
			
			for(Merchant mTmp : lm)
			{
				for(Commerce cTmp : mTmp.getCommerces())		//because the list is mapped that way : '@OneToMany(mappedBy = "merchant")', you don't have automatic persistence. so you have to it manually. If it's mapped on other class, it should be good , but problematic on loading merchant without loading all the bdd.
					em.persist(cTmp);
				
				for(ProductTemplate ptTmp : mTmp.getProductTemplates())
					em.persist(ptTmp);
				
				if(mTmp.getAddress()!=null)
					em.persist(mTmp.getAddress());
				em.persist(mTmp);
			}
			
			et.commit();
			
			
		} catch (Exception e) {
			et.rollback();					//undo if troubles
			e.printStackTrace();		
		}
		
		System.out.println("------------------------- setupDataBase End -------------------------");
	}
}
