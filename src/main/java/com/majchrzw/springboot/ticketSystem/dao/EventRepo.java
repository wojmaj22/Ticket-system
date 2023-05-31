package com.majchrzw.springboot.ticketSystem.dao;

import com.majchrzw.springboot.ticketSystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {
}
