package com.thefreshlystore.services;

import java.math.BigInteger;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.customresponses.CartItemsForOrderPlacement;
import com.thefreshlystore.dtos.request.AddToOrUpdateCartRequest;
import com.thefreshlystore.dtos.request.PlaceOrderRequest;
import com.thefreshlystore.dtos.response.MyCartResponse;
import com.thefreshlystore.models.CartItems;

@ImplementedBy(CartItemsServiceImpl.class)
public interface CartItemsService extends BaseService<CartItems> {
	public void addToOrUpdateCart(AddToOrUpdateCartRequest request, BigInteger userId, BigInteger itemId);
	public MyCartResponse getMyCart(BigInteger userId);
	public CartItemsForOrderPlacement getCartItemsForOrderPlacement(BigInteger userId, PlaceOrderRequest request);
	public void deleteMyCart(BigInteger userId);
	public int getTotalCartItems(BigInteger userId);
}
