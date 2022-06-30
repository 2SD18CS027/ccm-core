package com.thefreshlystore.daos;

import java.math.BigInteger;

import javax.inject.Inject;

import com.thefreshlystore.models.PromocodesUsage;

import io.ebean.Ebean;

public class PromocodesUsageDAOImpl extends BaseDAOImpl<PromocodesUsage> implements PromocodesUsageDAO {
	
	@Inject
	public PromocodesUsageDAOImpl() {
		super("id");
	}

	@Override
	public int checkPromocodeUsageCount(BigInteger promocodeId, BigInteger userId) {
		return Ebean.find(PromocodesUsage.class).where().eq("promocode_id", promocodeId).eq("user_id", userId).findCount();
	}
}
