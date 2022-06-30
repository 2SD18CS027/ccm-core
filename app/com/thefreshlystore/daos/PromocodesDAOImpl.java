package com.thefreshlystore.daos;

import javax.inject.Inject;

import com.thefreshlystore.models.Promocodes;

public class PromocodesDAOImpl extends BaseDAOImpl<Promocodes> implements PromocodesDAO {

	@Inject
	public PromocodesDAOImpl() {
		super("id");
	}
}
