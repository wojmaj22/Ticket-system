package com.majchrzw.springboot.ticketSystem.daoTests;

import com.majchrzw.springboot.ticketSystem.dao.UserRepo;
import com.majchrzw.springboot.ticketSystem.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTests {
	
	@Autowired
	private UserRepo repo;
	
	@Test
	@Order(1)
	public void shouldSaveUserToRepository(){
		// given
		User testUser = new User("testUsername", "testPassword", "testEmail@email.com", true);
		// then
		User theUser = repo.save(testUser);
		System.out.println(1);
	}
	
	@Test
	@Order(2)
	public void shouldGetUserFromRepository(){
		// given
		String email = "testEmail@email.com";
		// when
		User testUser = repo.findById("testEmail@email.com").get();
		// then
		assertEquals( testUser.getEmail(), "testEmail@email.com");
	}
	
	@Test
	@Order(3)
	public void shouldGetListOfUsersFromRepository(){
		
		List<User> users = repo.findAll();
		
		Assertions.assertTrue(users.size() != 0);
	}
	
	@Test
	@Order(4)
	public void shouldUpdateUserFromRepository(){
		// given
		String email = "testEmail@email.com";
		String newUsername = "newTestUsername";
		// when
		User testUser = repo.findById("testEmail@email.com").get();
		testUser.setUsername("newTestUsername");
		User newTestUser = repo.save(testUser);
		
		assertEquals(newUsername, repo.findById(email).get().getUsername());
	}
	
	@Test
	@Order(5)
	public void shouldDeleteUserFromRepository(){
		// given
		String email = "testEmail@email.com";
		// when
		repo.deleteById("testEmail@email.com");
		// then
		Assertions.assertTrue( repo.findById(email).isEmpty());
	}
	
	
}
