package fr.dawan.nogashi.tools;

import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailTool 
{
	
	private Properties prop;
	private Authenticator auth;
	
	
	public EmailTool()
	{
	}
	
	public EmailTool(Properties prop, Authenticator auth)
	{
		super();
		this.prop = prop;
		this.auth = auth;
	}
	
	public void sendMail(String dest, String exp, String sujet, String message) throws AddressException, MessagingException
	{
		Session session = Session.getDefaultInstance(prop, auth);
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(exp));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(dest));
		msg.setSubject(sujet, "utf-8");
		msg.setContent(message, "text/plain");		//test/html
		msg.setSentDate(new Date());
		Transport.send(msg);
	}
	public void sendMail_html(String dest, String exp, String sujet, String message) throws AddressException, MessagingException
	{
		Session session = Session.getDefaultInstance(prop, auth);
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(exp));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(dest));
		msg.setSubject(sujet, "utf-8");
		msg.setContent(message, "text/html");
		msg.setSentDate(new Date());
		Transport.send(msg);
	}
	
	public void sendMail_pieceJointe(String dest, String exp, String sujet, String message, String filename) throws AddressException, MessagingException
	{
		Session session = Session.getDefaultInstance(prop, auth);
		
		MimeBodyPart part = new MimeBodyPart();
		part.setText(message);
		
		FileDataSource fds = new FileDataSource(filename);
		MimeBodyPart part2 = new MimeBodyPart();
		part2.setDataHandler(new DataHandler(fds));
		part2.setFileName(fds.getName());
		
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(part);
		mp.addBodyPart(part2);
		
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(exp));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(dest));
		msg.setSubject(sujet, "utf-8");
		msg.setContent(message, "text/plain");		//test/html
		msg.setSentDate(new Date());
		Transport.send(msg);
	}
}
