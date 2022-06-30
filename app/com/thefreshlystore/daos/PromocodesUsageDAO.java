package com.thefreshlystore.daos;

import java.math.BigInteger;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.PromocodesUsage;

@ImplementedBy(PromocodesUsageDAOImpl.class)
public interface PromocodesUsageDAO extends BaseDAO<PromocodesUsage> {
	public int checkPromocodeUsageCount(BigInteger promocodeId, BigInteger userId);
}
