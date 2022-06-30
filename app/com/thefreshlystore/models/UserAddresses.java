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

import io.ebean.Model;

@Entity
@Table(name = "user_addresses")
public class UserAddresses extends Model {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false)
	private BigInteger id;
	
	@Column(name = "address_id")
	private String addressId;
	
	@Column(name = "user_id")
	private BigInteger userId;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "area_id")
	private BigInteger areaId;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "created_time", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdTime;
	
	@Column(name = "lut", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP")
	private Date lut;
	
	public UserAddresses () {}
	
	public UserAddresses(BigInteger userId, String address, BigInteger areaId) {
		this.addressId = UUID.randomUUID().toString().replaceAll("-", "");
		this.userId = userId;
		this.address = address;
		this.areaId = areaId;
		this.isActive = true;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigInteger getAreaId() {
		return areaId;
	}

	public void setAreaId(BigInteger areaId) {
		this.areaId = areaId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
