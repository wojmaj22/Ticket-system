package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ticket_type")
public class TicketType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToMany( mappedBy = "ticketType", cascade = CascadeType.ALL)
	private List<Ticket> ticket;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "price")
	private float price;
	
	@ManyToOne()
	@JoinColumn( name = "event_id")
	private Event event;
	
	@OneToMany(mappedBy = "ticketType", cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetails;
	
	public TicketType(String type, float price, Event event) {
		this.type = type;
		this.price = price;
		this.event = event;
	}
}
