package com.majchrzw.springboot.ticketSystem.controller;


import com.majchrzw.springboot.ticketSystem.entity.*;
import com.majchrzw.springboot.ticketSystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

//TODO - add mapping for /events/ticket_types and /events/ticket_types/buy and /profile
@Controller
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	private EventService eventService;
	private TicketTypeService ticketTypeService;
	private OrderService orderService;
	private OrderDetailsService orderDetailsService;

	@Autowired
	public UserController(UserService userService, EventService eventService, TicketTypeService ticketTypeService, OrderService orderService, OrderDetailsService orderDetailsService){
		this.userService = userService;
		this.eventService = eventService;
		this.ticketTypeService = ticketTypeService;
		this.orderService = orderService;
		this.orderDetailsService = orderDetailsService;
	}
	
	@GetMapping("/tickets/list") // returns page with list of tickets that user bought
	public String getUserTickets(Model model, Authentication authentication){
		
		User theUser = userService.findUserByEmail(authentication.getName());
		// TODO - to jest do poprawy bo bilety już nie istnieją
		//model.addAttribute("ticketsList", theUser.getTickets());
		
		return "user/tickets/list";
	}
	
	@GetMapping("/events/list") // gets list of available events
	public String getEvents(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
		int currentPage = page.orElse(0);
		int currentSize = size.orElse(2);
		Page<Event> eventPage = eventService.findEventsPageable(PageRequest.of(currentPage,currentSize));
		
 		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentSize", currentSize);
		model.addAttribute("events", eventPage.getContent());
		model.addAttribute("hasNext", eventPage.hasNext());
		model.addAttribute("hasPrevious", eventPage.hasPrevious());
		
		return "user/events/list";
	}
	
	@GetMapping("/events/search")
	public String getSearchedEvents(@RequestParam("name") String name, Model model){
		List<Event> events;
		if(name.isEmpty()) {
			events = eventService.findAllEvents();
		} else {
			events = eventService.findEventByName(name);
		}
		model.addAttribute("events", events);
		model.addAttribute("currentPage", 0);
		model.addAttribute("currentSize", events.size());
		model.addAttribute("hasNext", false);
		model.addAttribute("hasPrevious", false);
		
		return "user/events/list";
		}
	
	@GetMapping("/events/ticket_types")
	public String buyTicket(@RequestParam(name = "eventId") long eventId, Model model){
		Event tempEvent = eventService.findEventById(eventId);
		List<TicketType> ticketTypeList = ticketTypeService.findAllTicketTypesForEvent(tempEvent);
		Event event = eventService.getEventById(eventId);
		
		model.addAttribute("event", event);
		model.addAttribute("ticketTypes", ticketTypeList);
		
		return "user/events/ticket_types/list";
	}
	
	@GetMapping("/events/ticket_types/add_to_cart")
	public String addTicketToCart(@RequestParam(name = "ticketType") int id, Authentication authentication){
		User user = userService.findUserByEmail(authentication.getName());
		Order order = orderService.findOrderByUserAndStatus(user, "new");
		TicketType ticketType = ticketTypeService.findTicketType(id);
		
		orderDetailsService.saveNewOrderDetails(ticketType, order);
		return "redirect:user/cart";
	}
	
	@GetMapping("/cart")
	public String showCart(Model model, Authentication authentication){
		User user = userService.findUserByEmail(authentication.getName());
		Order order = orderService.findOrderByUserAndStatus(user, "new");
		List<OrderDetails> items = orderDetailsService.findAllOrderDetailsByOrder(order);
		
		model.addAttribute("items", items);
		return "user/cart";
	}
	
}
