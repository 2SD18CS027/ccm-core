package com.thefreshlystore.daos;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.Promocodes;

@ImplementedBy(PromocodesDAOImpl.class)
public interface PromocodesDAO extends BaseDAO<Promocodes> {

}
