package com.thefreshlystore.services;

import java.math.BigInteger;

import javax.inject.Inject;

import com.thefreshlystore.daos.OrderReviewsDAO;
import com.thefreshlystore.dtos.request.AddOrderReview;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.OrderReviews;
import com.thefreshlystore.models.Orders;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;

public class OrderReviewsServiceImpl extends BaseServiceImpl<OrderReviews> implements OrderReviewsService {
	private final OrderReviewsDAO orderReviewsDAO;
	
	@Inject
	public OrderReviewsServiceImpl(OrderReviewsDAO orderReviewsDAO) {
		super(orderReviewsDAO);
		this.orderReviewsDAO = orderReviewsDAO;
	}

	@Override
	public void addReview(AddOrderReview request, BigInteger userId, Orders order) {
		if(getByFieldName("order_id", order.getId(), false) != null) {
			throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
		}
		OrderReviews orderReview = new OrderReviews(order.getId(), userId, request.rating, request.comment);
		save(orderReview);
	}
}
