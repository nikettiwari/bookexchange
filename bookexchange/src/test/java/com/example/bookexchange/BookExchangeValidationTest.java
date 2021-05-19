package com.example.bookexchange;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Matchers.anyObject;

import com.example.bookexchange.exception.MyException;
import com.example.bookexchange.pojo.Book;
import com.example.bookexchange.pojo.BorrowDetails;
import com.example.bookexchange.pojo.User;
import com.example.bookexchange.repository.BookRepository;
import com.example.bookexchange.repository.BorrowDetailsRepository;
import com.example.bookexchange.repository.UserRepository;
import com.example.bookexchange.validation.BookExchangeValidation;

@RunWith(MockitoJUnitRunner.class)
public class BookExchangeValidationTest {
	
	@InjectMocks
	@Autowired
	BookExchangeValidation baseExchangeValidation;
	
	@Mock
	BorrowDetailsRepository borrowDetailsRepository;
	
	@Mock
	UserRepository userRepository;
	
	Optional<User> userOptionalEmpty;
	
	@Mock
	BookRepository bookRepository;
	
	Optional<Book> bookOptionalEmpty;
	
	User user;
	
	BorrowDetails borrowDetails;
	
	Book book;
	

	@Before
	public void setup()  {
		
		 user= new User();
		 user.setUserId("user123");
		 user.setPassword("password123");
		 user.setPhoneNumber("123456789");
		 user.setUserName("User 1");
		 user.setTotalRewardPoint(0);
		 
		 borrowDetails= new BorrowDetails();
		 borrowDetails.setBookId("book1");
		 borrowDetails.setBorrowerId("user123");
		 borrowDetails.setOwnerId("user1");
		 
		 book= new Book();
		 book.setBookId("bookId1");
		 book.setBookName("Book test");
		 book.setIsExchangable(false);
		 book.setUserId("user123");
		 book.setAuthorName("author1");
		 
		 bookOptionalEmpty=Optional.empty();
		 userOptionalEmpty=Optional.empty();
	}
	
	@Test(expected=MyException.class)
	public void testCheckRewardPointsAreSufficientOrNot() throws MyException
	{
		baseExchangeValidation.checkRewardPointsAreSufficientOrNot(user);
	}
	
	@Test(expected=MyException.class)
	public void testCheckBookIsAlreadyBorrowedFromSameOwner()throws MyException
	{
		when(borrowDetailsRepository.findByBookIdAndBorrowerIdAndOwnerId(anyObject(),anyObject(),anyObject())).thenReturn(borrowDetails);
		baseExchangeValidation.checkBookIsAlreadyBorrowedFromSameOwner("test","test","test");
	}
	
	@Test(expected=MyException.class)
	public void testCheckBookIsAlreadyBorrowedOrNot()throws MyException
	{
		when(borrowDetailsRepository.findByBookId(anyObject())).thenReturn(borrowDetails);
		baseExchangeValidation.checkBookIsAlreadyBorrowedOrNot("test");
	}
	
	@Test(expected=MyException.class)
	public void testCheckBookIsExchangableOrNot()throws MyException
	{
		when(borrowDetailsRepository.findByBookId(anyObject())).thenReturn(borrowDetails);
		baseExchangeValidation.checkBookIsExchangableOrNot(book);
	}

	@Test(expected=MyException.class)
	public void testcheckBothUserAreSameOrNot()throws MyException
	{
		baseExchangeValidation.checkBothUserAreSameOrNot("userId1","userId1");
	}
	
	@Test(expected=MyException.class)
	public void testCheckBookIdIsAssignedToUserOrNot()throws MyException
	{
		when(bookRepository.findByBookIdAndUserId(anyObject(),anyObject())).thenReturn(null);
		baseExchangeValidation.checkBookIdIsAssignedToUserOrNot("bookid","userid");
	}
	
	@Test(expected=MyException.class)
	public void testCheckBookIdIsPresentOrNot()throws MyException
	{
		when(bookRepository.findById(anyObject())).thenReturn(bookOptionalEmpty);
		baseExchangeValidation.checkBookIdIsPresentOrNot("bookId");
	}
	
	@Test(expected=MyException.class)
	public void testCheckUserIsRegisteredorNot()throws MyException
	{
		when(userRepository.findById(anyObject())).thenReturn(userOptionalEmpty);
		baseExchangeValidation.checkUserIsRegisteredorNot("userId");
	}
}
