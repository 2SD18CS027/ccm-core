package com.thefreshlystore.services;

import java.math.BigInteger;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.dtos.request.AddOrderReview;
import com.thefreshlystore.models.OrderReviews;
import com.thefreshlystore.models.Orders;

@ImplementedBy(OrderReviewsServiceImpl.class)
public interface OrderReviewsService extends BaseService<OrderReviews> {
	public void addReview(AddOrderReview request, BigInteger userId, Orders order);
}
