package com.example.bookexchange.controller;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.bookexchange.exception.MyException;
import com.example.bookexchange.pojo.Book;
import com.example.bookexchange.pojo.BorrowDetails;
import com.example.bookexchange.pojo.ExchangeDetails;
import com.example.bookexchange.pojo.User;
import com.example.bookexchange.repository.BookRepository;
import com.example.bookexchange.repository.BorrowDetailsRepository;
import com.example.bookexchange.repository.ExchangeDetailsRepository;
import com.example.bookexchange.repository.UserRepository;
import com.example.bookexchange.validation.BookExchangeValidation;

@RestController
public class BookExchangeController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	ExchangeDetailsRepository exchangeDetailsRepository;
	
	@Autowired
	BorrowDetailsRepository borrowDetailsRepository;
	
	@Autowired
	BookExchangeValidation bookExchangeValidation;
	
	@PostMapping("/verifyUser")
	public String verifyUser(@RequestBody User user) throws MyException
	{
		if(userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword())==null)
			 throw new MyException("Username or password is wrong");
		return "User Verified";
		
	} 
	
	@PostMapping("/getAllBooksForVerifiedUser")
	public List<Book> getAllExchangableBooksForVerifiedUser(@RequestBody User user) throws MyException
	{
		if(!userRepository.findById(user.getUserId()).isPresent())
			 throw new MyException("You are not verified user");
		return bookRepository.findAllExchangableBooksByUser(user.getUserId());
	} 
	
	@GetMapping("/getAllBooksForGuestUser")
	public List<Book> getALlBooksForGuestUser()
	{
		return bookRepository.findAllExchangableBooks();
	} 
	
	@PutMapping("/markBookExchangable")
	@Transactional
	public String markBookExchangable(@RequestBody Book book) throws MyException
	{

		if(bookRepository.findByBookIdAndUserId(book.getBookId(),book.getUserId())==null)
			 throw new MyException( "Book "+book.getBookId()+" not found for user "+book.getUserId());
		Book bookDb= bookRepository.findByBookIdAndUserId(book.getBookId(),book.getUserId());
		bookDb.setIsExchangable(book.getIsExchangable());
		bookRepository.save(bookDb);
		return "Succesfully marked book";
	}
	
	@PostMapping("/addUser")
	@Transactional
	public String addUser(@RequestBody User user) throws MyException
	{
		bookExchangeValidation.validateUser(user);
		if(userRepository.findById(user.getUserId()).isPresent())
			throw new MyException("UserId already exist please give different userid");
		userRepository.save(user);
		return "Succesfully added User";
	}
	
	@PostMapping("/addBook")
	@Transactional
	public String addBook(@RequestBody Book book) throws MyException
	{
		bookExchangeValidation.validateBook(book);
		if(bookRepository.findById(book.getBookId()).isPresent())
			throw new MyException("BookId already exist please give different bookid");
		if(!userRepository.findById(book.getUserId()).isPresent())
			throw new MyException("UserId is not present please provide correct userid");
		bookRepository.save(book);
		return "Succesfully added Book";
	}
	
	@PostMapping("/exchangeBook")
	@Transactional
	public String exchangeBook(@RequestBody ExchangeDetails exchangeDetails) throws MyException
	{	

		bookExchangeValidation.validateExchange(exchangeDetails);
		String bookId1=exchangeDetails.getBookId1();
		String bookId2=exchangeDetails.getBookId2();
		String userId1=exchangeDetails.getUserId1();
		String userId2=exchangeDetails.getUserId2();		
	
		bookExchangeValidation.checkUserIsRegisteredorNot(userId1);
		bookExchangeValidation.checkUserIsRegisteredorNot(userId2);
		bookExchangeValidation.checkBookIdIsPresentOrNot(bookId1);
		bookExchangeValidation.checkBookIdIsPresentOrNot(bookId2);
		bookExchangeValidation.checkBookIdIsAssignedToUserOrNot(bookId1,userId1);	
		bookExchangeValidation.checkBookIdIsAssignedToUserOrNot(bookId2,userId2);	
		bookExchangeValidation.checkBothUserAreSameOrNot(userId1,userId2);
		
		Book bookForUser1=bookRepository.findById(bookId1).get();
		Book bookForUser2=bookRepository.findById(bookId2).get();
		
		bookExchangeValidation.checkBookIsExchangableOrNot(bookForUser1);
		bookExchangeValidation.checkBookIsExchangableOrNot(bookForUser2);
		bookExchangeValidation.checkBookIsAlreadyBorrowedOrNot(bookId1);
		bookExchangeValidation.checkBookIsAlreadyBorrowedOrNot(bookId2);
		
		bookForUser1.setUserId(userId2);
		bookForUser2.setUserId(userId1);
		
		bookRepository.save(bookForUser1);
		bookRepository.save(bookForUser2);
		
		User user1= userRepository.findById(exchangeDetails.getUserId1()).get();
		User user2= userRepository.findById(exchangeDetails.getUserId2()).get();
		user1.setTotalRewardPoint(user1.getTotalRewardPoint()+1);
		user2.setTotalRewardPoint(user2.getTotalRewardPoint()+1);
		userRepository.save(user1);
		userRepository.save(user2);
		
		exchangeDetailsRepository.save(exchangeDetails);
		return "SuccessFully exchanged books";
	}


	@PostMapping("/borrowBook")
	@Transactional
	public String borrowBook(@RequestBody BorrowDetails borrowDetails) throws MyException
	{
		bookExchangeValidation.validateBorrowDetails(borrowDetails);
		String ownerId=borrowDetails.getOwnerId();
		String borrowerId= borrowDetails.getBorrowerId();
		String bookId= borrowDetails.getBookId();
		bookExchangeValidation.checkUserIsRegisteredorNot(ownerId);
		bookExchangeValidation.checkUserIsRegisteredorNot(borrowerId);
		bookExchangeValidation.checkBookIdIsPresentOrNot(bookId);
		bookExchangeValidation.checkBookIdIsAssignedToUserOrNot(bookId,ownerId);
		bookExchangeValidation.checkBothUserAreSameOrNot(ownerId,borrowerId);
		bookExchangeValidation.checkBookIsAlreadyBorrowedFromSameOwner(bookId, borrowerId, ownerId);
		
		User owner=userRepository.findById(ownerId).get();
		User borrower=userRepository.findById(borrowerId).get();
		
		bookExchangeValidation.checkRewardPointsAreSufficientOrNot(borrower);
		owner.setTotalRewardPoint(owner.getTotalRewardPoint()+1);
		borrower.setTotalRewardPoint(borrower.getTotalRewardPoint()-1);
		
		borrowDetailsRepository.save(borrowDetails);
	
		return "Successfully borrowed book from owner";
		
	}


	
	

}
