package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	@Column(name = "event_description")
	private String description;
	
	@Column(name = "event_date")
	private Date date;
	@OneToMany( mappedBy = "event", cascade = CascadeType.ALL)
	private List<Ticket> ticketList;
	
	public Event(){}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public List<Ticket> getTicketList() {
		return ticketList;
	}
	
	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Event(String name) {
		this.name = name;
	}
	
	public EventForm changeEventToForm(){
		EventForm form = new EventForm(this.id, this.name, this.description, this.date.toString());
		return form;
	}
}
