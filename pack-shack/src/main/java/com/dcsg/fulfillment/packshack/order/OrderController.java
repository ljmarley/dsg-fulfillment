package com.dcsg.fulfillment.packshack.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pack-shack")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/orders/{distributionOrderId}")
	public ResponseEntity<Orders> getOrderDetail(@PathVariable("distributionOrderId") String distributionOrderId) {
		Orders orderDetail = orderService.getOrderDetail(distributionOrderId);
		return new ResponseEntity<Orders>(orderDetail, HttpStatus.OK);
	}

}
