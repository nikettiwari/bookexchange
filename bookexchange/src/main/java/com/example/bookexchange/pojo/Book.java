package com.example.bookexchange.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.example.bookexchange.constants.Constants;

@Entity(name=Constants.BOOK_TABLE_NAME)
public class Book {
	@Id
	@Column
	private String bookId;
	
	@Column(nullable = false)
	private String userId;
	
	@Column
	private String bookName;
	
	@Column
	private String authorName;
	
	@Column
	private boolean isExchangable;
	
	public Boolean getIsExchangable() {
		return isExchangable;
	}
	public void setIsExchangable(Boolean isExchangable) {
		this.isExchangable = isExchangable;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
