package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private User user;
	
	@Column(name = "order_date")
	private Date orderDate;
	
	@Column(name = "order_status")
	private String status;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetails> detailsList;
	
	public Order(User user, Date orderDate, String status) {
		this.user = user;
		this.orderDate = orderDate;
		this.status = status;
	}
}
