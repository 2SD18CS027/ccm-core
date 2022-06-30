package com.thefreshlystore.daos;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.OrderItems;

@ImplementedBy(OrderItemsDAOImpl.class)
public interface OrderItemsDAO extends BaseDAO<OrderItems> {

}
