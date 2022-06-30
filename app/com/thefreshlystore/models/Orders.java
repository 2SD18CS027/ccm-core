package com.thefreshlystore.models;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.thefreshlystore.utilities.TheFreshlyStoreConstants;

import io.ebean.Model;

@Entity
@Table(name = "orders")
public class Orders extends Model {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false)
	private BigInteger id;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "user_id")
	private BigInteger userId;
	
	@Column(name = "area_id")
	private BigInteger areaId;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "promocode")
	private String promocode;
	
	@Column(name = "from_slot_time")
	private Date fromSlotTime;
	
	@Column(name = "to_slot_time")
	private Date toSlotTime;
	
	@Column(name = "payment_id")
	private String paymentId;
	
	@Column(name = "delivery_fee")
	private Integer deliveryFee;
	
	@Column(name = "created_time", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdTime;
	
	@Column(name = "lut", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP")
	private Date lut;
	
	public Orders () {}
	
	public Orders(String paymentId, String promocode, BigInteger userId, String address, 
			Date fromSlotTime, Date toSlotTime, BigInteger areaId, Integer deliveryFee) {
		this.orderId = UUID.randomUUID().toString().replaceAll("-", "");
		this.status = TheFreshlyStoreConstants.OrderStatus.ORDER_PLACED;
		this.paymentId = paymentId;
		this.promocode = promocode;
		this.userId = userId;
		this.address = address;
		this.fromSlotTime = fromSlotTime;
		this.toSlotTime = toSlotTime;
		this.areaId = areaId;
		this.deliveryFee = deliveryFee;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getAreaId() {
		return areaId;
	}

	public void setAreaId(BigInteger areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPromocode() {
		return promocode;
	}

	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}

	public Date getFromSlotTime() {
		return fromSlotTime;
	}

	public void setFromSlotTime(Date fromSlotTime) {
		this.fromSlotTime = fromSlotTime;
	}

	public Date getToSlotTime() {
		return toSlotTime;
	}

	public void setToSlotTime(Date toSlotTime) {
		this.toSlotTime = toSlotTime;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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

	public Integer getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Integer deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
}
