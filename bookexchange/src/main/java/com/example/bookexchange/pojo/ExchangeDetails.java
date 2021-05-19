package com.example.bookexchange.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.bookexchange.constants.Constants;


@Entity(name=Constants.BOOKEXCHANGE_TABLE_NAME)
public class ExchangeDetails {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long exchangeId;
	
	@Column
	private String userId1;
	
	@Column
	private String userId2;
	@Column
	private String bookId1;
	@Column
	private String bookId2;
	
	public String getUserId1() {
		return userId1;
	}
	public void setUserId1(String userId1) {
		this.userId1 = userId1;
	}
	public String getUserId2() {
		return userId2;
	}
	public void setUserId2(String userId2) {
		this.userId2 = userId2;
	}
	public String getBookId1() {
		return bookId1;
	}
	public void setBookId1(String bookId1) {
		this.bookId1 = bookId1;
	}
	public String getBookId2() {
		return bookId2;
	}
	public void setBookId2(String bookId2) {
		this.bookId2 = bookId2;
	}


}
