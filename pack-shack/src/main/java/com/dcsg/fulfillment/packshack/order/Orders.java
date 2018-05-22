package com.dcsg.fulfillment.packshack.order;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
public class Orders {

	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_ID")
	@SequenceGenerator(name = "SEQ_ORDER_ID", sequenceName = "SEQ_ORDER_ID", allocationSize = 1, initialValue = 1)
	private  Long id;
	
	@Column(name = "TC_ORDER_ID")
	private String distributionOrderId;
	
	@Column(name = "PURCHASE_ORDER_NUMBER")
	private String customerOrderId;

	@Column(name = "DO_STATUS")
	private String status;

	@Column(name = "DSG_SHIP_VIA")
	private String shipVia;

	@Column(name = "O_FACILITY_ALIAS_ID")
	private String storeId;

	@Column(name = "ORDER_TYPE")
	private String orderType;

	@Column(name = "PICKLIST_ID")
	private String picklistId;

	@Column(name = "EFFECTIVE_RANK")
	private String rank;

	@Column(name = "LAST_UPDATED_DTTM")
	private LocalDateTime lastUpdatedTime;

	protected Orders() {}

}
