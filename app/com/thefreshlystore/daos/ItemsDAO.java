package com.thefreshlystore.daos;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.Items;

import play.data.validation.Constraints.Required;

@ImplementedBy(ItemsDAOImpl.class)
public interface ItemsDAO extends BaseDAO<Items> {
	public List<Items> getAllAvailableItems();
	public void deleteItem(@Required String itemId);
}
