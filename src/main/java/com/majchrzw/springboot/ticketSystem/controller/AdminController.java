package com.majchrzw.springboot.ticketSystem.controller;

import com.majchrzw.springboot.ticketSystem.entity.*;
import com.majchrzw.springboot.ticketSystem.service.EventService;
import com.majchrzw.springboot.ticketSystem.service.TicketTypeService;
import com.majchrzw.springboot.ticketSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private EventService eventService;
	
	private UserService userService;
	
	private TicketTypeService ticketTypeService;
	
	@Autowired
	public AdminController(EventService eventService, TicketTypeService ticketTypeService, UserService userService){
		this.eventService = eventService;
		this.ticketTypeService = ticketTypeService;
		this.userService = userService;
	}
	
	@GetMapping("/") // returns main admin page
	public String admin(){
		return "admin/admin";
	}
	
	@GetMapping("/events/list") // return a list of available events
	public String listEvent(Model model){
		List<Event> events = eventService.findAllEvents();
		
		model.addAttribute("events", events);
		
		return "admin/events/list";
	}
	
	@GetMapping("/events/add") // return page for adding a new event
	public String addEvent(Model model){
		EventForm eventForm = new EventForm();
		model.addAttribute("eventForm", eventForm);
		
		return "admin/events/add";
	}
	
	@PostMapping("/events/save") // mapping for saving event, redirects to list of events or to form if there is an error is form
	public String saveEvent(@ModelAttribute("eventForm") @Valid EventForm eventForm, BindingResult bindingResult, @RequestParam("image") MultipartFile file) throws IOException {
		if( bindingResult.hasErrors()){
			return "admin/events/add";
		}
		eventForm.setPhoto(file);
		eventService.saveEventFromForm(eventForm);
		return "redirect:/admin/events/list";
	}
	
	@GetMapping("/events/delete") // mapping for deleting event, after deletion redirects to list
	public String deleteEvent(@RequestParam("eventId") long eventId) throws IOException{
		eventService.deleteEvent(eventId);
		return "redirect:/admin/events/list";
	}
	
	@GetMapping("/events/update") // page for updating event
	public String updateEvent(@RequestParam("eventId") long eventId, Model model){
		
		model.addAttribute("event", eventService.getEventById(eventId));
		
		return "admin/events/update";
	}
	
	@PostMapping("/process_update") // mapping for POST of update event
	public String processUpdate(@ModelAttribute("event") Event updatedEvent){
		eventService.saveEvent(updatedEvent);
		
		return "redirect:/admin/events";
	}
	
	@GetMapping("/ticket_types/add") // page for adding new ticket type for an event
	public String addTicketTypeToEvent(@RequestParam("eventId") long eventId, Model model){
		TicketType ticketType = new TicketType();
		Event event = eventService.getEventById(eventId);
		ticketType.setEvent(event);
		model.addAttribute( "ticketType", ticketType);
		
		return "/admin/ticket_types/add";
	}
	
	@PostMapping("/ticket_types/save_type") // POST mapping for saving ticket type, redirects to list of events
	public String saveTicketType(@ModelAttribute("ticketType")TicketType ticketType){
		
		ticketTypeService.saveTicketType(ticketType);
		
		return "redirect:/admin/ticket_types/list?eventId=" + ticketType.getEvent().getId();
	}
	
	@GetMapping("/ticket_types/list")
	public String getListOfTicketTypesForEvent(@RequestParam("eventId") long eventId, Model model) {
		Event event = eventService.getEventById(eventId);
		
		List<TicketType> ticketTypes = event.getTicketList();
		model.addAttribute( "event", event);
		model.addAttribute( "ticketTypes", ticketTypes);
		
		return "/admin/ticket_types/list";
	}
	
	@GetMapping("/ticket_types/delete") // page for adding new ticket type for an event
	public String deleteTicketTypeFromEvent(@RequestParam("typeId") int typeId){
		Event event = ticketTypeService.findTicketType(typeId).getEvent();
		ticketTypeService.deleteTicketType(typeId);
		return "redirect:/admin/ticket_types/list?eventId=" + event.getId() ;
	}
	
	@GetMapping("/users/search") // search for user
	public String browseUsers(){
		return "/admin/users/search";
	}
	
	@PostMapping("/users/search")
	public String searchUsers(@RequestParam("email") String email, Model model){
		System.out.println(email);
		// TODO - w liście user dodać przeglądanie stron w liście
		Page<User> userPage = userService.findPaginated(PageRequest.of(0,20), email);
		model.addAttribute("userPage", userPage);
		
		return "/admin/users/list";
	}
	
	@GetMapping("/users/edit")
	public String editUserRoles(@RequestParam("email") String email, Model model, Authentication authentication){
		User user = userService.findUserByEmail(email);
		Collection<? extends GrantedAuthority> authorities = userService.findUserRoles(authentication, user);
		ArrayList<GrantedAuthority> authorityArrayList = new ArrayList<>(authorities);
		UserAuthorityForm form = new UserAuthorityForm(user.getEmail(), "");
		UserEnableForm enableForm = new UserEnableForm(user.getEmail(),user.isEnabled());
		
		model.addAttribute("user", user);
		model.addAttribute("authorities", authorityArrayList);
		model.addAttribute("form", form);
		model.addAttribute("enableForm", enableForm);
		
		return "/admin/users/edit";
	}
	
	@PostMapping("/users/add_authority")
	public String addUserAuthority(@ModelAttribute("form") UserAuthorityForm form){
		String role = "ROLE_" + form.getAuthority().toUpperCase();
		System.out.println(role);
		userService.addNewAuthority(form.getEmail(),role);
		return "redirect:/admin/";
	}
	
	@PostMapping("/users/enable")
	public String editUserEnable(@ModelAttribute("enableForm") UserEnableForm form){
		userService.changeUserEnable(form);
		return "redirect:/admin/";
	}
	
	@GetMapping("/users/delete_authority")
	public String deleteAuthority(@RequestParam("authority") String authority, @RequestParam("email") String email){
		userService.deleteAuthorityFromUser(email, authority);
		return "redirect:/admin/";
	}
	
}
