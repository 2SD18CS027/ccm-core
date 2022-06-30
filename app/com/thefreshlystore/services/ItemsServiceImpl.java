package com.thefreshlystore.services;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.daos.ItemsDAO;
import com.thefreshlystore.dtos.request.ChangeItemAvailabilityStatusRequest;
import com.thefreshlystore.dtos.request.CreateItemRequest;
import com.thefreshlystore.dtos.request.DeleteItemRequest;
import com.thefreshlystore.dtos.request.EditItemRequest;
import com.thefreshlystore.dtos.response.AllItemsResponse;
import com.thefreshlystore.dtos.response.CreateItemResponse;
import com.thefreshlystore.models.Items;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.DBConnection;

public class ItemsServiceImpl extends BaseServiceImpl<Items> implements ItemsService {
	private final ItemsDAO itemsDAO;
	private final CustomObjectMapper objectMapper;
	
	@Inject
	public ItemsServiceImpl(ItemsDAO itemsDAO, CustomObjectMapper objectMapper) {
		super(itemsDAO);
		this.itemsDAO = itemsDAO;
		this.objectMapper = objectMapper;
	}

	@Override
	public CreateItemResponse createItem(CreateItemRequest request) {
		Items item = new Items(request.name, request.price, request.imageUrl, request.category, request.uom);
		save(item);
		return new CreateItemResponse(item.getItemId());
	}

	@Override
	public AllItemsResponse getAllItems(boolean isAll) {
		AllItemsResponse response = new AllItemsResponse();
		try {
			Connection connection = DBConnection.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from items");
			
			while(rs.next()) {
				String itemId = rs.getString("item_id");
				String name = rs.getString("name");
				String category = rs.getString("category");
				String imageUrl = rs.getString("image_url");
				Double price = rs.getDouble("price");
				response.items.add(new AllItemsResponse.Items(itemId, name, category, price, imageUrl, 0, "KB", true));
				response.categories.add(category);
			}
			
			rs.close();
			stmt.close();
		} catch(Exception e) { e.printStackTrace(); } 
//		ObjectMapper mapper = objectMapper.getInstance();
//		List<Items> items = (isAll) ? getAll(null, null, "name") : itemsDAO.getAllAvailableItems();
//		items.forEach((item) -> {
//			response.items.add( mapper.convertValue(item, AllItemsResponse.Items.class) );
//			response.categories.add(item.getCategory());
//		});
		return response;
	}

	@Override
	public Map<BigInteger, Items> getItemDetailsFromItemId(List<BigInteger> itemIds) {
		Map<BigInteger, Items> response = new HashMap<>();
		getByFieldName("id", itemIds, null, null).forEach((item) -> {
			response.put(item.getId(), item);
		});
		return response;
	}

	@Override
	public void editItem(EditItemRequest request) {
		Items item = getByFieldName("item_id", request.itemId, true);
		boolean isUpdate = false;
		if(request.category != null) {
			item.setCategory(request.category);
			isUpdate = true;
		}
		if(request.name != null) {
			item.setName(request.name);
			isUpdate = true;
		}
		if(request.price != null) {
			item.setPrice(request.price);
			isUpdate = true;
		}
		if(request.imageUrl != null) {
			item.setImageUrl(request.imageUrl);
			isUpdate = true;
		}
		if(request.uom != null) {
			item.setUom(request.uom);
			isUpdate = true;
		}
		if(request.isAvailable != null) {
			item.setIsAvailable(request.isAvailable);
			isUpdate = true;
		}
		
		if(isUpdate) {
			update(item);
		}
	}

	@Override
	public void changeItemAvailabilityStatus(ChangeItemAvailabilityStatusRequest request) {
		Items item = getByFieldName("item_id", request.itemId, true);
		item.setIsAvailable(request.status);
		update(item);
	}
}
