package com.majchrzw.springboot.ticketSystem.daoTests;

import com.majchrzw.springboot.ticketSystem.dao.EventRepo;
import com.majchrzw.springboot.ticketSystem.entity.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class EventRepositoryTests {
	
	@Autowired
	private EventRepo repo;
	
	private Event event;

	long id;
	@Before
	public void setUp(){
		event = new Event( "testEvent", "description of event", Date.valueOf("2022-11-10"));
		id =  repo.save(event).getId();
	}
	
	@After
	public void cleanUp(){
		repo.delete(event);
	}
	
	@Test
	public void shouldSaveToDB(){
		Assertions.assertTrue( repo.findById(id).isPresent());
	}
	
	@Test
	public void shouldGetFromDB(){
		Event tempEvent = repo.findById(id).get();
		
		assertEquals( tempEvent.getName(), event.getName());
		assertEquals( tempEvent.getId(), id);
	}
	
	@Test
	public void shouldUpdateEvent(){
		Event tempEvent = repo.findById(id).get();
		String tDescription = "new Description";
		
		tempEvent.setDescription(tDescription);
		repo.save(tempEvent);
		assertEquals( repo.findById(id).get().getDescription(), tDescription);
	}
	
	@Test
	public void shouldDeleteEvent(){
		Event event = new Event( "testEvent", "description of event", Date.valueOf("2022-11-10"));
		long id = repo.save(event).getId();
		
		repo.deleteById(id);
		
		Assertions.assertTrue( repo.findById(id).isEmpty());
	}

}
