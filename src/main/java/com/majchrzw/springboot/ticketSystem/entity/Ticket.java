package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn( name = "email")
	private User user;

	@Column( name = "ticket_status")
	private String ticketStatus;
	@Column( name = "description")
	private String description;
	
	@ManyToOne()
	@JoinColumn( name = "ticket_type")
	private TicketType ticketType;
	
	public Ticket(User user, String ticketStatus, String description, TicketType ticketType) {
		this.user = user;
		this.ticketStatus = ticketStatus;
		this.description = description;
		this.ticketType = ticketType;
	}
}
