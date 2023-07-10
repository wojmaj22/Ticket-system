package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.TicketTypeRepo;
import com.majchrzw.springboot.ticketSystem.entity.Event;
import com.majchrzw.springboot.ticketSystem.entity.TicketType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketTypeService {
	
	TicketTypeRepo repo;
	
	@Autowired
	public TicketTypeService(TicketTypeRepo repo) {
		this.repo = repo;
	}
	
	public void saveTicketType(TicketType type){
		repo.save(type);
	}
	
	public void deleteTicketType(int id){
		repo.deleteById(id);
	}
	
	public TicketType findTicketType(int id){
		Optional<TicketType> type = repo.findById(id);
		if( type.isEmpty()){
			throw new RuntimeException("No ticket type with id:" + id + " found!");
		}
		return type.get();
	}
	
	public List<TicketType> findAllTicketTypes(){
		return repo.findAll();
	}
	
	public List<TicketType> findAllTicketTypesForEvent( Event event){
		return repo.findAllByEvent(event);
	}
}
