package fr.dawan.nogashi.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Component
public class SchedulerHoursRange extends DbObject {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private int startTime;
	@Column(nullable = false)
	private int endTime;
	
	@ManyToOne  @XmlTransient @JsonIgnore
	private SchedulerDay parent;
	
	
	
	
	
	
	
	public SchedulerHoursRange(SchedulerHoursRange other, SchedulerDay parent) {					//constructeurde copie
		super();
		this.startTime = other.startTime;
		this.endTime = other.endTime;
		this.parent = parent;
	}
	
	
	
	//---------------------------------

	@Override
	public String toString() {
		return "SchedulerHoursRange [startTime=" + startTime + ", endTime=" + endTime + "]";
	}


	public SchedulerHoursRange(int startTime, int endTime, SchedulerDay parent) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.parent = parent;
	}


	public int getStartTime() {
		return startTime;
	}


	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}


	public int getEndTime() {
		return endTime;
	}


	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}


	public SchedulerHoursRange() {
		super();
	}


	public SchedulerDay getParent() {
		return parent;
	}


	public void setParent(SchedulerDay parent) {
		this.parent = parent;
	}

	
}
