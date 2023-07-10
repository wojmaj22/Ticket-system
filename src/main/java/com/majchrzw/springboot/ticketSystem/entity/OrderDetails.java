package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_details")
public class OrderDetails {
	
	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn( name = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn( name = "ticket_type_id")
	private TicketType ticketType;
	
	@Column(name = "amount")
	private int amount;
	
	@Column(name = "price")
	private double price;
	
	public OrderDetails(Order order, TicketType ticketType, int amount, double price) {
		this.order = order;
		this.ticketType = ticketType;
		this.amount = amount;
		this.price = price;
	}
}
