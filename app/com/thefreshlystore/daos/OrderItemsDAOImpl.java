package com.thefreshlystore.daos;

import javax.inject.Inject;

import com.thefreshlystore.models.OrderItems;

public class OrderItemsDAOImpl extends BaseDAOImpl<OrderItems> implements OrderItemsDAO {
	
	@Inject
	public OrderItemsDAOImpl() {
		super("id");
	}
	
}
