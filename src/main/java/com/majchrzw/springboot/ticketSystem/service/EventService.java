package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.EventRepo;
import com.majchrzw.springboot.ticketSystem.entity.Event;
import com.majchrzw.springboot.ticketSystem.entity.EventForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

	private EventRepo eventRepo;
	@Autowired
	public EventService(EventRepo repo){
		eventRepo = repo;
	}

	public List<Event> findAllEvents(){
		return eventRepo.findAll();
	}
	
	public void saveEventFromForm( EventForm eventForm){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-" +
				"MM-dd");
		Date eventDate;
		try {
			eventDate = dateFormat.parse(eventForm.getDate());
		} catch ( ParseException e){
			System.out.println("Cannot get date of event");
			return;
		}
		Event event = new Event();
		event.setDate(eventDate);
		event.setId(eventForm.getId());
		event.setDescription(eventForm.getDescription());
		event.setName(eventForm.getName());
		eventRepo.save(event);
	}
	
	public void saveEvent(Event event){
		eventRepo.save(event);
	}
	
	public void deleteEvent(long id){
		eventRepo.deleteById(id);
	}
	
	public Event getEventById(long id){
		if(eventRepo.findById(id).isPresent()) {
			return eventRepo.findById(id).get();
		} else
			throw new IllegalArgumentException("No event with id:" + id + " found!");
	}
	
}
