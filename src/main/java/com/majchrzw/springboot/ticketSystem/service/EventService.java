package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.EventRepo;
import com.majchrzw.springboot.ticketSystem.entity.Event;
import com.majchrzw.springboot.ticketSystem.entity.EventForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

	private EventRepo eventRepo;
	
	private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	@Autowired
	public EventService(EventRepo repo){
		eventRepo = repo;
	}

	public List<Event> findAllEvents(){
		return eventRepo.findAll();
	}
	
	public void saveEventFromForm( EventForm eventForm) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date eventDate;
		try {
			eventDate = dateFormat.parse(eventForm.getDate());
		} catch ( ParseException e){
			System.out.println("Cannot get date of event");
			return;
		}
		// TODO - co jeśli wgra się pliik o nazwie która już istnieje
		Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, eventForm.getPhoto().getOriginalFilename());
		Files.write(fileNameAndPath, eventForm.getPhoto().getBytes());
		
		Event event = new Event();
		event.setDate(eventDate);
		event.setId(eventForm.getId());
		event.setDescription(eventForm.getDescription());
		event.setName(eventForm.getName());
		event.setPhotoPath(eventForm.getPhoto().getOriginalFilename());
		eventRepo.save(event);
	}
	
	public void saveEvent(Event event){
		eventRepo.save(event);
	}
	
	public void deleteEvent(long id) throws IOException{
		String photoPath = getEventById(id).getPhotoPath();
		Files.delete(Paths.get(UPLOAD_DIRECTORY, photoPath));
		eventRepo.deleteById(id);
	}
	
	public Event getEventById(long id){
		if(eventRepo.findById(id).isPresent()) {
			return eventRepo.findById(id).get();
		} else
			throw new IllegalArgumentException("No event with id:" + id + " found!");
	}
	
	public Page<Event> findEventsPageable(Pageable pageable){
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		
		List<Event> events = eventRepo.findAll();
		int toIndex = Math.min(startItem + pageSize, events.size());
		
		return new PageImpl<>(events.subList(startItem,toIndex), pageable, events.size());
	}
	
	public List<Event> findEventByName(String name){
		return eventRepo.findByNameContaining(name);
	}
	
	public Event findEventById(Long id){
		Optional<Event> event = eventRepo.findById(id);
		if( event.isPresent()){
			return event.get();
		} else {
			throw new EntityNotFoundException("No event with id: " + id + " has been found.");
		}
	}
	
}
