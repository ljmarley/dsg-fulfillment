package com.dcsg.fulfillment.packshack.order.pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dcsg.fulfillment.packshack.order.pending.OrderDetailModel;

@RestController
@RequestMapping("/pack-shack")
public class PackOrderController {

	@Autowired
	private PackOrderService packOrderService;
	
	@RequestMapping(value = "/orders/{distributionOrderId}", method = RequestMethod.PATCH)
	public ResponseEntity<HttpStatus> packByDistributionOrderId(
			@PathVariable("distributionOrderId") String distributionOrderId,
			@RequestBody OrderDetailModel orderDetailModel) {

		packOrderService.packByDistributionOrderId(orderDetailModel);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "/orders/picklists/{picklistId}", method = RequestMethod.PATCH)
	public ResponseEntity<HttpStatus> packByPicklistId(@PathVariable("picklistId") String picklistId) {
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
	}

}
