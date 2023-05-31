package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.TicketRepo;
import com.majchrzw.springboot.ticketSystem.entity.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class TicketService {
	
	private TicketRepo ticketRepo;
	
	public TicketService( TicketRepo ticketRepo){
		this.ticketRepo = ticketRepo;
	}
	
	public Ticket findTicketById(int id){
		return ticketRepo.findById(id).get();
	}
	
	public void saveTicket(Ticket ticket){
		ticketRepo.save(ticket);
	}
	
	public List<Ticket> findTicketsByEmail(String email){
		return ticketRepo.findTicketsByEmail(email);
	}
}
