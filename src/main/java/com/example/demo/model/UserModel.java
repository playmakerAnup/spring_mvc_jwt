package com.example.demo.model;
/**
 * @author Anup Nair
 * Created on 30th-March-2019
 */
public class UserModel {
	
	private Integer userId;
	private Integer loginStatus;
	private Long expiryTime;
	private String authToken;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Long getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(Long expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
	

}
