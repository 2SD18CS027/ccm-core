package com.thefreshlystore.daos;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.Orders;

@ImplementedBy(OrdersDAOImpl.class)
public interface OrdersDAO extends BaseDAO<Orders> {
	public List<Orders> getTodaysOrdersForDelivery();
}
