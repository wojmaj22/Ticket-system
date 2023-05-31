package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Set;

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
	
	public List<Ticket> getTickets() {
		return tickets;
	}
	
	@OneToMany( mappedBy = "user",  cascade = CascadeType.REMOVE)
	private List<Ticket> tickets;
	
	public User() {
	}
	
	public User(String username, String password, String email, boolean enabled) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
