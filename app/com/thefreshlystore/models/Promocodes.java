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
@Table(name = "promocodes")
public class Promocodes extends Model {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false)
	private BigInteger id;
	
	@Column(name = "promocode")
	private String promocode;
	
	@Column(name = "from_time")
	private Date fromTime;
	
	@Column(name = "to_time")
	private Date toTime;
	
	@Column(name = "is_percentage")
	private Boolean isPercentage;
	
	@Column(name = "value")
	private Double value;
	
	@Column(name = "reuse_count")
	private Integer reuseCount;
	
	@Column(name = "max_value")
	private Double maxValue;
	
	@Column(name = "created_time", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdTime;
	
	@Column(name = "lut", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP")
	private Date lut;
	
	public Promocodes () {}

	public Promocodes(String promocode, Date fromTime, Date toTime, Boolean isPercentage, Double value,
			Double maxValue, Integer reuseCount) {
		this.promocode = promocode;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.isPercentage = isPercentage;
		this.value = value;
		this.maxValue = maxValue;
		this.reuseCount = reuseCount;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getPromocode() {
		return promocode;
	}

	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public Boolean getIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getReuseCount() {
		return reuseCount;
	}

	public void setReuseCount(Integer reuseCount) {
		this.reuseCount = reuseCount;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
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
