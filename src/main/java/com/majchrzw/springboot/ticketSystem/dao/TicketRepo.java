package com.majchrzw.springboot.ticketSystem.dao;

import com.majchrzw.springboot.ticketSystem.entity.Ticket;
import com.majchrzw.springboot.ticketSystem.entity.User;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
	
	@Query("select t.description, t.ticketStatus From Ticket t where t.user.email = :email")
	List<Ticket> findTicketsByEmail(@Param("email") String email);

}
