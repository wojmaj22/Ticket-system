package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class EventForm {
	
	private long id;
	
	@NotEmpty( message = "Event name is needed!")
	private String name;
	@NotEmpty( message = "Must have a description!")
	private String description;
	
	@NotEmpty( message = "Must have a date of event!")
	private String date;
	

	public MultipartFile getPhoto() {
		return photo;
	}
	
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
	
	private MultipartFile photo;

	public EventForm(long id, String name, String description, String date, MultipartFile photo) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
		this.photo = photo;
	}
	
	public EventForm(long id, String name, String description, String date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
	}
	
	public EventForm() {
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
