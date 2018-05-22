package com.dcsg.fulfillment.packshack.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {

	public Orders findAllByDistributionOrderId(String distributionOrderId);

}
