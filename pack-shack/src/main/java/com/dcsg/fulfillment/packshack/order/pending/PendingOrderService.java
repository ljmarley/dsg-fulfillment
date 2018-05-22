package com.dcsg.fulfillment.packshack.order.pending;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PendingOrderService {

	@Autowired
	private PendingOrderRepository pendingOrderRepository;
	
	public List<PendingOrder> getReadyToPackOrders(String storeId, String fulfillmentType) {
		return pendingOrderRepository.getReadyToPackOrders(storeId, fulfillmentType);
	}
	
	public OrderDetailModel getHighestRankedOrderDetail(String storeId, String upc, String fulfillmentType) {
		OrderDetailModel orderDetailModel = null;
		List<OrderDetail> orderDetailList = pendingOrderRepository.getHighestRankedOrderDetail(storeId, upc, fulfillmentType);
		
		if(!orderDetailList.isEmpty()) {
			List<LineDetailModel> lineDetailModels = new ArrayList<LineDetailModel>();

			// Build the order detail model from the order details result set
			orderDetailModel = new OrderDetailModel();
			orderDetailModel.setDistributionOrderId(orderDetailList.get(0).getDistributionOrderId());
			orderDetailModel.setOrderStatus(orderDetailList.get(0).getOrderStatus());
			orderDetailModel.setShipVia(orderDetailList.get(0).getShipVia());
			orderDetailList.forEach(detail -> {
				LineDetailModel lineDetail = new LineDetailModel(detail.getDistributionOrderLineId(),
						detail.getOrderLineStatus(), detail.getBarCode(), detail.getItemDescription(),
						detail.getNotes(), detail.getItemImageUrl(), detail.getQuantity());
				
				lineDetailModels.add(lineDetail);
			});
			
			orderDetailModel.setLineDetailModel(lineDetailModels);
		}

		return orderDetailModel;
	}
}
