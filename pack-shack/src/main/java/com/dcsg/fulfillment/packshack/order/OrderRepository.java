package com.dcsg.fulfillment.packshack.order;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {

	@Query(value = "select o from Orders o " +
	        "where status = 130 " +
	        "and shipVia <> 'BOPS' " +
	        "and storeId = :storeId " +
	        "and orderType <> 'COS' ")
	public List<Orders> findReadyToPackOrdersByStoreId(@Param("storeId") String storeId);

	public Orders findAllByDistributionOrderId(String distributionOrderId);

}
