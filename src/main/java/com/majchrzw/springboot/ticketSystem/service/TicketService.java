package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.TicketRepo;
import com.majchrzw.springboot.ticketSystem.entity.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {
	
	private TicketRepo ticketRepo;
	
	@Autowired
	public TicketService( TicketRepo ticketRepo){
		this.ticketRepo = ticketRepo;
	}
	
	public Ticket findTicketById(int id){
		Optional<Ticket> ticketOptional = ticketRepo.findById(id);
		if( ticketOptional.isEmpty()){
			throw new RuntimeException("Ticket with id:" + id + " ,does not exist!");
		}
		return ticketOptional.get();
	}
	
	public void saveTicket(Ticket ticket){
		ticketRepo.save(ticket);
	}
	
	public List<Ticket> findTicketsByEmail(String email){
		return ticketRepo.findTicketsByEmail(email);
	}
}
