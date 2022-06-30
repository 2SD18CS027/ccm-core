package com.thefreshlystore.daos;

import javax.inject.Inject;

import com.thefreshlystore.models.OrderReviews;

public class OrderReviewsDAOImpl extends BaseDAOImpl<OrderReviews> implements OrderReviewsDAO {
	
	@Inject
	public OrderReviewsDAOImpl() {
		super("id");
	}
}
