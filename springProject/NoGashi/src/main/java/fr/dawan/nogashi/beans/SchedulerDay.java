package fr.dawan.nogashi.beans;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Component
public class SchedulerDay extends DbObject {
	
	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.ORDINAL)
	private DayOfWeek day = DayOfWeek.MONDAY;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SchedulerHoursRange> hoursRanges = new ArrayList<SchedulerHoursRange>();
	
	
	@ManyToOne @XmlTransient @JsonIgnore
	private SchedulerWeek parent;
	
	
	
	public void addHoursRanges(SchedulerHoursRange hr) {
		if(! this.hoursRanges.contains(hr))
		{
			this.hoursRanges.add(hr);
			hr.setParent(this);
		}
	}
	
	
	public SchedulerDay(SchedulerDay other, SchedulerWeek parent) {				//constructeur de copie
		super();
		this.day = other.day;
		this.parent = parent;
		
		for(SchedulerHoursRange hr : other.hoursRanges)
			this.hoursRanges.add( new SchedulerHoursRange(hr, this));
	}
	
	
	
	//-------------------------------
	

	public SchedulerDay(DayOfWeek day, SchedulerWeek parent) {
		super();
		this.day = day;
		this.parent = parent;
	}




	public DayOfWeek getDay() {
		return day;
	}





	public void setDay(DayOfWeek day) {
		this.day = day;
	}



	public List<SchedulerHoursRange> getHoursRanges() {
		return hoursRanges;
	}



	public void setHoursRanges(List<SchedulerHoursRange> hoursRanges) {
		this.hoursRanges = hoursRanges;
	}



	@Override
	public String toString() {
		return "SchedulerDay [day=" + day + "]";
	}



	public SchedulerDay() {
		super();
	}



	public SchedulerWeek getParent() {
		return parent;
	}



	public void setParent(SchedulerWeek parent) {
		this.parent = parent;
	}
	
	
	

}
