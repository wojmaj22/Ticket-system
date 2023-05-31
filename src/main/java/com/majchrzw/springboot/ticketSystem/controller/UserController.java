package com.majchrzw.springboot.ticketSystem.controller;


import com.majchrzw.springboot.ticketSystem.entity.Event;
import com.majchrzw.springboot.ticketSystem.entity.Ticket;
import com.majchrzw.springboot.ticketSystem.entity.User;
import com.majchrzw.springboot.ticketSystem.security.SecurityConfig;
import com.majchrzw.springboot.ticketSystem.service.EventService;
import com.majchrzw.springboot.ticketSystem.service.TicketService;
import com.majchrzw.springboot.ticketSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	private EventService eventService;
	private TicketService ticketService;

	@Autowired
	public UserController(UserService userService, EventService eventService, TicketService ticketService){
		this.userService = userService;
		this.eventService = eventService;
		this.ticketService = ticketService;
	}
	
	@GetMapping("/tickets")
	public String getUserTickets(Model model, Authentication authentication){
		
		User theUser = userService.findUserByEmail(authentication.getName()); // TODO - tutaj ma być wczytywanie biletów w zależności od użytkownika
		
		model.addAttribute("ticketsList", theUser.getTickets());
		
		return "user/tickets";
	}
	
	@GetMapping("/events")
	public String getEvents(Model model){
		
		model.addAttribute("eventList", eventService.findAllEvents());
		
		return "user/events";
	}
	
	@GetMapping("/buy-ticket")
	public String buyTicket(@RequestParam(name = "eventId") long eventId, Authentication authentication){
		Ticket theTicket = new Ticket();
		Event event = eventService.getEventById(eventId);
		
		theTicket.setTicketStatus("bought");
		theTicket.setEvent(event);
		theTicket.setUser(userService.findUserByEmail(authentication.getName()));
		theTicket.setDescription("new ticket");
		
		ticketService.saveTicket(theTicket);
		
		return "redirect:/user/tickets";
	}
	
}
