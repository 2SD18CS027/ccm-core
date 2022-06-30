package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import com.thefreshlystore.customresponses.CartItemsForOrderPlacement;
import com.thefreshlystore.daos.CartItemsDAO;
import com.thefreshlystore.dtos.request.AddToOrUpdateCartRequest;
import com.thefreshlystore.dtos.request.PlaceOrderRequest;
import com.thefreshlystore.dtos.response.MyCartResponse;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.CartItems;
import com.thefreshlystore.models.OrderItems;
import com.thefreshlystore.utilities.Configurations;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;

import io.ebean.SqlRow;

public class CartItemsServiceImpl extends BaseServiceImpl<CartItems> implements CartItemsService {
	private final CartItemsDAO cartDAO;
	private final Configurations configucations;
	@Inject
	public CartItemsServiceImpl(CartItemsDAO cartDAO, Configurations configucations) {
		super(cartDAO);
		this.cartDAO = cartDAO;
		this.configucations = configucations;
	}

	@Override
	public void addToOrUpdateCart(AddToOrUpdateCartRequest request, BigInteger userId, BigInteger itemId) {
		if(request.isDelete) {
			cartDAO.deleteCartItem(userId, itemId);
			return;
		}
		
		CartItems cart = cartDAO.getCartByUserIdAndItemId(userId, itemId);
		if(cart == null) {
			save( new CartItems(userId, itemId, request.quantity) );
		} else {
			cart.setQuantity(request.quantity);
			update(cart);
		}
	}

	@Override
	public MyCartResponse getMyCart(BigInteger userId) {
		MyCartResponse response = new MyCartResponse(configucations.DELIVERY_FEE, configucations.RAZOR_PAY_API_KEY, configucations.MINIMUM_AMOUNT);
		for(SqlRow row : cartDAO.getMyCart(userId)) {
			String itemId = row.getString("item_id");
			String name = row.getString("name");
			String imageUrl = row.getString("image_url");
			Integer quantity = row.getInteger("quantity");
			Double price = row.getDouble("price");
			response.totalPrice += (price * quantity);
			String uom = row.getString("uom");
			response.items.add( new MyCartResponse.Items(itemId, quantity, price, name, imageUrl, uom) );
		};
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		calendar.setTime(new Date());
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		List<String> slotTimes = Arrays.asList( configucations.SLOT_TIMES.split(",") );
		for(int i=0; i<configucations.NEXT_AVAILABLE_DATES_COUNT; i++) {
			for(String slotTime : slotTimes) {
				String fromHour = slotTime.split("-")[0];
				String toHour = slotTime.split("-")[1];
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fromHour.split(":")[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(fromHour.split(":")[1]));
				if(new Date().after(calendar.getTime())) continue;
				long fromSlotTime = calendar.getTimeInMillis();
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(toHour.split(":")[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(toHour.split(":")[1]));
				long toSlotTime = calendar.getTimeInMillis();
				response.slotDateAndTime.add(new MyCartResponse.SlotDateAndTime(fromSlotTime, toSlotTime));
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return response;
	}

	@Override
	public CartItemsForOrderPlacement getCartItemsForOrderPlacement(BigInteger userId, PlaceOrderRequest request) {
		CartItemsForOrderPlacement response = new CartItemsForOrderPlacement();
		for(SqlRow row : cartDAO.getDataForOrderPlacement(userId)) {
			Integer quantity = row.getInteger("quantity");
			BigInteger itemId = BigInteger.valueOf( row.getLong("item_id") );
			Double price = row.getDouble("price");
			response.totalAmount += (quantity * price);
			response.orderItems.add( new OrderItems(itemId, price, quantity) );
		}
		return response;
	}

	@Override
	public void deleteMyCart(BigInteger userId) {
		cartDAO.deleteMyCart(userId);
	}

	@Override
	public int getTotalCartItems(BigInteger userId) {
		return cartDAO.getTotalCartItems(userId);
	}
}
