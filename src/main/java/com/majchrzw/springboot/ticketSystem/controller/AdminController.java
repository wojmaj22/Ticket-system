package com.majchrzw.springboot.ticketSystem.controller;

import com.majchrzw.springboot.ticketSystem.entity.Event;
import com.majchrzw.springboot.ticketSystem.entity.EventForm;
import com.majchrzw.springboot.ticketSystem.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
// TODO - dodać zarządzanie użytkownikami
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private EventService eventService;
	
	@Autowired
	public AdminController(EventService eventService){
		this.eventService = eventService;
	}
	
	@GetMapping("/")
	public String admin(){
		return "admin/admin";
	}
	
	@GetMapping("/events")
	public String listEvent(Model model){
		List<Event> events = eventService.findAllEvents();
		
		model.addAttribute("events", events);
		
		return "admin/events";
	}
	
	@GetMapping("/add_event")
	public String addEvent(Model model){
		EventForm eventForm = new EventForm();
		
		model.addAttribute("eventForm", eventForm);
		
		return "admin/add_event";
	}
	
	@PostMapping("/saveEvent")
	public String saveEvent(@ModelAttribute("eventForm") @Valid EventForm eventForm, BindingResult bindingResult){
		if( bindingResult.hasErrors()){
			return "add_event";
		}
		eventService.saveEventFromForm(eventForm);
		return "redirect:/admin/events";
	}
	
	@GetMapping("/deleteEvent")
	public String deleteEvent(@RequestParam("eventId") long eventId){
		eventService.deleteEvent(eventId);
		return "redirect:/admin/events";
	}
	
	@GetMapping("/updateEvent")
	public String updateEvent(@RequestParam("eventId") long eventId, Model model){
		Event tempEvent = eventService.getEventById(eventId);
		
		EventForm form = tempEvent.changeEventToForm();
		
		model.addAttribute("event", eventService.getEventById(eventId));
		model.addAttribute( "date", tempEvent.getDate());
		
		return "admin/update_event";
	}
	
	@PostMapping("/processUpdate")
	public String processUpdate(@ModelAttribute("event") Event updatedEvent){
		eventService.saveEvent(updatedEvent);
		
		return "redirect:/admin/events";
	}
	
}
