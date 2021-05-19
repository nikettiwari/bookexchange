package com.example.bookexchange.constants;

public final class Constants {
	
	  private Constants() {
			throw new IllegalStateException("Cannot Instantiate Constants");
		}

    public static final String BOOK_TABLE_NAME = "Book";
    public static final String USER_TABLE_NAME = "User";
    public static final String BOOKEXCHANGE_TABLE_NAME = "BookExchangeDetails";
    public static final String BORROW_TABLE_NAME = "BorrowDetails";

}
