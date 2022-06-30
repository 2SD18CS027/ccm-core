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
@Table(name = "user_sessions")
public class UserSessions extends Model {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false)
	private BigInteger id;
	
	@Column(name = "user_id")
	private BigInteger userId;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "device_type_id")
	private Integer deviceTypeId;
	
	@Column(name = "created_time", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdTime;
	
	@Column(name = "lut", insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP")
	private Date lut;
	
	public UserSessions () {}
	
	public UserSessions (BigInteger userId, String token, Integer deviceTypeId){
		this.userId = userId;
		this.token = token;
		this.deviceTypeId = deviceTypeId;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
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
