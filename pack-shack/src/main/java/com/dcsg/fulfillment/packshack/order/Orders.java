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
public class Orders {

	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_ID")
	@SequenceGenerator(name = "SEQ_ORDER_ID", sequenceName = "SEQ_ORDER_ID", allocationSize = 1, initialValue = 1)
	private @Getter @Setter Long id;
	
	@Column(name = "TC_ORDER_ID")
	private @Getter @Setter String distributionOrderId;
	
	@Column(name = "PURCHASE_ORDER_NUMBER")
	private @Getter @Setter String customerOrderId;

	@Column(name = "DO_STATUS")
	private @Getter @Setter String status;

	@Column(name = "DSG_SHIP_VIA")
	private @Getter @Setter String shipVia;

	@Column(name = "O_FACILITY_ALIAS_ID")
	private @Getter @Setter String storeId;

	@Column(name = "ORDER_TYPE")
	private @Getter @Setter String orderType;

	@Column(name = "PICKLIST_ID")
	private @Getter @Setter String picklistId;

	@Column(name = "EFFECTIVE_RANK")
	private @Getter @Setter String rank;

	@Column(name = "LAST_UPDATED_DTTM")
	private @Getter @Setter LocalDateTime lastUpdatedTime;

	protected Orders() {}

}
