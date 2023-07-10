package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	//@OneToMany( mappedBy = "user",  cascade = CascadeType.ALL)
	//private List<Ticket> tickets;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> orders;
	
	public User(String username, String password, String email, boolean enabled) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
	}
}
