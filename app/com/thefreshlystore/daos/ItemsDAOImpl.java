package com.thefreshlystore.daos;

import java.util.List;

import javax.inject.Inject;

import com.thefreshlystore.models.Items;

import io.ebean.Ebean;
import play.data.validation.Constraints.Required;

public class ItemsDAOImpl extends BaseDAOImpl<Items> implements ItemsDAO {
	
	@Inject
	public ItemsDAOImpl() {
		super("id");
	}

	@Override
	public List<Items> getAllAvailableItems() {
		return Ebean.find(Items.class).where().eq("is_available", true).findList();
	}

	@Override
	public void deleteItem(@Required String itemId) {
		Ebean.createUpdate(Items.class, "delete from items where item_id=\"" + itemId + "\"").execute();
	}
}
