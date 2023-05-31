package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn( name = "email", nullable = false)
	private User user;

	@Column( name = "ticket_status")
	private String ticketStatus;
	@Column( name = "description")
	private String description;
	@ManyToOne
	@JoinColumn( name = "event_id", nullable = false)
	private Event event;
	
	public Ticket() {
	}
	
	public Ticket(User user, String ticketStatus, String description, Event event) {
		this.user = user;
		this.ticketStatus = ticketStatus;
		this.description = description;
		this.event = event;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getTicketStatus() {
		return ticketStatus;
	}
	
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Event getEvent(){
		return event;
	}
	
	public void setEvent(Event theEvent){
		event = theEvent;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
