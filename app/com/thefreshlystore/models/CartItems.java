package com.thefreshlystore.models;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.ebean.Model;

@Entity
@Table(name = "cart_items")
public class CartItems extends Model {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false)
	private BigInteger id;
	
	@Column(name = "user_id")
	private BigInteger userId;
	
	@Column(name = "item_id")
	private BigInteger itemId;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "created_time", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdTime;
	
	@Column(name = "lut", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP")
	private Date lut;
	
	public CartItems () {}
	
	public CartItems(BigInteger userId, BigInteger itemId, Integer quantity) {
		this.userId = userId;
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getItemId() {
		return itemId;
	}

	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLut() {
		return lut;
	}

	public void setLut(Date lut) {
		this.lut = lut;
	}
}
