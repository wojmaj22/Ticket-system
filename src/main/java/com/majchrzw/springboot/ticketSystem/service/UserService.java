package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.UserRepo;
import com.majchrzw.springboot.ticketSystem.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
	
	// metody do zaimplementowania: dodawanie użytkownika - dodać do tabeli users i authorities
	
	private UserRepo userRepo;
	private EntityManager entityManager;
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepo userRepo, EntityManager entityManager, PasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.entityManager = entityManager;
		passwordEncoder = encoder;
	}
	
	public User findUserByEmail(String email){
		Optional<User> optionalUser = userRepo.findById(email);
		if( optionalUser.isEmpty()){
			throw new EntityNotFoundException("No user with email:" + email + " found.");
		}
		return optionalUser.get();
	}
	
	public boolean checkIfUserExistsByEmail(String email){
		Optional<User> optionalUser = userRepo.findById(email);
		return optionalUser.isPresent();
	}
	
	public void saveUserWithAuthority(User user){
		String codedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(codedPassword);
		userRepo.save(user);
		entityManager.createNativeQuery("insert into authorities(email, authority) values (?, ?)")
				.setParameter(1, user.getEmail())
				.setParameter(2, "ROLE_USER")
				.executeUpdate();
	}
}
