package com.dcsg.fulfillment.packshack.order.pending;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dcsg.fulfillment.packshack.PackShackConstants;

@Repository
public class PendingOrderRepository implements PackShackConstants {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<PendingOrder> getReadyToPackOrders(String storeId, String fulfillmentType) {

		// Build the query to return BOPIS/STH orders that are in a ready to pack status
		StringBuilder sql = new StringBuilder("select\n");
		sql.append("  tc_order_id,\n");
		sql.append("  purchase_order_number,\n");
		sql.append("  do_status,\n");
		sql.append("  dsg_ship_via,\n");
		sql.append("  o_facility_alias_id,\n");
		sql.append("  order_type,\n");
		sql.append("  picklist_id,\n");
		sql.append("  effective_rank,\n");
		sql.append("  last_updated_dttm\n");
		sql.append("from orders\n");
		sql.append("where do_status = :status \n");

		// Perform check against ship via based on fulfillment type (BOPIS/SHIP)
		if(BOPIS.equalsIgnoreCase(fulfillmentType)) {
			sql.append("and dsg_ship_via = 'BOPS' \n");
		} else {
			sql.append("and dsg_ship_via <> 'BOPS' \n");
		}

		sql.append("and o_facility_alias_id = :storeId \n");
		sql.append("and order_type <> 'COS'\n");

		int status = (BOPIS.equalsIgnoreCase(fulfillmentType)) ? BOPIS_READY_STATUS : SHIP_READY_STATUS;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("status", status);
		params.addValue("storeId", storeId);

		// Build the list of ready to pack orders from the result set
		List<PendingOrder> pendingOrders = jdbcTemplate.query(sql.toString(), params,
				(rs, rowNum) -> new PendingOrder(rs.getString("tc_order_id"), rs.getString("purchase_order_number"),
						rs.getInt("do_status"), rs.getString("dsg_ship_via"), rs.getString("o_facility_alias_id"),
						rs.getString("order_type"), rs.getString("picklist_id"), rs.getString("effective_rank"),
						rs.getTimestamp("last_updated_dttm").toLocalDateTime()));

		return pendingOrders;

	}

	public List<OrderDetail> getHighestRankedOrderDetail(String storeId, String upc, String fulfillmentType) {

		// Build the query to return the highest ranked order for the provided UPC
		StringBuilder sql = new StringBuilder("select\n");
		sql.append("  o.tc_order_id,\n");
		sql.append("  o.do_status,\n");
		sql.append("  o.dsg_ship_via,\n");
		sql.append("  oli.tc_order_line_id,\n");
		sql.append("  oli.do_dtl_status,\n");
		sql.append("  i.item_bar_code,\n");
		sql.append("  i.description,\n");
		sql.append("  notetable.note,\n");
		sql.append("  i.item_image_filename,\n");
		sql.append("  oli.order_qty\n");
		sql.append("from order_line_item oli\n");
		sql.append("join item_cbo i on oli.item_id = i.item_id\n");
		sql.append("join orders o on oli.order_id = o.order_id\n");
		sql.append("left outer join\n");
		sql.append("(\n");
		sql.append("  select\n");
		sql.append("    purchase_orders_line_item_id,\n");
		sql.append("    LISTAGG(note,',') within group (order by note) note\n");
		sql.append("  from\n");
		sql.append("  (\n");
		sql.append("    select\n");
		sql.append("      purchase_orders_line_item_id,\n");
		sql.append("      replace(note,',',' ') as note,\n");
		sql.append("      note_seq\n");
		sql.append("    from purchase_orders_note\n");
		sql.append("    order by note_seq asc\n");
		sql.append("  ) purchase_orders_note\n");
		sql.append("  where note_seq < 5\n");
		sql.append("  group by purchase_orders_line_item_id\n");
		sql.append(") notetable\n");
		sql.append("on oli.mo_line_item_id = notetable.purchase_orders_line_item_id\n");
		sql.append("where\n");
		sql.append("oli.order_id = \n");
		sql.append("(\n");
		sql.append("  select\n");
		sql.append("    o.order_id\n");
		sql.append("  from orders o\n");
		sql.append("  join order_line_item oli on oli.order_id = o.order_id\n");
		sql.append("  where oli.sku_gtin = :upc \n");
		sql.append("  and o.do_status = :status \n");

		// Perform check against ship via based on fulfillment type (BOPIS/SHIP)
		if(BOPIS.equalsIgnoreCase(fulfillmentType)) {
			sql.append("and o.dsg_ship_via = 'BOPS' \n");
		} else {
			sql.append("and o.dsg_ship_via <> 'BOPS' \n");
		}

		sql.append("  and o_facility_alias_id = :storeId \n");
		sql.append("  and o.order_type <> 'COS'\n");
		sql.append("  order by o.effective_rank\n");
		sql.append("  fetch first 1 row only\n");
		sql.append(")\n");

		int status = (BOPIS.equalsIgnoreCase(fulfillmentType)) ? BOPIS_READY_STATUS : SHIP_READY_STATUS;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("upc", upc);
		params.addValue("status", status);
		params.addValue("storeId", storeId);

		// Build the order detail line(s) from the result set
		List<OrderDetail> orderDetails = jdbcTemplate.query(sql.toString(), params,
				(rs, rowNum) -> new OrderDetail(rs.getString("tc_order_id"), rs.getInt("do_status"),
						rs.getString("dsg_ship_via"), rs.getString("tc_order_line_id"), rs.getInt("do_dtl_status"),
						rs.getString("item_bar_code"), rs.getString("description"), rs.getString("note"), 
						rs.getString("item_image_filename"), rs.getInt("order_qty")));

		return orderDetails;
		
	}
}
