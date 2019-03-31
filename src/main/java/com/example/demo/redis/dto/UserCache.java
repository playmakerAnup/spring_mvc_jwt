package com.example.demo.redis.dto;

import java.io.Serializable;

/**
 * Redis DTO for user and token details<br>  
 * <h4>Created On: 19th-Jan-2019</h4><br>
 * @author Anup Nair<br>
 * 
 */
public class UserCache implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String tokenTimeStampInMillis;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTokenTimeStampInMillis() {
		return tokenTimeStampInMillis;
	}
	public void setTokenTimeStampInMillis(String tokenTimeStampInMillis) {
		this.tokenTimeStampInMillis = tokenTimeStampInMillis;
	}
	
}
