package com.dcsg.fulfillment.packshack.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pack-shack")
@CrossOrigin(origins = {"${settings.cors_origin}"})
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/orders/store/{storeId}")
	public ResponseEntity<List<OrdersDTO>> getReadyToPackOrderList(@PathVariable("storeId") String storeId) {
		List<OrdersDTO> orderList = orderService.getReadyToPackOrderList(storeId);
		ResponseEntity<List<OrdersDTO>> responseEntity = null;

		if(orderList.isEmpty()) {
			responseEntity = new ResponseEntity<List<OrdersDTO>>(HttpStatus.NO_CONTENT); 
		} else {
			responseEntity = new ResponseEntity<List<OrdersDTO>>(orderList, HttpStatus.OK);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/orders/{distributionOrderId}")
	public ResponseEntity<Orders> getOrderDetail(@PathVariable("distributionOrderId") String distributionOrderId) {
		Orders orderDetail = orderService.getOrderDetail(distributionOrderId);
		return new ResponseEntity<Orders>(orderDetail, HttpStatus.OK);
	}

}
