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
@Table(name = "promocodes_usage")
public class PromocodesUsage extends Model {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false)
	private BigInteger id;
	
	@Column(name = "promocode_id")
	private BigInteger promocodeId;
	
	@Column(name = "user_id")
	private BigInteger userId;
	
	@Column(name = "created_time", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdTime;
	
	@Column(name = "lut", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP")
	private Date lut;
	
	public PromocodesUsage () {}

	public PromocodesUsage(BigInteger promocodeId, BigInteger userId) {
		this.promocodeId = promocodeId;
		this.userId = userId;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getPromocodeId() {
		return promocodeId;
	}

	public void setPromocodeId(BigInteger promocodeId) {
		this.promocodeId = promocodeId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
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
