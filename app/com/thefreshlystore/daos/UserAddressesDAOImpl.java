package com.thefreshlystore.daos;

import java.math.BigInteger;

import javax.inject.Inject;

import com.thefreshlystore.models.UserAddresses;

import io.ebean.Ebean;
import io.ebean.SqlRow;
import io.ebean.Update;

public class UserAddressesDAOImpl extends BaseDAOImpl<UserAddresses> implements UserAddressesDAO {

	@Inject
	public UserAddressesDAOImpl() {
		super("id");
	}

	@Override
	public SqlRow getAddressIdFromAddressId(String addressId, BigInteger userId) {
		String sql = "select address from user_addresses where user_id=" + userId + " and address_id = \"" + addressId + "\"";
		return Ebean.createSqlQuery(sql).findOne();
	}

	@Override
	public void deleteUserAddress(String addressId, BigInteger userId) {
		String sql = "delete from user_addresses where user_id=" + userId + " and address_id = \"" + addressId + "\"";
		Update<UserAddresses> upd = Ebean.createUpdate(UserAddresses.class, sql);
		upd.execute();
	}

	@Override
	public void makeOtherAddressesInactive(BigInteger id, BigInteger userId) {
		String sql = "update user_addresses set is_active = false where id != " + id + " and user_id= " + userId;
		Update<UserAddresses> upd = Ebean.createUpdate(UserAddresses.class, sql);
		upd.execute();
	}

	@Override
	public UserAddresses getDefaultAddress(BigInteger userId) {
		return Ebean.find(UserAddresses.class).where().eq("user_id", userId).eq("is_active", true).findOne();
	}

}
