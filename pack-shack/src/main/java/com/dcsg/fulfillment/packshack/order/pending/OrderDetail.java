package com.dcsg.fulfillment.packshack.order.pending;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderDetail {
	private String distributionOrderId;
	private Integer orderStatus;
	private String shipVia;
	private String distributionOrderLineId;
	private Integer orderLineStatus;
	private String barCode;
	private String itemDescription;
	private String notes;
	private String itemImageUrl;
	private Integer quantity;

}
