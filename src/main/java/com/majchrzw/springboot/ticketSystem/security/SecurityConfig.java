package com.majchrzw.springboot.ticketSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource){
		
		JdbcUserDetailsManager udm = new JdbcUserDetailsManager(dataSource);
		
		udm.setUsersByUsernameQuery("select email, password, enabled from users where email=?");
		udm.setAuthoritiesByUsernameQuery("select email, authority from authorities where email=?");
		udm.setDeleteUserAuthoritiesSql("delete from authorities where username=?");
		
		return udm;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests( configurer ->
						configurer
								.requestMatchers("/register").permitAll()
								.requestMatchers("/process-register").permitAll()
								.requestMatchers("/admin").hasRole("ADMIN")
								.requestMatchers("/admin/**").hasRole("ADMIN")
								.requestMatchers("/user").hasRole("USER")
								.requestMatchers("/user/**").hasRole("USER")
								.anyRequest().authenticated())
				.formLogin( form ->
						form
								.loginPage("/login")
								.permitAll()
								.loginProcessingUrl("/process-login")
								.permitAll()
								.usernameParameter("email")
								.defaultSuccessUrl("/home"))
				.exceptionHandling( exception ->
						exception
								.accessDeniedPage("/access-denied"))
				.csrf( csrf ->
						csrf
								.disable())
				.logout( logout ->
						logout
								.permitAll())
				.rememberMe( remember ->
						remember
								.rememberMeParameter("remember-me")
								.tokenRepository(tokenRepository()));
		return http.build();
	}
	
	@Bean
	public JdbcTokenRepositoryImpl tokenRepository(){
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
}
