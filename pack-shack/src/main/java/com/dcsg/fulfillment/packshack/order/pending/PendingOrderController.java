package com.dcsg.fulfillment.packshack.order.pending;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pack-shack")
public class PendingOrderController {

	@Autowired
	private PendingOrderService pendingOrderService;
	
	@RequestMapping(value = "/orders/stores/{storeId}")
	public ResponseEntity<List<PendingOrder>> getReadyToPackOrderList(@PathVariable("storeId") String storeId,
			@RequestParam(value="fulfillmentType", required=false, defaultValue="SHIP") String fulfillmentType) {

		List<PendingOrder> orderList = pendingOrderService.getReadyToPackOrders(storeId, fulfillmentType);
		ResponseEntity<List<PendingOrder>> responseEntity = null;

		// Return HTTP 204 No Content if there are no orders
		if(orderList.isEmpty()) {
			responseEntity = new ResponseEntity<List<PendingOrder>>(HttpStatus.NO_CONTENT); 
		} else {
			responseEntity = new ResponseEntity<List<PendingOrder>>(orderList, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "/orders/stores/{storeId}/upcs/{upc}")
	public ResponseEntity<OrderDetailModel> getHighestRankedOrderDetail(
			@PathVariable("storeId") String storeId, @PathVariable("upc") String upc,
			@RequestParam(value="fulfillmentType", required=false, defaultValue="SHIP") String fulfillmentType) {

		OrderDetailModel orderDetailModel = pendingOrderService.getHighestRankedOrderDetail(storeId, upc, fulfillmentType);
		ResponseEntity<OrderDetailModel> responseEntity = null;
		
		// Return HTTP 204 No Content if there are no orders for the UPC
		if(orderDetailModel == null) {
			responseEntity = new ResponseEntity<OrderDetailModel>(HttpStatus.NO_CONTENT); 
		} else {
			responseEntity = new ResponseEntity<OrderDetailModel>(orderDetailModel, HttpStatus.OK);
		}
		return responseEntity;
	}

}
