package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.UserRepo;
import com.majchrzw.springboot.ticketSystem.entity.User;
import com.majchrzw.springboot.ticketSystem.entity.UserEnableForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
	
	private UserRepo userRepo;
	private EntityManager entityManager;
	
	private PasswordEncoder passwordEncoder;
	
	private UserDetailsManager userDetailsManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	public UserService(UserRepo userRepo, EntityManager entityManager, PasswordEncoder encoder, UserDetailsManager userDetailsManager) {
		this.userRepo = userRepo;
		this.entityManager = entityManager;
		passwordEncoder = encoder;
		this.userDetailsManager = userDetailsManager;
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
	
	public void registerUser(User newUser){
		if( !checkIfUserExistsByEmail(newUser.getEmail()) ){
			saveUserWithAuthority(newUser);
		} else {
			throw new IllegalArgumentException("User already exists");
		}
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
	
	public Page<User> findPaginated(Pageable pageable, String email){
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		
		List<User> users = userRepo.findByEmailContaining(email);
		int toIndex = Math.min(startItem + pageSize, users.size());
		// TODO - tutaj trzeba poprawić tak jak jest w event service, pageImpl - users ma być tylko odpowiedni wycinek
		return new PageImpl<>(users.subList(startItem,toIndex), pageable, users.size());
	}
	
	public Collection<? extends GrantedAuthority> findUserRoles(Authentication authentication, User user){
		UserDetails details = userDetailsManager.loadUserByUsername(user.getEmail());
		
		return details.getAuthorities();
	}
	
	public void deleteAuthorityFromUser( String email, String authorityName){
		entityManager.createNativeQuery("delete from authorities where email = ? and authority = ?")
				.setParameter(1, email)
				.setParameter(2, authorityName)
				.executeUpdate();
	}
	
	public void addNewAuthority( String email, String newAuthority) {
		entityManager.createNativeQuery("insert into authorities(email, authority) values (?, ?)")
				.setParameter(1, email)
				.setParameter(2, newAuthority)
				.executeUpdate();
	}
	
	public void changeUserEnable( UserEnableForm form){
		User user = findUserByEmail(form.getEmail());
		user.setEnabled(form.getEnabled());
		userRepo.save(user);
	}
	
}
