package com.thefreshlystore.daos;

import java.math.BigInteger;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.UserAddresses;

import io.ebean.SqlRow;

@ImplementedBy(UserAddressesDAOImpl.class)
public interface UserAddressesDAO extends BaseDAO<UserAddresses> {
	public SqlRow getAddressIdFromAddressId(String addressId, BigInteger userId);
	public void deleteUserAddress(String addressId, BigInteger userId);
	public void makeOtherAddressesInactive(BigInteger id, BigInteger userId);
	public UserAddresses getDefaultAddress(BigInteger userId);
}
