package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	@Column(name = "event_description")
	private String description;
	
	@Column(name = "photo_path")
	private String photoPath;
	@Column(name = "event_date")
	private Date date;
	@OneToMany( mappedBy = "event", cascade = CascadeType.ALL)
	private List<TicketType> ticketList;
	
	public Event(long id, String name, String description, Date date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
	}
	
	public Event(String name, String description, Date date) {
		this.name = name;
		this.description = description;
		this.date = date;
	}
	
	public EventForm changeEventToForm(){
		return new EventForm(this.id, this.name, this.description, this.date.toString());
	}
}
