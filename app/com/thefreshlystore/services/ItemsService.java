package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.dtos.request.ChangeItemAvailabilityStatusRequest;
import com.thefreshlystore.dtos.request.CreateItemRequest;
import com.thefreshlystore.dtos.request.EditItemRequest;
import com.thefreshlystore.dtos.response.AllItemsResponse;
import com.thefreshlystore.dtos.response.CreateItemResponse;
import com.thefreshlystore.models.Items;

@ImplementedBy(ItemsServiceImpl.class)
public interface ItemsService extends BaseService<Items> {
	public CreateItemResponse createItem(CreateItemRequest request);
	public AllItemsResponse getAllItems(boolean isAll);
	public Map<BigInteger, Items> getItemDetailsFromItemId(List<BigInteger> itemIds);
	public void editItem(EditItemRequest request);
	public void changeItemAvailabilityStatus(ChangeItemAvailabilityStatusRequest request);
}
