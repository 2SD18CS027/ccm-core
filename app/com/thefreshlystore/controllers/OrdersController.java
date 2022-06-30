package com.thefreshlystore.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.AdminAuthentication;
import com.thefreshlystore.actions.UserAuthentication;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.customresponses.CartItemsForOrderPlacement;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.dtos.request.PlaceOrderRequest;
import com.thefreshlystore.dtos.response.CheckPromocodeResponse;
import com.thefreshlystore.dtos.response.PlaceOrderResponse;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.Items;
import com.thefreshlystore.models.OrderItems;
import com.thefreshlystore.models.Orders;
import com.thefreshlystore.models.Users;
import com.thefreshlystore.services.CartItemsService;
import com.thefreshlystore.services.ItemsService;
import com.thefreshlystore.services.OrdersService;
import com.thefreshlystore.services.PromocodesService;
import com.thefreshlystore.services.UsersService;
import com.thefreshlystore.utilities.Configurations;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.ThreadPool;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;

import play.mvc.Http.Request;
import play.mvc.Result;

public class OrdersController extends BaseController {
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final OrdersService ordersService;
	private final CartItemsService cartItemsService;
	private final UsersService usersService;
	private final ItemsService itemsService;
	private final PromocodesService promocodesService;
	private final Configurations configurations;
	
	@Inject
	public OrdersController(CustomObjectMapper objectMapper, OrdersService ordersService, 
			CartItemsService cartItemsService, UsersService usersService, ItemsService itemsService,
			PromocodesService promocodesService, Configurations configurations) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.ordersService = ordersService;
		this.cartItemsService = cartItemsService;
		this.usersService = usersService;
		this.itemsService = itemsService;
		this.promocodesService = promocodesService;
		this.configurations = configurations;
	}
	
	@Cors
	@UserAuthentication
	@ValidateJson(PlaceOrderRequest.class)
	public CompletionStage<Result> placeOrder() {
		try {
			Request apiRequest = request();
			BigInteger userId = UserController.getLoggedInUserId();
			return CompletableFuture.supplyAsync(() -> {
				Users user = usersService.getByFieldName("id", userId, true);
				ObjectMapper mapper = objectMapper.getInstance();
				PlaceOrderRequest request = mapper.convertValue(apiRequest.body().asJson(), PlaceOrderRequest.class);
				if((request.totalPrice + configurations.DELIVERY_FEE) < configurations.MINIMUM_AMOUNT) {
					throw new TheFreshlyStoreException(ApiFailureMessages.Orders.MINIMUN_AMOUNT_REQUIRED_TO_PLACE_ORDER);
				}
				String address = usersService.getAddressFromAddressId(request.addressId, userId);
				if(address == null) {
					throw new TheFreshlyStoreException(ApiFailureMessages.Users.ADDRESS_NOT_FOUND);
				}
				CartItemsForOrderPlacement cartItems = cartItemsService.getCartItemsForOrderPlacement(userId, request);
				cartItems.totalAmount = (configurations.DELIVERY_FEE + cartItems.totalAmount);
				BigInteger promocodeId = null;
				Double promocodeDiscount = 0d;
				if(request.promocode != null) {
					request.promocode = request.promocode.toUpperCase().trim();
					CheckPromocodeResponse promocodeResponse = promocodesService.checkPromocode(request.promocode, userId, request.totalPrice);
					promocodeId = promocodeResponse.promocodeId;
					promocodeDiscount = promocodeResponse.discount;
				}
				if(!(cartItems.totalAmount.equals(request.totalPrice))) {
					throw new TheFreshlyStoreException(ApiFailureMessages.Cart.CART_ITEMS_CHANGED);
				}
				PlaceOrderResponse response = ordersService.placeOrder(request, userId, cartItems, 
						address, user, promocodeDiscount, configurations.DELIVERY_FEE);
				try { cartItemsService.deleteMyCart(userId); } catch(Exception e) {e.printStackTrace();}
				if(request.promocode != null) {
					try {
						promocodesService.addEntryToPromocodeUsage(userId, promocodeId);
					} catch(Exception e){}
				}
				return response;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@UserAuthentication
	public CompletionStage<Result> getMyOrders(Integer count, Integer offset) {
		try {
			BigInteger userId = UserController.getLoggedInUserId();
			return CompletableFuture.supplyAsync(() -> {
				List<Orders> orders = ordersService.getMyOrders(userId, count+1, offset);
				boolean areMoreItemsAvailable;
				if(orders.size() == count+1) { areMoreItemsAvailable = true; orders.remove(orders.size()-1); } else { areMoreItemsAvailable = false; }
				Set<BigInteger> orderIds = new HashSet<>();
				Set<BigInteger> itemIds = new HashSet<>();
				orders.forEach((order) -> {
					orderIds.add(order.getId());
				});
				Map<BigInteger, List<OrderItems>> orderItemsMap = ordersService.getOrderItems(new ArrayList<>(orderIds));
				orderItemsMap.forEach((orderId, orderItems) -> {
					orderItems.forEach((orderItem) -> {
						itemIds.add(orderItem.getItemId());
					});
				});
				Map<BigInteger, Items> itemIdNameMap = itemsService.getItemDetailsFromItemId(new ArrayList<>(itemIds));
				
				return ordersService.getMyOrdersResponse(orders, orderItemsMap, areMoreItemsAvailable, itemIdNameMap);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@AdminAuthentication
	@ValidateJson(ChangeOrderStatusRequest.class)
	public CompletionStage<Result> changeOrderStatus() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				ChangeOrderStatusRequest request = mapper.convertValue(apiRequest.body().asJson(), ChangeOrderStatusRequest.class);
				Orders order = ordersService.getByFieldName("order_id", request.orderId, true);
				Users user = usersService.getByFieldName("id", order.getUserId(), false);
				if(user == null) {
					throw new TheFreshlyStoreException(ApiFailureMessages.Users.USER_NOT_FOUND);
				}
				ordersService.changeOrderStatus(request, order, user);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@UserAuthentication
	public CompletionStage<Result> emailInvoice(String orderId) {
		try {
			return CompletableFuture.supplyAsync(() -> {
				Orders order = ordersService.getByFieldName("order_id", orderId, true);
				Users user = usersService.getByFieldName("id", order.getUserId(), false);
				if(user == null) {
					throw new TheFreshlyStoreException(ApiFailureMessages.Users.USER_NOT_FOUND);
				}
				if(!(order.getUserId().equals(user.getId()))) {
					throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
				}
				ordersService.emailInvoice(order, user);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
