package com.thefreshlystore.dtos.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class MyOrdersResponse {
	public List<Orders> orders;
	public boolean areMoreItemsAvailable;
	
	public MyOrdersResponse() { this.orders = new ArrayList<>(); }
	
	public static class Orders {
		public String orderId;
		public long createdTime;
		public int orderStatus;
		public List<OrderItems> orderItems;
		public double totalPrice;
		public String address;
		
		public Orders(String orderId, long createdTime, int orderStatus, String address,
				Integer deliveryFee) {
			this.orderId = orderId; this.createdTime = createdTime; this.orderItems = new ArrayList<>();
			this.orderStatus = orderStatus;
			this.totalPrice = deliveryFee;
			this.address = address;
		}
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class OrderItems {
		public String name;
		public double price;
		public int quantity;
		public String imageUrl;
		
		public OrderItems(String name, double price, int quantity, String imageUrl) {
			this.name = name; this.price = price; this.quantity = quantity; this.imageUrl = imageUrl;
		}
	}
}
