package com.example.bookexchange;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.bookexchange.controller.BookExchangeController;
import com.example.bookexchange.exception.MyException;
import com.example.bookexchange.pojo.Book;
import com.example.bookexchange.pojo.User;
import com.example.bookexchange.repository.BookRepository;
import com.example.bookexchange.repository.UserRepository;
import com.example.bookexchange.validation.BookExchangeValidation;

@RunWith(MockitoJUnitRunner.class)
public class BookExchangeControllerTest {
	
	@InjectMocks
	@Autowired
	BookExchangeController bookExchangeController;

	@Mock
	UserRepository userRepository;
	
	@Mock
	BookRepository bookRepository;
		
	Optional<User> userOptionalNotEmpty;
	
	Optional<User> userOptionalEmpty;
	
	Optional<Book> bookOptionalNotEmpty;
	
	Optional<Book> bookOptionalEmpty;
	
	@Mock
	BookExchangeValidation bookExchangeValidation;
	
	User user;
	
	Book book;
	
	@Before
	public void setup() throws MyException  {
		
		 user= new User();
		 user.setUserId("user123");
		 user.setPassword("password123");
		 user.setPhoneNumber("123456789");
		 user.setUserName("User 1");
		 user.setTotalRewardPoint(1);
		 
		 book= new Book();
		 book.setBookId("bookId1");
		 book.setBookName("Book test");
		 book.setIsExchangable(true);
		 book.setUserId("user123");
		 book.setAuthorName("author1");
		 
		 userOptionalEmpty=Optional.empty();
		 userOptionalNotEmpty=Optional.of(user);
		 bookOptionalEmpty=Optional.empty();
		 bookOptionalNotEmpty=Optional.of(book);
		
		 when(userRepository.save(anyObject())).thenReturn(user);
		 Mockito.doNothing().when(bookExchangeValidation).validateUser(anyObject());
		
	}
	
	@Test(expected=MyException.class)
	public void testAddUserIdAlreadyExist() throws MyException
	{
		 when(userRepository.findById(Mockito.anyObject())).thenReturn(userOptionalNotEmpty);
		bookExchangeController.addUser(user);	
	}

	@Test
	public void testAddUserIdisSaved() throws MyException
	{
		when(userRepository.findById(Mockito.anyObject())).thenReturn(userOptionalEmpty);
		assertEquals("Succesfully added User",bookExchangeController.addUser(user));	
	}
	
	@Test(expected=MyException.class)
	public void testAddBookIdAlreadyExist() throws MyException
	{
		 when(bookRepository.findById(Mockito.anyObject())).thenReturn(bookOptionalNotEmpty);
		 bookExchangeController.addBook(book);	
	}

	@Test
	public void testAddBookIdisSaved() throws MyException
	{
		 when(userRepository.findById(Mockito.anyObject())).thenReturn(userOptionalNotEmpty);
		 when(bookRepository.findById(Mockito.anyObject())).thenReturn(bookOptionalEmpty);
		 assertEquals("Succesfully added Book",bookExchangeController.addBook(book));	
	}

	
}
