package com.thefreshlystore.customresponses;

import java.util.ArrayList;
import java.util.List;

import com.thefreshlystore.models.OrderItems;

public class CartItemsForOrderPlacement {
	public List<OrderItems> orderItems;
	public Double totalAmount;
	
	public CartItemsForOrderPlacement() {
		this.orderItems = new ArrayList<>();
		this.totalAmount = 0d;
	}
}
