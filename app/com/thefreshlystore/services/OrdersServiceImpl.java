package com.thefreshlystore.services;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.thefreshlystore.customresponses.CartItemsForOrderPlacement;
import com.thefreshlystore.daos.OrderItemsDAO;
import com.thefreshlystore.daos.OrdersDAO;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.dtos.request.PlaceOrderRequest;
import com.thefreshlystore.dtos.response.MyOrdersResponse;
import com.thefreshlystore.dtos.response.PlaceOrderResponse;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.Items;
import com.thefreshlystore.models.OrderItems;
import com.thefreshlystore.models.Orders;
import com.thefreshlystore.models.ServiceableAreas;
import com.thefreshlystore.models.Users;
import com.thefreshlystore.utilities.RazorPayUtility;
import com.thefreshlystore.utilities.SesUtility;
import com.thefreshlystore.utilities.SmsUtility;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.OrderStatus;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.SmsEventIds;

public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements OrdersService {
	private final OrdersDAO ordersDAO;
	private final OrderItemsDAO orderItemsDAO;
	private final RazorPayUtility razorPayUtility;
	private final ItemsService itemsService;
	private final SmsUtility smsUtility;
	private final SesUtility sesUtility;
	private final ServiceableAreasService serviceableAreasService;
	
	@Inject
	public OrdersServiceImpl(OrdersDAO ordersDAO, RazorPayUtility razorPayUtility, 
			OrderItemsDAO orderItemsDAO, SmsUtility smsUtility, ItemsService itemsService,
			SesUtility sesUtility, ServiceableAreasService serviceableAreasService) {
		super(ordersDAO);
		this.ordersDAO = ordersDAO;
		this.razorPayUtility = razorPayUtility;
		this.orderItemsDAO = orderItemsDAO;
		this.smsUtility = smsUtility;
		this.itemsService = itemsService;
		this.sesUtility = sesUtility;
		this.serviceableAreasService = serviceableAreasService;
	}

	@Override
	public PlaceOrderResponse placeOrder(PlaceOrderRequest request, BigInteger userId, 
			CartItemsForOrderPlacement cartItems, String address, Users user, Double promocodeDiscount,
			Integer deliveryFee) {
		Date fromSlotTime = new Date(request.fromSlotTime);
		Date toSlotTime = new Date(request.toSlotTime);
		
		if(new Date().after(fromSlotTime)) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Orders.DELIVERY_SLOT_EXPIRED);
		}
		
		boolean isCaptureSuccess = razorPayUtility.capture(request.totalPrice, request.razorPaymentId);
		if(!isCaptureSuccess) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Payments.PAYMENT_FAILED);
		}
		ServiceableAreas serviceableArea = serviceableAreasService.getByFieldName("area_id", request.areaId, false);
		BigInteger areaId = new BigInteger("999");
		areaId = (serviceableArea == null) ? areaId : serviceableArea.getId();
		Orders order = new Orders(request.razorPaymentId, null, userId, address, 
				fromSlotTime, toSlotTime, areaId, deliveryFee);
		if(request.promocode != null) {
			order.setPromocode(request.promocode);
		}
		save(order);
		List<OrderItems> orderItems = new ArrayList<>();
		Set<BigInteger> itemIds = new HashSet<>();
		cartItems.orderItems.forEach((record) -> {
			orderItems.add( new OrderItems(record.getItemId(), record.getPrice(), record.getQuantity(), order.getId()) );
			itemIds.add(record.getItemId());
		});
		orderItemsDAO.save(orderItems);
		Map<BigInteger, Items> itemDetails = itemsService.getItemDetailsFromItemId(new ArrayList<>(itemIds));
		try { 
			String message = smsUtility.getOrderPlacedMessage(SmsEventIds.ORDER_PLACED, order.getId().toString(), fromSlotTime, toSlotTime);
			smsUtility.sendSms(message, user.getMobileNumber());
		} catch(Exception e){}
		try { 
			DateFormat df = new SimpleDateFormat("EEE MMM dd,");
			String orderDate = df.format(new Date());
			String on = df.format(fromSlotTime);
			df = new SimpleDateFormat("HH:mm");
			String from = df.format(fromSlotTime);
			String to = df.format(toSlotTime);
			String deliveryDate = on + " between "+from+"-"+to;
			sesUtility.sendOrderConfirmationOrInvoiceEmail(user.getName(), order.getId().toString(), orderDate, orderItems, itemDetails, user.getEmail(), deliveryDate, address, true, promocodeDiscount, null);
		} catch(Exception e){e.printStackTrace();}
		return new PlaceOrderResponse(order.getId().toString());
	}

	@Override
	public List<Orders> getMyOrders(BigInteger userId, Integer count, Integer offset) {
		return getByFieldName("user_id", userId, count, offset, "-created_time");
	}
	
	@Override
	public Map<BigInteger, List<OrderItems>> getOrderItems(List<BigInteger> orderIds) {
		Map<BigInteger, List<OrderItems>> response = new HashMap<>();
		orderItemsDAO.getByFieldName("order_id", orderIds, null, null).forEach((orderItem) -> {
			BigInteger orderId = orderItem.getOrderId();
			if(response.containsKey(orderId)) {
				List<OrderItems> temp = new ArrayList<>();
				temp.addAll( response.get(orderId) );
				temp.add(orderItem);
				response.put(orderId, temp);
			} else {
				response.put(orderId, Arrays.asList(orderItem));
			}
		});
		return response;
	}

	@Override
	public MyOrdersResponse getMyOrdersResponse(List<Orders> orders, Map<BigInteger, List<OrderItems>> orderItemsMap,
			boolean areMoreItemsAvailable, Map<BigInteger, Items> itemIdNameMap) {
		MyOrdersResponse response = new MyOrdersResponse();
		response.areMoreItemsAvailable = areMoreItemsAvailable;
		
		orders.forEach((eachOrder) -> {
			MyOrdersResponse.Orders order = new MyOrdersResponse.Orders(eachOrder.getOrderId(), 
					eachOrder.getCreatedTime().getTime(), eachOrder.getStatus(), eachOrder.getAddress(),
					eachOrder.getDeliveryFee());
			if(orderItemsMap.containsKey(eachOrder.getId())) {
				orderItemsMap.get(eachOrder.getId()).forEach((orderItem) -> {
					String name = (itemIdNameMap.containsKey(orderItem.getItemId())) ? itemIdNameMap.get(orderItem.getItemId()).getName() : null;
					String imageUrl = (itemIdNameMap.containsKey(orderItem.getItemId())) ? itemIdNameMap.get(orderItem.getItemId()).getImageUrl() : null;
					order.orderItems.add( new MyOrdersResponse.OrderItems(name, orderItem.getPrice(), orderItem.getQuantity(), imageUrl) );
					order.totalPrice += (orderItem.getPrice() * orderItem.getQuantity());
				});
			}
			response.orders.add(order);
		});
		
		return response;
	}

	@Override
	public List<Orders> getTodaysOrdersForDelivery() {
		return ordersDAO.getTodaysOrdersForDelivery();
	}

	@Override
	public void changeOrderStatus(ChangeOrderStatusRequest request, Orders order, Users user) {
		if(request.newOrderStatus == OrderStatus.ORDER_PLACED || order.getStatus() >= request.newOrderStatus) {
			throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
		}
		switch (request.newOrderStatus) {
			case OrderStatus.ORDER_PROCESSED_AND_PACKED:
				break;
			case OrderStatus.ORDER_ON_THE_WAY_TO_CUSTOMER:
				try {
					String message = smsUtility.getOrderOutForDeliveryMessage(order.getId().toString(), user.getName(), order.getFromSlotTime(), order.getToSlotTime());
					smsUtility.sendSms(message, user.getMobileNumber());
				} catch(Exception e){}
				break;
			case OrderStatus.ORDER_DELIVERED:
				try {
					String message = smsUtility.getOrderDeliveredMessage(order.getId().toString(), user.getName());
					smsUtility.sendSms(message, user.getMobileNumber());
				} catch(Exception e){}
				break;
			default:
				throw new TheFreshlyStoreException(ApiFailureMessages.Orders.INVALID_STATUS);
		}
		order.setStatus(request.newOrderStatus);
		update(order);
	}

	@Override
	public void emailInvoice(Orders order, Users user) {
		try { 
			Date fromSlotTime = order.getFromSlotTime();
			Date toSlotTime = order.getToSlotTime();
			DateFormat df = new SimpleDateFormat("EEE MMM dd,");
			String orderDate = df.format(new Date());
			String on = df.format(fromSlotTime);
			df = new SimpleDateFormat("HH:mm");
			String from = df.format(fromSlotTime);
			String to = df.format(toSlotTime);
			String deliveryDate = on + " between "+from+"-"+to;
			List<OrderItems> orderItems = orderItemsDAO.getByFieldName("order_id", order.getId(), null, null);
			Set<BigInteger> itemIds = new HashSet<>();
			orderItems.forEach((orderItem) -> {
				itemIds.add(orderItem.getItemId());
			});
			Map<BigInteger, Items> itemDetails = itemsService.getItemDetailsFromItemId(new ArrayList<>(itemIds));
			Integer capturedAmount = razorPayUtility.getCapturedAmountForOrder(order.getPaymentId());
			sesUtility.sendOrderConfirmationOrInvoiceEmail(user.getName(), order.getId().toString(), orderDate, orderItems, itemDetails, user.getEmail(), deliveryDate, order.getAddress(), false, null, capturedAmount);
		} catch(Exception e){e.printStackTrace();}
	}
}
