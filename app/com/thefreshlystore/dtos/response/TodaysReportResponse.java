package com.thefreshlystore.dtos.response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class TodaysReportResponse {
	public List<ItemsToBuy> itemsToBuy;
	public List<OrderDetails> orders;
	
	public TodaysReportResponse() {
		this.itemsToBuy = new ArrayList<>();
		this.orders = new ArrayList<>();
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class OrderDetails {
		public String orderId;
		public BigInteger customerOrderId;
		public Integer status;
		public Long fromSlotTime;
		public Long toSlotTime;
		public String address;
		public String area;
		public Double totalPrice;
		public List<ItemDetails> items;
		public String name;
		public String mobileNumber;
		
		public OrderDetails(String orderId, Integer status, Long fromSlotTime, Long toSlotTime, 
				String address, String name, String mobileNumber, String area, BigInteger customerOrderId) {
			this.orderId = orderId;
			this.status = status;
			this.fromSlotTime = fromSlotTime;
			this.toSlotTime = toSlotTime;
			this.address = address;
			this.items = new ArrayList<>();
			this.totalPrice = 0d;
			this.name = name;
			this.mobileNumber = mobileNumber;
			this.area = area;
			this.customerOrderId = customerOrderId;
		}
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class ItemDetails {
		public String itemId;
		public String name;
		public String imageUrl;
		public Double price;
		public String uom;
		public int quantity;
		
		public ItemDetails(String itemId, String name, String imageUrl, Double price, int quantity,
				String uom) {
			this.itemId = itemId;
			this.name = name;
			this.imageUrl = imageUrl;
			this.price = price;
			this.quantity = quantity;
			this.uom = uom;
		}
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class ItemsToBuy {
		public String itemId;
		public String name;
		public String imageUrl;
		public int quantity;
		public String uom;
		
		public ItemsToBuy(String itemId, String name, String imageUrl, int quantity, String uom) {
			this.itemId = itemId;
			this.name = name;
			this.imageUrl = imageUrl;
			this.quantity = quantity;
			this.uom = uom;
		}
	}
}
