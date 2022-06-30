package com.thefreshlystore.daos;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import com.thefreshlystore.models.CartItems;

import io.ebean.Ebean;
import io.ebean.SqlRow;

public class CartItemsDAOImpl extends BaseDAOImpl<CartItems> implements CartItemsDAO {
	
	@Inject
	public CartItemsDAOImpl() {
		super("id");
	}

	@Override
	public CartItems getCartByUserIdAndItemId(BigInteger userId, BigInteger itemId) {
		return Ebean.find(CartItems.class).where().eq("user_id", userId).eq("item_id", itemId).findOne();
	}

	@Override
	public void deleteCartItem(BigInteger userId, BigInteger itemId) {
		Ebean.find(CartItems.class).where().eq("user_id", userId).eq("item_id", itemId).delete();
	}

	@Override
	public List<SqlRow> getMyCart(BigInteger userId) {
		String sql = "select i.name, i.item_id, i.price, i.image_url, c.quantity, i.uom from items i, cart_items c where c.user_id=" + userId + " and c.item_id = i.id";
		return Ebean.createSqlQuery(sql).findList();
	}

	@Override
	public List<SqlRow> getDataForOrderPlacement(BigInteger userId) {
		String sql = "select quantity, i.id as item_id, i.price from cart_items ci, items i where ci.user_id = " + userId + " and i.id = ci.item_id";
		return Ebean.createSqlQuery(sql).findList();
	}

	@Override
	public void deleteMyCart(BigInteger userId) {
		Ebean.find(CartItems.class).where().eq("user_id", userId).delete();
	}

	@Override
	public int getTotalCartItems(BigInteger userId) {
		return Ebean.find(CartItems.class).where().eq("user_id", userId).findCount();
	}
}
