package fr.dawan.nogashi.beans;

import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;


@Entity
@Component
public class SchedulerHoursRange extends DbObject {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private LocalTime startTime;
	@Column(nullable = false)
	private LocalTime endTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private SchedulerDay parent;
	
	//---------------------------------

	@Override
	public String toString() {
		return "SchedulerHoursRange [startTime=" + startTime + ", endTime=" + endTime + "]";
	}


	public SchedulerHoursRange(LocalTime startTime, LocalTime endTime, SchedulerDay parent) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.parent = parent;
	}


	public LocalTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}


	public LocalTime getEndTime() {
		return endTime;
	}


	public void setEndTime(LocalTime endTime) {
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
