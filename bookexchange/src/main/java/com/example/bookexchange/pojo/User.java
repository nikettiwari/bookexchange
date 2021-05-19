package com.example.bookexchange.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.example.bookexchange.constants.Constants;

@Entity(name=Constants.USER_TABLE_NAME)
public class User {
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", phoneNumber=" + phoneNumber + ", password="
				+ password + ", totalRewardPoint=" + totalRewardPoint + "]";
	}
	@Id
	@Column
	private String userId;
	@Column
	private String userName;
	@Column
	private String phoneNumber;
	@Column
	private String password;
	@Column
	private int totalRewardPoint;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userID) {
		this.userId = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTotalRewardPoint() {
		return totalRewardPoint;
	}
	public void setTotalRewardPoint(int totalRewardPoint) {
		this.totalRewardPoint = totalRewardPoint;
	}
	
}
