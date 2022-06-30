package com.thefreshlystore.daos;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.OrderReviews;

@ImplementedBy(OrderReviewsDAOImpl.class)
public interface OrderReviewsDAO extends BaseDAO<OrderReviews> {

}
