package com.dcsg.fulfillment.packshack.order.pending;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderDetailModel {

	private String distributionOrderId;
	private Integer orderStatus;
	private String shipVia;
	private List<LineDetailModel> lineDetailModel;

}
