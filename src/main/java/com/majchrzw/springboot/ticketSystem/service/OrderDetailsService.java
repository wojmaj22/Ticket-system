package com.majchrzw.springboot.ticketSystem.service;

import com.majchrzw.springboot.ticketSystem.dao.OrderDetailsRepo;
import com.majchrzw.springboot.ticketSystem.entity.Order;
import com.majchrzw.springboot.ticketSystem.entity.OrderDetails;
import com.majchrzw.springboot.ticketSystem.entity.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {

	private OrderDetailsRepo orderDetailsRepo;
	
	@Autowired
	public OrderDetailsService(OrderDetailsRepo orderDetailsRepo) {
		this.orderDetailsRepo = orderDetailsRepo;
	}
	
	public void saveOrderDetails(OrderDetails orderDetails){
		orderDetailsRepo.save(orderDetails);
	}
	
	public List<OrderDetails> findAllOrderDetailsByOrder(Order order){
		return orderDetailsRepo.findAllByOrder(order);
	}
	
	public void saveNewOrderDetails(TicketType ticketType, Order order){
		OrderDetails newOrderDetails = new OrderDetails(order, ticketType, 1, ticketType.getPrice());
		saveOrderDetails(newOrderDetails);
	}
}
