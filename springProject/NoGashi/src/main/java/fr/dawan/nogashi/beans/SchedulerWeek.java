package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.SchedulerWeekType;

// the goal is to have open commerce shedule (horaires d'ouvertures), or the schedule product are in prompotion , or unsold. To be displayed inside a graphic week calendar.
// for that , there is a Week , witch have days, witch have ranges of time in day, with diferrent type on Week for distainct differents use (and so colors) 
// to regroup the display, there is a Type Group on week , to have the other type , as some layouts.


@Entity
@Component
public class SchedulerWeek extends DbObject {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String name;									//name and description are for inernal use (because different context will use this classe, but all are save in the same table, so )
	private String description; 
	
	@Enumerated(EnumType.ORDINAL)
	private SchedulerWeekType type = SchedulerWeekType.OPEN;	// with the type, you will could have many layout
	
	@OneToMany
	private List<SchedulerWeek> group = new ArrayList<SchedulerWeek>();				// the case with many layouts.
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private List<SchedulerDay> days = new ArrayList<SchedulerDay>();
	
	
	
	public void addDays(SchedulerDay d) {
		if(! this.days.contains(d))
		{
			this.days.add(d);
			d.setParent(this);
		}
	}
	
	public void addDays(SchedulerWeek w) {
		if(!this.group.contains(w))
			this.group.add(w);
	}


	
	
	//---------------------------------
	

	public SchedulerWeek(String name, String description, SchedulerWeekType type) {
		super();
		this.name = name;
		this.description = description;
		this.type = type;
	}



	public String getName() {
		return name;
	}



	public SchedulerWeek() {
		super();
	}




	@Override
	public String toString() {
		return "SchedulerWeek [name=" + name + ", description=" + description + ", type=" + type + "]";
	}


	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public SchedulerWeekType getType() {
		return type;
	}



	public void setType(SchedulerWeekType type) {
		this.type = type;
	}



	public List<SchedulerWeek> getGroup() {
		return group;
	}



	public void setGroup(List<SchedulerWeek> group) {
		this.group = group;
	}



	public List<SchedulerDay> getDays() {
		return days;
	}



	public void setDays(List<SchedulerDay> days) {
		this.days = days;
	}
	
}
