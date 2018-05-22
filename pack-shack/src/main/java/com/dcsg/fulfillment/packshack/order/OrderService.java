package com.dcsg.fulfillment.packshack.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcsg.fulfillment.packshack.exception.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public Orders getOrderDetail(String distributionOrderId) {
		Orders orders = orderRepository.findAllByDistributionOrderId(distributionOrderId);

		// Return HTTP 404 Not Found if no order found
		if(orders == null) {
			throw new ResourceNotFoundException("Order = " + distributionOrderId);
		}
		
		return orders;
	}
}
