package com.dcsg.fulfillment.packshack.order.pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcsg.fulfillment.packshack.PackShackConstants;
import com.dcsg.fulfillment.packshack.order.pending.OrderDetailModel;

@Service
public class PackOrderService implements PackShackConstants {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Transactional
	public void packByDistributionOrderId(OrderDetailModel orderDetailModel) {

		// Default to Ship to home statuses and update to BOPIS statuses if the order is a BOPIS order
		int packedStatus = SHIP_PACKED_STATUS;
		int readyStatus = SHIP_READY_STATUS;
		if(BOPS.equals(orderDetailModel.getShipVia())) {
			packedStatus =  BOPIS_PACKED_STATUS;
			readyStatus = BOPIS_READY_STATUS;
		}

		// Move the distribution order to packed based on id 
		{
			String sql = "update orders set do_status = :packedStatus"
					+ " where tc_order_id = :distributionOrderId";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("packedStatus", packedStatus);
			params.addValue("distributionOrderId", orderDetailModel.getDistributionOrderId());
			jdbcTemplate.update(sql, params);
		}

		// Moving all eligible distribution order lines to packed based on id
		{
			String sql = "update order_line_item set do_dtl_status = :packedStatus"
					+ " where order_id = (select order_id from orders where tc_order_id = :distributionOrderId)"
					+ "and do_dtl_status = :readyStatus";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("packedStatus", packedStatus);
			params.addValue("distributionOrderId", orderDetailModel.getDistributionOrderId());
			params.addValue("readyStatus", readyStatus);
			jdbcTemplate.update(sql, params);
		}

	}
	
	public void packByPicklistId(String picklistId) {
		
	}
}
