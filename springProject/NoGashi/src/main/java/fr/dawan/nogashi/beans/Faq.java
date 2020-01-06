package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Faq extends DbObject {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "faq")
	private List<FaqQuestion> questions = new ArrayList<FaqQuestion>();
	
	
	
	
	public Faq(String name) {
		super();
		this.name = name;
	}

	
	public void addQuestions(FaqQuestion q) {
		questions.add(q);
	}
	public void removeQuestions(FaqQuestion q) {
		if(questions.contains(q))
			questions.remove(q);
	}
	public void clearQuestions() {
			questions.clear();
	}
	
	
	//----------------------------------------------------------------

	public Faq() {
		super();
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<FaqQuestion> getQuestions() {
		return questions;
	}


	public void setQuestions(List<FaqQuestion> questions) {
		this.questions = questions;
	}


	@Override
	public String toString() {
		return "Faq [name=" + name + "]";
	}
	
	
	
	
}
