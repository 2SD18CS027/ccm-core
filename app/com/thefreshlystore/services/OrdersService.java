package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.customresponses.CartItemsForOrderPlacement;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.dtos.request.PlaceOrderRequest;
import com.thefreshlystore.dtos.response.MyOrdersResponse;
import com.thefreshlystore.dtos.response.PlaceOrderResponse;
import com.thefreshlystore.models.Items;
import com.thefreshlystore.models.OrderItems;
import com.thefreshlystore.models.Orders;
import com.thefreshlystore.models.Users;

@ImplementedBy(OrdersServiceImpl.class)
public interface OrdersService extends BaseService<Orders> {
	public PlaceOrderResponse placeOrder(PlaceOrderRequest request, BigInteger userId, CartItemsForOrderPlacement cartItems, 
			String address, Users user, Double promocodeDiscount, Integer deliveryFee);
	public List<Orders> getMyOrders(BigInteger userId, Integer count, Integer offset);
	public Map<BigInteger, List<OrderItems>> getOrderItems(List<BigInteger> orderIds);
	public MyOrdersResponse getMyOrdersResponse(List<Orders> orders, Map<BigInteger, List<OrderItems>> orderItemsMap,
			boolean areMoreItemsAvailable, Map<BigInteger, Items> itemIdNameMap);
	public List<Orders> getTodaysOrdersForDelivery();
	public void changeOrderStatus(ChangeOrderStatusRequest request, Orders order, Users user);
	public void emailInvoice(Orders order, Users user);
}
