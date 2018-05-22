package com.dcsg.fulfillment.packshack.order.pending;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class PendingOrder {

	private @Getter @Setter String distributionOrderId;
	private @Getter @Setter String customerOrderId;
	private @Getter @Setter Integer status;
	private @Getter @Setter String shipVia;
	private @Getter @Setter String storeId;
	private @Getter @Setter String orderType;
	private @Getter @Setter String picklistId;
	private @Getter @Setter String rank;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private @Getter @Setter LocalDateTime lastUpdatedTime;

	protected PendingOrder() {}

}
