package fr.dawan.nogashi;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.mindrot.jbcrypt.BCrypt;

import fr.dawan.nogashi.beans.ProductTemplate;
import fr.dawan.nogashi.beans.SchedulerDay;
import fr.dawan.nogashi.beans.SchedulerHoursRange;
import fr.dawan.nogashi.beans.SchedulerWeek;
import fr.dawan.nogashi.beans.ShoppingCart;
import fr.dawan.nogashi.beans.ShoppingCartByCommerce;
import fr.dawan.nogashi.beans.Address;
import fr.dawan.nogashi.beans.Association;
import fr.dawan.nogashi.beans.Buyer;
import fr.dawan.nogashi.beans.Commerce;
//import fr.dawan.nogashi.beans.CommerceCategory;
import fr.dawan.nogashi.beans.CreditCard;
//import fr.dawan.nogashi.beans.EnumManager;
import fr.dawan.nogashi.beans.Individual;
import fr.dawan.nogashi.beans.Merchant;
import fr.dawan.nogashi.beans.Product;
import fr.dawan.nogashi.beans.User;
import fr.dawan.nogashi.daos.GenericDao;
import fr.dawan.nogashi.enums.ProductStatus;
import fr.dawan.nogashi.enums.SchedulerWeekType;
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
		
		previouslyInNogashi();								// un historic pour Gilles
		
		merchantStartDay();
		individualInPromotion();
		
		
		
		// un shopping card payé pour Gilles.
		// l'association stop gashi
		
		
		
		
		
		em.close();
		emf.close();	
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
		
		User a = new User("Admin", "admin@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ADMIN, true); a.setEmailValid(true); a.setAvatarFilename("admin.jpg");
		Merchant m = new Merchant(new User("Merchant", "merchant@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.03.03.03.03", 	new Address("59, Rue Merchant", "", "59000", "Lille", "France", 00.00, 00.00), true , true, "merchant.jpg"), "362 521 879 00030", "FR12 1234 1234 1234 1234 59", "12346"); lm.add(m);
		Individual u = new Individual(	new User("User", "user@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.01.01.01.01", new Address("59, Rue User", "", "59000", "Lille", "France", 00.00, 00.00), true , true, "user.jpg"), 	new CreditCard("MasterCard", "4539 1593 1309 2658", "User", "04/23", "092"));	li.add(u);
		Association ass = new Association(	new User("Association", "associationt@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "02.02.02.02.02", 	new Address("59, Rue Association", "Au fond a Gauche", "59000", "Lille", "France", 00.00, 00.00), true , true, "association.jpg"), "362 521 880", "W751212507");	la.add(ass);
		// li.add(new Individual(	new User("Anonymous", "anonymous@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, null, null, true , true), null));
		
		li.add(new Individual(	new User("Anaïs Despins", "anais.despins@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.56.78.43.93", 						new Address("9, boulevard d'Alsace", "", "59000", "Lille", "France", 00.00, 00.00), true , true, "F_0001.jpg"), 						new CreditCard("Visa", "4539 1593 1309 2658", "Anaïs Despins", "04/23", "092"))); li.get(li.size()-1).setPhoneNumber2("06.77.77.77.16"); 
		li.add(new Individual(	new User("Éléonore Asselin", "eleonore.asselin@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.88.05.42.57", 				new Address("29, Chemin Challet", "", "59000", "Lille", "France", 00.00, 00.00), true , true, "F_0046.jpg"), 							new CreditCard("MasterCard", "4929 2911 9298 7563", "Éléonore Asselin", "03/25", "572")));
		li.add(new Individual(	new User("Elita Quenneville", "elita.quenneville@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.40.10.61.98",				new Address("27, Chemin Challet", "", "59000", "Lille", "France", 00.00, 00.00), true , true, "F_0010.jpg"), 							new CreditCard("Visa", "5392 1701 5112 0249", "Elita Quenneville", "03/21", "018")));
		li.add(new Individual(	new User("Geoffrey Verreau", "geoffrey.verreau@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.80.40.11.26", 				new Address("8, Avenue des Tuileries", "", "59113", "Seclin", "France", 00.00, 00.00), true , true, "H_0017.jpg"), 						new CreditCard("MasterCard", "5163 6444 7073 0919", "Geoffrey Verreau", "02/24", "833")));
		li.add(new Individual(	new User("Delmar Dumont", "delmar.dumont@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.45.59.69.12", 						new Address("66, Rue St Ferréol", "", "59113", "Seclin", "France", 00.00, 00.00), true , true, "H_0004.jpg"),							new CreditCard("Visa", "4532 8751 8101 7868", "Delmar Dumont", "08/24", "820")));
		li.add(new Individual(	new User("Nathalie Gabriaux", "nathalie.gabriaux@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.62.58.77.00",				new Address("13, rue Gouin de Beauchesne", "", "59680", "Wattignies", "France", 00.00, 00.00), true , true, "F_0007.jpg"), 				new CreditCard("MasterCard", "4929 5131 5748 7382", "Nathalie Gabriaux", "06/23", "590")));
		li.add(new Individual(	new User("Charles Margand", "charles.margand@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "01.65.89.96.02", 					new Address("13, rue Gouin de Beauchesne", "", "59680", "Wattignies", "France", 00.00, 00.00), true , true, "H_0002.jpg"), 				new CreditCard("Visa", "4556 7288 3650 6226", "Charles Margand", "12/22", "095")));
		li.add(new Individual(	new User("Belisarda De La Vergne", "belisarda.de.la.vergne@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.48.23.29.74", 	new Address("55, boulevard Aristide Briand", "", "59650", "Villeneuve d'Ascq", "France", 00.00, 00.00), true , true, "F_0040.jpg"), 	new CreditCard("MasterCard", "5469 4131 0379 1255", "Belisarda De La Vergne", "12/25", "892")));
		li.add(new Individual(	new User("Gilles Collin", "gilles.collin@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.INDIVIDUAL, "03.82.19.05.37", 						new Address("34, Rue de la Pompe", "", "59700", "Marcq-en-Baroeul", "France", 00.00, 00.00), true , true, "H_0029.jpg"), 				new CreditCard("Visa", "5545 8830 0947 7027", "Gilles Collin", "02/24", "784")));
		
		//ex code siret 362 521 879 00034
		//ex code siren 362 521 879
		//ex code RNA (Association) W751212517
		//ex IBAN FR12 1234 1234 1234 1234 12
		//ex BIC 12345
		la.add(new Association(	new User("La Tente des Glaneurs", "contact@latentedesglaneurs.fr", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "06.81.56.21.28", 	new Address("57 rue Paul Bret", "", "59280", "Armentières", "France", 2.88876, 50.6875), true , true, "LaTentedesGlaneurs.jpg"), "362 521 879", "W751212517") );
		la.add(new Association(	new User("Les Gars'pilleurs", "lesgarspilleurs@gmail.com", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "06.22.99.87.92",	 			new Address("", "", "59000", "Lille", "France", 3.0586, 50.633), true , true, "LesGarspilleurs.png"), "362 521 878", "W751212518") );
		la.add(new Association(	new User("Zéro-Gâchis", "retail.zero@gachis.com", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "(+33) 2 40 75 04 95", 				new Address("6 avenue Marcelin Berthelot", "Bâtiment Le Newton", "44800", "Saint-Herblain", "France", -1.617772, 47.226511), true , true, "ZeroGachis.png"), "362 521 877", "W751212519") );
		la.add(new Association(	new User("Banque Alimentaire du Nord", "ba590@banquealimentaire.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.ASSOCIATION, "03 20 93 93 93",  new Address("Port Fluvial de Lille", "2ème rue - Bâtiment A", "59000", "Lille", "France", 3.037617, 50.634222), true , true, "LogoBanqueAlimentaire59.png"), "362 521 876", "W751212520") );
		
		
		
		Merchant mBC =  new Merchant(new User("BASILIC & CO Développement", "contact@basilic-and-co.com", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "04.75.45.96.78", new Address("80 rue Nationale", "", "59800", "Lille", "France", 3.05968, 50.6359), true , true, "basilic.png"), "362 521 879 00034", "FR12 1234 1234 1234 1234 12", "12345");	lm.add(mBC); 
		
		// TODO corriger apres tous ce qu'il y a apres
		/* ---------- MERCHANTS ---------- */
		Merchant mDj =  new Merchant(new User("Daily-juicery", "thedailyjuicery@gmail.com", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.20.30.79.73", 				new Address("380 rue Léon Gambetta", "", "59800", "Lille", "France", 3.04787, 50.6267), true , true, "daily-juicery.png"), "362 521 879 00034", "FR12 1234 1234 1234 1234 12", "12345");	lm.add(mDj); 
		Merchant mBm =  new Merchant(new User("Boulangerie-Mathieu", "boulangerie.mathieu@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.20.55.66.57", 	new Address("82 rue du Molinel", "", "59800", "Lille", "France", 3.06686, 50.6341), true , true, "boulangerie-mathieu.jpeg"), "795 335 793 00027", "FR12 1234 1234 1234 1234 11", "12344");	lm.add(mBm); 
		Merchant mP =  	new Merchant(new User("Paul", "paul@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.20.57.40.99", 								new Address("19 place Général de Gaulle", "", "59000", "Lille", "France", 3.063, 50.6368), true , true, "paul.jpg"), "403 052 111 00420", "FR12 1234 1234 1234 1234 10", "12343");	lm.add(mP); 
		Merchant mBf =  new Merchant(new User("Big Fernand", "big.fernand@noghasi.org", BCrypt.hashpw("totototo", BCrypt.gensalt()), UserRole.MERCHANT, "03.20.94.77.767", 					new Address("107 rue Esquermoise", "", "59800", "Lille", "France", 3.05871, 50.6389), true , true, "big-fernand.png"), "830 134 458 00017", "FR12 1234 1234 1234 1234 09", "12342");	lm.add(mBf);
		
		/* ---------- SCHEDULER WEEKS ---------- */
		/*
		SchedulerWeek sw = new SchedulerWeek();
		sw.setType(SchedulerWeekType.GROUP);
		List<SchedulerWeek> lsw = sw.getGroup();
		
		lsw.add(swTmp);
		*/
		
		SchedulerWeek swTmp = new SchedulerWeek();
		swTmp.setName("Horaire d'ouverture");
		swTmp.setType(SchedulerWeekType.OPEN);
		List<SchedulerDay> lsd = swTmp.getDays();
		
		// Exemple d'horaires d'ouverture.
		SchedulerHoursRange shr_am = new SchedulerHoursRange();
		shr_am.setStartTime(9 * 60);		// 09h00
		shr_am.setEndTime(12 * 60 + 30);	// 12h30
		
		SchedulerHoursRange shr_pm = new SchedulerHoursRange();
		shr_pm.setStartTime(14 * 60);		// 14h00
		shr_pm.setEndTime(19 * 60 + 30);	// 19h00
		
		SchedulerDay sd;
		for(int i= 0; i <= 5; i++) { 		// Monday -> Friday
			sd = new SchedulerDay();
			sd.setDay( DayOfWeek.values()[i] );
			lsd.add(sd);
			List<SchedulerHoursRange> lshr = sd.getHoursRanges();
			
			lshr.add(shr_am);
			lshr.add(shr_pm);
		}
		
		/* ---------- COMMERCES ---------- */
		Commerce mBC_c1 = new Commerce("Basilic & Co", "362 521 879 00033", new Address("80 rue Nationale", "", "59800", "Lille", "France", 3.059697, 50.635931), "basilic.png", "basilic_desc.jpg", "24, c'est le nombre de restaurants que compte désormais le réseau Basilic & Co, depuis l'ouverture de son nouveau point de vente de Tours Nord. Depuis début Novembre 2019, Aurélien Harnay et Geoffrey Vilain vous accueillent au 42 rue Daniel Mayer, en plein coeur de Monconseil. Très impliqués localement, ils s'investissent pour faire de leur pizzeria de terroir l'un des lieux de vie incontournables du quartier. Leur ouverture s'est traduite par un succès retentissant, pour le plus grand bonheur des Tourangeaux !", swTmp, "Nationale"); mBC_c1.setMerchant(mBC);		
		Commerce mDj_c1 = new Commerce("Daily-juicery", "362 521 879 00032", new Address("380 rue Léon Gambetta", "", "59000", "Lille", "France", 3.04787, 50.6267), "daily-juicery.png", "dailyjuicery_desc.png", "Buvez et mangez vos 5 fruits et légumes par jour grâce à nos jus DETOX pressés à froid & à nos préparations HEALTHY  100% à base de fruits et légumes frais et de saison, sans sucres ajoutés ni conservateurs !", swTmp, "Gambetta"); mDj_c1.setMerchant(mDj);
		Commerce mBm_c1 = new Commerce("Boulangerie-Mathieu", "795 335 793 00027", new Address("82 rue du Molinel", "", "59000", "Lille", "France", 3.06686, 50.6341), "boulangerie-mathieu.jpeg", "mathieu_desc.jpg", "Un bon pain c’est tout d’abord un bon partenariat avec son meunier qui saura apporter des farines de qualité, mais c’est aussi le temps de travail avec de bons levains. Nous avons à cœur de créer et de réaliser des recettes respectueuses de l’environnement pour vous proposer une variété de pains bio, composés d’une multitude de céréales.", swTmp, "Molinel"); mBm_c1.setMerchant(mBm);
		Commerce mP_c1 = new Commerce("Paul", "403 052 111 00420", new Address("19 place Charles de Gaulle", "", "59000", "Lille", "France", 3.0630005, 50.6367832), "paul.jpg", "paul_desc.jpg", "Après 125 ans d’existence, PAUL est l’ambassadeur de l’Art de vivre à la française. Les équipes de l’enseigne, présentent dans plus de 33 pays, partagent le goût du travail bien fait, la joie d’offrir chaque jour des produits de qualité et d’accueillir les clients dans un cadre enchanteur et unique.", swTmp, "Charles de Gaulle"); mP_c1.setMerchant(mP);
		Commerce mBf_c1 = new Commerce("Big Fernand", "830 134 458 00017", new Address("107 Rue Esquermoise", "", "59000", "Lille", "France", 3.0586802, 50.6388127), "big-fernand.png", "fernand_desc.jpg", "\"Faudro qt'arrêtes eud' mier des burgers, mies des hamburgés, che ben meilleu\"<br/>L'équipe", swTmp, "Esquermoise"); mBf_c1.setMerchant(mBf);
		Commerce mBf_c2 = new Commerce("Big Fernand", "830 134 458 00017", new Address("10 Rue Faidherbe", "", "59000", "Lille", "France", 3.0657011, 50.6369357), "big-fernand.png", "fernand_desc_2.jpg", "\"Faudro qt'arrêtes eud' mier des burgers, mies des hamburgés, che ben meilleu\"<br/>L'équipe", swTmp, "Faidherbe"); mBf_c2.setMerchant(mBf);
		
		/* ---------- COMMERCE CATEGORIES  ---------- */
		// TODO compléter même si je suis en train de pleurer sur ce truc
		/* EnumManager enuma = new EnumManager();
		enuma.addCommerceCategory(new CommerceCategory("Pizzeria"));
		enuma.addCommerceCategory(new CommerceCategory("Boulangerie"));
		enuma.addCommerceCategory(new CommerceCategory("Restaurant")); */
		
		// TODO déplacer les ingredients de la description de chaque produit vers ProductDetail.
		/* ---------- PRODUITS BASILIC AND CO ---------- */
		// https://www.basilic-and-co.com/carte-pizzas
		mBC.addProductTemplate(new ProductTemplate("Pizza savoyarde", "31 cm<br>Crème fraîche, mozzarella artisanale française, lardons fumés, Reblochon de Savoie AOP et fondue d’oignons maison", "0000001", true, 14.90, 10.43, "pizza-savoyarde.png"));
		mBC.addProductTemplate(new ProductTemplate("Pizza 4 fromages des alpes", "31 cm<br>Sauce tomate BIO maison, mozzarella artisanale française, Tome des Bauges AOP, Bleu du Vercors AOP, Reblochon de Savoie AOP et origan. ", "0000002", true, 14.90, 10.43, "pizza-4-fromages-des-alpes-basilic.jpg"));
		mBC.addProductTemplate(new ProductTemplate("Pizza bourguignonne", "31 cm<br>Sauce tomate BIO maison, mozzarella artisanale française, bœuf haché BIO, fondue d’oignons maison, filet de crème et origan", "0000003", true, 15.90, 11.33, "pizza-bourguignonne-basilic.jpeg"));
		mBC.addProductTemplate(new ProductTemplate("Pizza baugienne", "31 cm<br>Crème fraîche, mozzarella artisanale française, champignons frais, lardons fumés, Tome des Bauges AOP et ciboulette fraîche.", "0000004", true, 15.90, 11.33, "pizza-baugienne-basilic.jpg"));
		mDj.addProductTemplate(new ProductTemplate("Green 5,0 – Jus Detox 100% naturel", "Concombre - Pomme - Chou kale -Céleri - Laitue - Menthe - Citron vert<br>Allié minceur - Vitamines C  et K1 - Contribue au renforcement des os", "0000005", true, 7.0, 4.9, "green-jus detox naturel-daily.jpg"));
		mDj.addProductTemplate(new ProductTemplate("Roots 1,2 – Jus Detox 100% naturel", "Orange - Pomme - Carotte - Betterave -  Gingembre - Citron vert<br>Aide à protéger des maladies - Energisant - Vitamines C - Aide la circulation du sang", "0000006", true, 7, 4.9, "roots-jus detox naturel-daily.jpg"));
		
		/* ---------- PRODUITS BOULANGERIE MATHIEU ---------- */
		// http://laboulangeriemathieu.com/portfolio-items/nos-pains/
		mBm.addProductTemplate(new ProductTemplate("Pain aux fromages", "Farine : type 65 label rouge, emmental, mimolette", "0000007", true, 1.50, 0.99, "painfromage-mathieu.jpg"));
		mBm.addProductTemplate(new ProductTemplate("Pain aux olives", "Farine : type 65 label rouge, olives vertes, herbes de Provence, huile d’olive", "0000008", true, 1.45, 0.99, "pain-olives-mathieujpg.jpg"));
		mBm.addProductTemplate(new ProductTemplate("Pain au chorizo", "Farine : type 65 label rouge, chorizo, épices mexicaines.", "0000009", true, 1.40, 0.99, "pain-chorizo-mathieu.jpg"));
		mBm.addProductTemplate(new ProductTemplate("Pain tradition", "Farine : t65 label rouge, levain liquide de blé", "0000010", true, 1.35, 0.99, "pain-tradition.jpg"));
		mBm.addProductTemplate(new ProductTemplate("Pain seigle (sans blé)", "Farine : bio seigle type 130 sur meule de pierre, levain dur de seigle.", "0000011", true, 1.85, 1.25, "pain-segles-mathieu.jpg"));
		
		/* ---------- PRODUITS PAUL ---------- */
		// https://www.paul.fr/fr/3-nos-produits
		mP.addProductTemplate(new ProductTemplate("Le fromage blanc miel et granola", "Finissez votre déjeuner sur une note sucrée avec ce dessert gourmand. Un fromage blanc accompagné d'un granola enrichi de cranberries, raisins secs et d'amandes effilées<br>Gluten – blé – lait – fruits à coque – noix - sulfite", "0000012", true, 3.82, 2.67, "fromage-blanc-miel-et-granola-paul.jpg"));
		mP.addProductTemplate(new ProductTemplate("Le riz au lait mangue", "Un dessert délicieusement crémeux accompagné de mangues. Ce riz au lait est composé de compote mangue citron vert, de riz au lait de coco et de graines de chia.<br>Gluten – avoine – lait – fruit à coque - amande", "0000013", true, 4.12, 2.88, "riz-au-lait-mangue-paul.jpg"));		
		mP.addProductTemplate(new ProductTemplate("La salade chèvre, miel et abricot", "Notre salade marie le sucré du miel avec le salé du chèvre et le croquant des noix. Elle est idéale pour les végétariens.<br>Une salade composée de roquette, haricots verts, chèvre, tomates, croûtons, noix, abricots secs et miel.<br>Gluten – blé – lait – fruits à coque – noix - sulfite", "0000014", true, 8.20, 5.74, "salade-chevre-miel-et-abricot-paul.jpg"));
		mP.addProductTemplate(new ProductTemplate("Quiche lorraine", "On ne la présente plus, la quiche Lorraine est une recette traditionnelle ! A l'origine, la cuisson de la quiche Lorraine était un moment de partage convivial. Aujourd'hui, c'est toujours le cas, en entrée ou en plat, chaude ou froide, elle est indémodable.<br>Nos quiches Lorraine sont cuisinées à base de pâte feuilletée pur beurre, lait, crème fraîche, ufs, sel, lardons fumés, et fromages râpés. Sans colorant ni conservateur.<br>Gluten – blé – oeufs – lait", "0000015", true, 5.80, 4.00, "quiche-lorraine-paul.jpg"));
		mP.addProductTemplate(new ProductTemplate("La salade Paul", "L'emblématique salade PAUL, généreuse et gourmande, est composée de filets de poulet, croûtons aillés et d'une sauce caesar.<br>Une salade composée d'un mélange de salade, radis, carotte, poulet, croutons aillés, fromages râpés, copeaux de parmesan, ciboulette ciselée, et d'une sauce caesar.<br>Gluten – blé – oeufs – orge – poisson – soja – lait – moutarde ", "0000016", true, 5.60, 3.92, "salade-paul.jpg"));
		mP.addProductTemplate(new ProductTemplate("Le sandwich viennois mixte crudités", "Le viennois mixte crudités substitue le jambon fromage crudité. Un pain viennois tendre, moelleux et un sandwich plus généreux pour le plus grand plaisir des gourmands.<br>Un pain viennois compsoé de jambon blanc de qualité supérieur, d'emmental, de salade et de la fraicheur d'une tartinade nature.<br>Gluten – blé – seigle – œufs - lait", "0000017", true, 5.60, 3.92, "sandwich-viennois-mixte-crudites-paul.jpg"));
		
		/* ---------- PRODUITS BIG FERNAND ---------- */
		// https://bigfernand.com/menu/
		mBf.addProductTemplate(new ProductTemplate("Le Pierrot", "Haut de cuisse de poulet fermier des Landes marinée label rouge, Sainte-Maure-de-Touraine (fromage de chèvre AOP), de la riquette poivrée et notre sauce mayonnaise maison aux olives, tomates et basilic + Frites maison (patates lavées, épluchées, coupées sur place) ou salade + Breuvage", "0000018", true, 16.0, 11.2, "le-pierrot-fernand.jpeg"));
		mBf.addProductTemplate(new ProductTemplate("Le Lucien (végétarien)", "Gros Champignon de Paname avec de la Tomme de Savoie au lait cru fondue, des tomates séchées, des oignons confits, de la ciboulette ciselée et notre sauce mayonnaise maison délicatement sucrée + Frites maison (patates lavées, épluchées, coupées sur place) ou salade + Breuvage + Dessert artisanal", "0000019", true, 16.0, 11.2, "lucien-vegetarien-fernand.jpeg"));
		mBf.addProductTemplate(new ProductTemplate("Le Victor", "Fourme d’Ambert (bleu crémeux) oignons confits, coriandre, sauce Tonton Fernand (mayonnaise délicatement sucrée", "0000020", true, 16.0, 11.2, "levictor.jpg"));
		mBf.addProductTemplate(new ProductTemplate("Le Big Fernand", "Bœuf (race à viande) tomme de Savoie au lait cru, tomates séchées, persil plat, sauce Tata Fernande (sauce cocktail maison)", "0000021", true, 16.0, 11.2, "lebigfernand.jpg"));
		mBf.addProductTemplate(new ProductTemplate("Le Bartholomé", "Bœuf (race à viande), raclette des Alpes au lait cru, poitrine de porc fumée, oignons confits – ciboulette, sauce BB Fernand (sauce barbecue big fernand)", "0000022", true, 16.0, 11.2, "lebartho.jpg"));
		
		// TODO mettre les URL individuelles des produits pour les retrouver facilement (j'ai mis que les URL principales pour l'instant).
		for(Merchant mTmp : lm)
			for(Commerce cTmp : mTmp.getCommerces())
				for(ProductTemplate ptTmp : mTmp.getProductTemplates())
					cTmp.addProductTemplate(ptTmp);
		
		
		// TODO regler encore des soucis : tables de relations par ex entre les productTemplate et Commerce , ou encore le INDIVIDUAL_id dans les CreditCard n'est pas remplit.
		
		try
		{
			et.begin();
			
			em.persist(a);
			
			for(SchedulerDay sdTmp : swTmp.getDays())
			{
				for(SchedulerHoursRange shrTmp : sdTmp.getHoursRanges())
					em.persist(shrTmp);
				em.persist(sdTmp);
			}
			em.persist(swTmp);
				
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
				{
					if(cTmp.getAddress()!=null)
						em.persist(cTmp.getAddress());				//because of the OneToONe, witch make the follow error if there isn't Adress saved : "Caused by: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : fr.dawan.nogashi.beans.Individual.address -> fr.dawan.nogashi.beans.Address"
					
					em.persist(cTmp);
				}
				
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
	
	
	
	
	
	
	
	
	
	
	

	public static void previouslyInNogashi()
	{
		GenericDao dao = new GenericDao();
		try {
			Product p = (Product) dao.find(Product.class, 0, em);
			System.out.println("Product attendu pour tester le remplissage de la bdd :"+ p);		//todo remove this print.
			
			if(p!=null)
				return;
		} catch (Exception e1) {
			e1.printStackTrace();
		}


		System.out.println("------------------------- previouslyInNogashi Start -------------------------");
		
		
		Merchant m = null;
		List<Commerce> lc = new ArrayList<Commerce>();
		Buyer buyer = null;
		ShoppingCart sc = null;
		
		try {
			List<Merchant> lm = new ArrayList<Merchant>();
			
			
			lm = dao.findNamed(Merchant.class, "name", "Big Fernand", em);
			if(lm.size()==0)
				return;
			
			m = lm.get(0);
			lc = m.getCommerces();
			if(lc.size()==0)
				return;
			
			
			List<Buyer> lb = dao.findNamed(Buyer.class, "name", "Gilles Collin", em);
			if(lb.size()==0)
				return;
			buyer = lb.get(0);
			
			List<ShoppingCart> lsc = dao.findBySomething(ShoppingCart.class, "buyer", buyer, em);
			if(lsc.size()==0)
				return;
				
			sc = lsc.get(0);
			System.out.println("ShoppingCart attendu pour tester le remplissage de la bdd :"+ sc);		//todo remove this print.
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		
		
		
		// operations du merchant.
		List<Product> lp = new ArrayList<Product>();
		
		for(Commerce c : m.getCommerces())
		{
			for(ProductTemplate pt : c.getProductTemplates())
			{
				int nbProducts = (int)Math.floor(Math.random() * 5);
				
				for(int i = 0; i< nbProducts; i++)
					lp.add( pt.createProduct(c) );
			}
		}
		
		
		// operations du buyer
		sc = new ShoppingCart(buyer);
		
		
		for(Commerce c : lc)
		{
			List<Product> lpt = c.getProducts();
			
			List<Integer> lpti = new ArrayList<Integer>();
			for(int i = 0; i < lpt.size(); i++)
				lpti.add(i);
			
			int nbProducts = lpt.size();
			for(int i = 0;  i< nbProducts; i++)
			{
				int index = (int)Math.round(Math.random() * (lpti.size() - 1));
				
				ShoppingCartByCommerce scbc = sc.getShoppingCartByCommerce(c);
				if(scbc==null)
				{
					scbc = new ShoppingCartByCommerce(c, sc);
					sc.addShoppingCartByCommerces(scbc);
				}
				
				Product p = lpt.get(lpti.get(index));
				p.setStatus(ProductStatus.SOLD);
				
				scbc.addProduct( p );
				lpti.remove((int)index);
			}
		}
		
		buyer.addHistoricShoppingCarts(sc);
		
		
		try
		{
			et.begin();
			
			
			em.persist(sc);
			em.persist(m);						//to save Commerce and ProducTemplate
			em.persist(buyer);					//normally also save shoppingCart
			
			for(Product p : lp)
				em.persist(p);
			
			et.commit();
			
			
		} catch (Exception e) {
			et.rollback();					//undo if troubles
			e.printStackTrace();		
		}
		
		System.out.println("------------------------- previouslyInNogashi End -------------------------");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void merchantStartDay()
	{
		GenericDao dao = new GenericDao();
		
		
		try {										//Todo make a better check because of previouslyInNogashi
			Product p = (Product) dao.find(Product.class, 0, em);
			System.out.println("Product attendu pour tester le remplissage de la bdd :"+ p);		//todo remove this print.
			
			if(p!=null)
				return;
		} catch (Exception e1) {
			e1.printStackTrace();
		}


		System.out.println("------------------------- merchantStartDay Start -------------------------");
		
		
		Merchant m = null;
		Commerce c1 = null;
		Commerce c2 = null;
		
		try {
			List<Merchant> lm = new ArrayList<Merchant>();
			List<Commerce> lc = new ArrayList<Commerce>();
			
			lm = dao.findNamed(Merchant.class, "name", "Big Fernand", em);
			if(lm.size()!=0)
			{
				System.out.println("tata");
				m = lm.get(0);
				
				lc = dao.findBySomething(Commerce.class, "merchant", m, em);
			}
			
			
			if(lc.size()>=2)
			{
				System.out.println("yoyo");
				c1 = lc.get(0);
				c2 = lc.get(1);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if((m==null) || (c1==null) || (c2==null))
			return;
		
		
		
		
		List<Product> lp = new ArrayList<Product>();
		
		
		for(ProductTemplate pt : c1.getProductTemplates())
		{
			int nbProducts = (int)Math.floor(Math.random() * 10);
			
			for(int i = 0; i< nbProducts; i++)
			{
				System.out.println("add to c1");
				lp.add( pt.createProduct(c1) );
			}
		}
		
		for(ProductTemplate pt : c2.getProductTemplates())
		{
			int nbProducts = (int)Math.floor(Math.random() * 10);
			
			for(int i = 0; i< nbProducts; i++)
			{
				System.out.println("add to c2");
				lp.add( pt.createProduct(c2) );
			}
		}
		
		
		try
		{
			et.begin();
			
			em.persist(m);						//to save Commerce and ProducTemplate 
			
			for(Product p : lp)
				em.persist(p);
			
			et.commit();
			
			
		} catch (Exception e) {
			et.rollback();					//undo if troubles
			e.printStackTrace();		
		}
		
		System.out.println("------------------------- merchantStartDay End -------------------------");
	}
	
	
	
	
	
	
	
	
	public static void individualInPromotion()
	{
		GenericDao dao = new GenericDao();
		
		
		Buyer buyer = null;
		ShoppingCart sc = null;
		try {
			List<Buyer> lb = dao.findNamed(Buyer.class, "name", "Gilles Collin", em);
			if(lb.size()==0)
				return;
			buyer = lb.get(0);
			
			List<ShoppingCart> lsc = dao.findBySomething(ShoppingCart.class, "buyer", buyer, em);
			if(lsc.size()!=0)
			{
				sc = lsc.get(0);
				System.out.println("ShoppingCart attendu pour tester le remplissage de la bdd :"+ sc);		//todo remove this print.
				return;
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}


		System.out.println("------------------------- individualInPromotion Start -------------------------");
		
		
		Merchant m = null;
		List<Commerce> lc = new ArrayList<Commerce>();
		
		try {
			List<Merchant> lm = new ArrayList<Merchant>();
			
			lm = dao.findNamed(Merchant.class, "name", "Big Fernand", em);
			if(lm.size()!=0)
			{
				System.out.println("tata");
				m = lm.get(0);
				
				lc = dao.findBySomething(Commerce.class, "merchant", m, em);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if((m==null) || (lc.size()==0))
			return;
		
		
		
		sc = new ShoppingCart(buyer);
		
		List<Product> lp = new ArrayList<Product>();
		
		for(Commerce c : lc)
		{
			List<Product> lpt = c.getProducts();
			
			List<Integer> lpti = new ArrayList<Integer>();
			for(int i = 0; i < lpt.size(); i++)
				lpti.add(i);
			
			int nbProducts = (int)Math.round(Math.random() * lpti.size());
			for(int i = 0;  i< nbProducts; i++)
			{
				int index = (int)Math.round(Math.random() * (lpti.size() - 1));
				
				ShoppingCartByCommerce scbc = sc.getShoppingCartByCommerce(c);
				if(scbc==null)
				{
					scbc = new ShoppingCartByCommerce(c, sc);
					sc.addShoppingCartByCommerces(scbc);
				}
				
				scbc.addProduct( lpt.get(lpti.get(index)) );
				lpti.remove((int)index);
			}
		}
		
		
		try
		{
			et.begin();
			
			em.persist(sc);
			em.persist(m);						//to save Commerce and ProducTemplate
			em.persist(buyer);					//normally also save shoppingCart
			
			for(Product p : lp)
				em.persist(p);
			
			et.commit();
			
			
		} catch (Exception e) {
			et.rollback();					//undo if troubles
			e.printStackTrace();		
		}
		
		System.out.println("------------------------- individualInPromotion End -------------------------");
	}
}
