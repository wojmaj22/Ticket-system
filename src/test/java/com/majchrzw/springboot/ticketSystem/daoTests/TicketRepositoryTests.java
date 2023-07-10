package com.majchrzw.springboot.ticketSystem.daoTests;
/*
import com.majchrzw.springboot.ticketSystem.dao.EventRepo;
import com.majchrzw.springboot.ticketSystem.dao.TicketRepo;
import com.majchrzw.springboot.ticketSystem.dao.TicketTypeRepo;
import com.majchrzw.springboot.ticketSystem.dao.UserRepo;
import com.majchrzw.springboot.ticketSystem.entity.Event;
import com.majchrzw.springboot.ticketSystem.entity.Ticket;
import com.majchrzw.springboot.ticketSystem.entity.TicketType;
import com.majchrzw.springboot.ticketSystem.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TicketRepositoryTests {
	@Autowired
	private TicketRepo repo;
	
	private Event tempEvent;
	private TicketType ticketType;
	private User tempUser;
	
	private Ticket testTicket;
	
	@Autowired
	private EventRepo eventRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TicketTypeRepo ticketTypeRepo;
	
	int id;
	
	@Before
	public void setUp(){
		tempUser = userRepo.findById("testEmail@test.com").get();
		ticketType = ticketTypeRepo.findById(1).get();
		tempEvent = eventRepo.findById(1L).get();
		testTicket = new Ticket( tempUser, "ticketStatus", "ticket description", ticketType);
		id = repo.save(testTicket).getId();
	}
	
	@After
	public void cleanUp(){
		repo.deleteById(id);
	}
	
	@Test
	public void shouldSaveTicketToRepository() {
		Assertions.assertTrue(repo.findById(id).isPresent());
	}
	
	@Test
	public void shouldGetTicketFromRepository() {
		// when
		Ticket testTicket = repo.findById(id).get();
		// then
		assertEquals(testTicket.getId(), id);

	}
	
	@Test
	public void shouldGetListOfUsersFromRepository() {
		List<Ticket> tickets = repo.findAll();
		
		Assertions.assertTrue(tickets.size() != 0);
	}
	
	@Test
	public void shouldUpdateUserFromRepository() {
		
		String ticketStatus = "newStatus";
		// when
		Ticket tempTicket = repo.findById(id).get();
		tempTicket.setTicketStatus(ticketStatus);
		repo.save(tempTicket);
		
		assertEquals(ticketStatus, repo.findById(id).get().getTicketStatus());
	}
	
}
*/