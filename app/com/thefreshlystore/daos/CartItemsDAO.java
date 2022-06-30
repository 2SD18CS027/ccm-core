package com.thefreshlystore.daos;

import java.math.BigInteger;
import java.util.List;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.CartItems;

import io.ebean.SqlRow;

@ImplementedBy(CartItemsDAOImpl.class)
public interface CartItemsDAO extends BaseDAO<CartItems> {
	public CartItems getCartByUserIdAndItemId(BigInteger userId, BigInteger itemId);
	public void deleteCartItem(BigInteger userId, BigInteger itemId);
	public List<SqlRow> getMyCart(BigInteger userId);
	public List<SqlRow> getDataForOrderPlacement(BigInteger userId);
	public void deleteMyCart(BigInteger userId);
	public int getTotalCartItems(BigInteger userId);
}