package fr.dawan.nogashi.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Entity
@Component
public class FaqQuestion extends DbObject {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String question;
	@Column(nullable = false)
	private String answer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Faq faq;
	
	
	

	public FaqQuestion(String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
	}

	//----------------------------------------
	
	public FaqQuestion() {
		super();
	}
	
	@Override
	public String toString() {
		return "FaqQuestion [question=" + question + ", answer=" + answer + "]";
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Faq getFaq() {
		return faq;
	}

	public void setFaq(Faq faq) {
		this.faq = faq;
	}
	
	
}
