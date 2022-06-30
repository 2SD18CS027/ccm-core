package com.thefreshlystore.daos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import com.thefreshlystore.models.Orders;

import io.ebean.Ebean;

public class OrdersDAOImpl extends BaseDAOImpl<Orders> implements OrdersDAO {
	
	@Inject
	public OrdersDAOImpl() {
		super("id");
	}

	@Override
	public List<Orders> getTodaysOrdersForDelivery() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date fromSlotTime = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date toSlotTime = calendar.getTime();
		return Ebean.find(Orders.class).where().ge("from_slot_time", fromSlotTime).le("to_slot_time", toSlotTime).findList();
	}
}
