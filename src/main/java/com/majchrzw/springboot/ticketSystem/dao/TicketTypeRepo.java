package com.majchrzw.springboot.ticketSystem.dao;

import com.majchrzw.springboot.ticketSystem.entity.Event;
import com.majchrzw.springboot.ticketSystem.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketTypeRepo extends JpaRepository<TicketType, Integer> {
	
	public List<TicketType> findAllByEvent(Event event);
}
