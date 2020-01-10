package fr.dawan.nogashi.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

@Entity
@Component
public class TchatMessage extends DbObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Todo revoir la classe (meme si l'exemple de Joffrey marche, il y a une communication trop global, il faut que la communication se fasse d'un utilisateur a un autre , (ajout notion de room ? gestion de chargement des anciens messages, etc ...) )

	@Column(nullable = false)
	private String messageContent;
	@Column(nullable = false)
	private LocalDateTime date;
	
	@OneToOne
	private User authorMessage;
		
	
	public TchatMessage() {
	}


	public TchatMessage(String messageContent, LocalDateTime date) {
		super();
		this.messageContent = messageContent;
		this.date = date;
	}


	public TchatMessage(String messageContent, LocalDateTime date, User authorMessage) {
		super();
		this.messageContent = messageContent;
		this.date = date;
		this.authorMessage = authorMessage;
	}


	public String getMessageContent() {
		return messageContent;
	}


	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public User getAuthorMessage() {
		return authorMessage;
	}


	public void setAuthorMessage(User authorMessage) {
		this.authorMessage = authorMessage;
	}


	@Override
	public String toString() {
		return "TchatMessage [messageContent=" + messageContent + ", date=" + date + "]";
	}



}
