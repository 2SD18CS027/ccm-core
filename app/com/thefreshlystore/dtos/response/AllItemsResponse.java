package com.thefreshlystore.dtos.response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class AllItemsResponse {
	public List<Items> items;
	public Set<String> categories;
	
	public AllItemsResponse() {
		this.items = new ArrayList<>();
		this.categories = new HashSet<>();
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class Items {
		public String itemId;
		public String name;
		public String category;
		public Double price;
		public String imageUrl;
		public int cartQty;
		public String uom;
		public BigInteger id;
		public Boolean isAvailable;
		
		public Items() {}

		public Items(String itemId, String name, String category, Double price, String imageUrl, int cartQty,
				String uom, Boolean isAvailable) {
			this.itemId = itemId;
			this.name = name;
			this.category = category;
			this.price = price;
			this.imageUrl = imageUrl;
			this.cartQty = cartQty;
			this.uom = uom;
			this.isAvailable = isAvailable;
		}
	}
}
