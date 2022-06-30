package com.thefreshlystore.dtos.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class MyCartResponse {
	public List<Items> items;
	public double totalPrice;
	public List<SlotDateAndTime> slotDateAndTime;
	public int deliveryFee;
	public int minimumOrderAmount;
	public String razorPayKey;
	
	public MyCartResponse(int deliveryFee, String razorPayKey, int minimumOrderAmount) {
		this.items = new ArrayList<>();
		this.totalPrice = 0d;
		this.slotDateAndTime = new ArrayList<>();
		this.deliveryFee = deliveryFee;
		this.razorPayKey = razorPayKey;
		this.minimumOrderAmount = (minimumOrderAmount - deliveryFee);
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class Items {
		public String itemId;
		public Integer quantity;
		public Double cost;
		public String name;
		public String imageUrl;
		public String uom;
		
		public Items(String itemId, Integer quantity, Double cost, String name, String imageUrl, String uom) {
			this.itemId = itemId;
			this.quantity = quantity;
			this.cost = cost;
			this.name = name;
			this.imageUrl = imageUrl;
			this.uom = uom;
		}
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class SlotDateAndTime {
		public long fromSlotTime;
		public long toSlotTime;
		
		public SlotDateAndTime(long fromSlotTime, long toSlotTime) {
			this.fromSlotTime = fromSlotTime;
			this.toSlotTime = toSlotTime;
		}
	}
}
