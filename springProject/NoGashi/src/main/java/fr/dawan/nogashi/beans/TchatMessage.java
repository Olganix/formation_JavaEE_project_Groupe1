package fr.dawan.nogashi.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Entity
@Component
public class TchatMessage extends DbObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	private String messageContent;
	
	@Basic(optional = false)
	private LocalDateTime date;
	
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
