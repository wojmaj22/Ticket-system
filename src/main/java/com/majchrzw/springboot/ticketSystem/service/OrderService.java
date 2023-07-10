package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.OrderRepo;
import com.majchrzw.springboot.ticketSystem.entity.Order;
import com.majchrzw.springboot.ticketSystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.util.Optional;

@Service
public class OrderService {
	private OrderRepo orderRepo;
	
	@Autowired
	public OrderService( OrderRepo orderRepo){
		this.orderRepo = orderRepo;
	}

	public Order findOrderByUserAndStatus(User user, String status){
		Optional<Order> orderOptional = orderRepo.findByUserAndStatus(user, status);
		if( orderOptional.isPresent()){
			return orderOptional.get();
		} else {
			Order order = new Order(user, DateUtils.createNow().getTime(), "new");
			saveOrder(order);
			return order;
		}
	}
	
	public void saveOrder( Order order){
		orderRepo.save(order);
	}
	
}
