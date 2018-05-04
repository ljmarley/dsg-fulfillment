package com.dcsg.fulfillment.packshack.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcsg.fulfillment.packshack.exception.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public List<OrdersDTO> getReadyToPackOrderList(String storeId) {
		List<OrdersDTO> ordersList = new ArrayList<OrdersDTO>();
		orderRepository.findReadyToPackOrdersByStoreId(storeId)
		.forEach(orders -> {
			// Build the object to be used in JSON response
			OrdersDTO ordersDTO = new OrdersDTO();
			ordersDTO.setDistributionOrderId(orders.getDistributionOrderId());
			ordersDTO.setCustomerOrderId(orders.getCustomerOrderId());
			ordersDTO.setStatus(orders.getStatus());
			ordersDTO.setShipVia(orders.getShipVia());
			ordersDTO.setStoreId(orders.getStoreId());
			ordersDTO.setPicklistId(orders.getPicklistId());
			ordersDTO.setRank(orders.getRank());
			ordersDTO.setLastUpdatedTime(orders.getLastUpdatedTime());
			
			ordersList.add(ordersDTO);
		});
		
		return ordersList;
	}
	
	public Orders getOrderDetail(String distributionOrderId) {
		Orders orders = orderRepository.findAllByDistributionOrderId(distributionOrderId);
		if(orders == null) {
			throw new ResourceNotFoundException("Order = " + distributionOrderId);
		}
		
		return orders;
	}
}
