package com.dcsg.fulfillment.packshack.order;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

public class OrdersDTO {

	private @Getter @Setter String distributionOrderId;
	private @Getter @Setter String customerOrderId;
	private @Getter @Setter String status;
	private @Getter @Setter String shipVia;
	private @Getter @Setter String storeId;
	private @Getter @Setter String picklistId;
	private @Getter @Setter String rank;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private @Getter @Setter LocalDateTime lastUpdatedTime;

}
