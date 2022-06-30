package com.thefreshlystore.controllers;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.UserAuthentication;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.AddOrderReview;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.Orders;
import com.thefreshlystore.services.OrderReviewsService;
import com.thefreshlystore.services.OrdersService;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.OrderStatus;
import com.thefreshlystore.utilities.ThreadPool;

import play.mvc.Http.Request;
import play.mvc.Result;

public class OrderReviewsController extends BaseController {
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final OrderReviewsService orderReviewsService;
	private final OrdersService ordersService;
	
	@Inject
	public OrderReviewsController(CustomObjectMapper objectMapper, OrderReviewsService orderReviewsService,
			OrdersService ordersService) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.orderReviewsService = orderReviewsService;
		this.ordersService = ordersService;
	}
	
	@Cors
	@UserAuthentication
	@ValidateJson(AddOrderReview.class)
	public CompletionStage<Result> createReview() {
		try {
			Request apiRequest = request();
			BigInteger userId = UserController.getLoggedInUserId();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				AddOrderReview request = mapper.convertValue(apiRequest.body().asJson(), AddOrderReview.class);
				Orders order = ordersService.getByFieldName("order_id", request.orderId, true);
				if(!(order.getUserId().equals(userId))) {
					throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
				}
				if(!(order.getStatus().equals(OrderStatus.ORDER_DELIVERED))) {
					throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
				}
				orderReviewsService.addReview(request, userId, order);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
