package com.majchrzw.springboot.ticketSystem.dao;

import com.majchrzw.springboot.ticketSystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
	List<Event> findByNameContaining(String name);
	
}
