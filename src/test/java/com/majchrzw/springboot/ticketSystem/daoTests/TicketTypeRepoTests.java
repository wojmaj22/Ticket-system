package com.majchrzw.springboot.ticketSystem.daoTests;


import com.majchrzw.springboot.ticketSystem.dao.TicketTypeRepo;
import com.majchrzw.springboot.ticketSystem.entity.Event;
import com.majchrzw.springboot.ticketSystem.entity.TicketType;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TicketTypeRepoTests {
	
	@Autowired
	private TicketTypeRepo repo;
	
	private TicketType ticketType;
	private Event tempEvent;
	
	int id;
	
	@Before
	public void setUp(){
		tempEvent = new Event( 1, "testEvent", "description", Date.valueOf("2022-11-11"));
		ticketType = new TicketType("testType", 21.37F, tempEvent);
		id = repo.save(ticketType).getId();
	}
	
	@After
	public void cleanUp(){
		repo.delete(ticketType);
	}
	
	@Test
	public void shouldSaveAndGetTypeToDB(){
		TicketType type =  repo.findById(id).get();
		assertEquals( type.getType() , ticketType.getType());
		assertEquals( type.getPrice() , ticketType.getPrice());
	}
	
	@Test
	public void shouldGetListOfTypesFromDB(){
		List<TicketType> list = repo.findAll();
		
		Assertions.assertThat( list.size() != 0);
	}
	
	@Test
	public void shouldUpdateType() {
		String type = "new type";
		TicketType tempType = repo.findById(id).get();
		
		tempType.setType(type);
		repo.save(tempType);
		
		assertEquals(repo.findById(id).get().getType(), type);
	}
	
	@Test
	public void shouldDeleteTypeFromDB(){
		TicketType type = new TicketType("testType2", 21.36F, tempEvent);
		
		int id = repo.save(type).getId();
		repo.deleteById(id);
		
		assertEquals( true, repo.findById(id).isEmpty());
	}

}
