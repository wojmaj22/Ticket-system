package com.majchrzw.springboot.ticketSystem.controller;

import com.majchrzw.springboot.ticketSystem.entity.RegisterForm;
import com.majchrzw.springboot.ticketSystem.entity.User;
import com.majchrzw.springboot.ticketSystem.service.EventService;
import com.majchrzw.springboot.ticketSystem.service.TicketService;
import com.majchrzw.springboot.ticketSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
	
	private final UserService userService;
	
	private final EventService eventService;
	private final TicketService ticketService;
	
	@Autowired
	public MainController(UserService userService, TicketService ticketService, EventService eventService) {
		this.userService = userService;
		this.ticketService = ticketService;
		this.eventService = eventService;
	}
	
	@GetMapping("/home")
	public String homeReturn(){
		return "home";
	}
	
	@GetMapping("/")
	public String mainHome(){
		return "home";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(){
		return "accessdenied";
	}
	
	@PostMapping(value = "/process-register")
	public String registerUser(@ModelAttribute("form") @Valid RegisterForm rF, BindingResult bindingResult){
		if( bindingResult.hasErrors()){
			return "register";
		}
		User newUser = new User( rF.getUsername(), rF.getPassword(), rF.getEmail(), true);
		try{
			userService.registerUser(newUser);
		} catch ( IllegalArgumentException e){
			return "redirect:/register?emailerror=emailtaken";
		}
		return "login";
	}
	
	@GetMapping("/register")
	public String register(Model model){
		RegisterForm registerForm = new RegisterForm();
		model.addAttribute("form", registerForm);
		
		return "register";
	}
	
	
	
}

