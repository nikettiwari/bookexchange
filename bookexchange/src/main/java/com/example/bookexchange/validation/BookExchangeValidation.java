package com.example.bookexchange.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookexchange.exception.MyException;
import com.example.bookexchange.pojo.Book;
import com.example.bookexchange.pojo.BorrowDetails;
import com.example.bookexchange.pojo.ExchangeDetails;
import com.example.bookexchange.pojo.User;
import com.example.bookexchange.repository.BookRepository;
import com.example.bookexchange.repository.BorrowDetailsRepository;
import com.example.bookexchange.repository.ExchangeDetailsRepository;
import com.example.bookexchange.repository.UserRepository;

@Service
public class BookExchangeValidation {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	ExchangeDetailsRepository exchangeDetailsRepository;
	
	@Autowired
	BorrowDetailsRepository borrowDetailsRepository;
	
	public  void checkRewardPointsAreSufficientOrNot(User borrower) throws MyException {
		if(borrower.getTotalRewardPoint()==0)
			throw new MyException( "Borrower "+borrower.getUserId()+ " does not have sufficient points so he can not borrow book");		
	}

	public void checkBookIsAlreadyBorrowedFromSameOwner(String bookId, String borrowerId, String ownerId) throws MyException {
		if(borrowDetailsRepository.findByBookIdAndBorrowerIdAndOwnerId(bookId, borrowerId, ownerId)!=null)
			throw new MyException( "You already borrowed book "+bookId+" from owner"+ownerId);	
	}
	
	
	
	public void checkBookIsAlreadyBorrowedOrNot(String bookId1) throws MyException {
		if(borrowDetailsRepository.findByBookId(bookId1)!=null)
			throw new MyException( bookId1+ "is borrowed by some other user so can not exchange");
		
	}

	public void checkBookIsExchangableOrNot(Book bookForUser) throws MyException {
		if(!bookForUser.getIsExchangable())
			throw new MyException( bookForUser.getBookId() +" is not exchangable");
		
	}

	public void checkBothUserAreSameOrNot(String userId1, String userId2) throws MyException {
		if(userId1.equals(userId2))
			throw new MyException( "both userIds are same please provide different userids");
		
	}

	public void checkBookIdIsAssignedToUserOrNot(String bookId, String userId) throws MyException {
		if(bookRepository.findByBookIdAndUserId(bookId,userId)==null)
			throw new MyException( "Bookid "+ bookId+" is not registered with user "+userId);
	}

	public void checkBookIdIsPresentOrNot(String bookId) throws MyException {
		if(!bookRepository.findById(bookId).isPresent())
			throw new MyException( "Book id "+bookId+" is not present . please provide correct bookid");
		
	}

	public void checkUserIsRegisteredorNot(String userId) throws MyException {
		System.out.println(" --- "+userRepository);
		if(!userRepository.findById(userId).isPresent())
			throw new MyException("userid "+userId+" is not registered user so he/she can not exchange book");
	}

	public void validateUser(User user) throws MyException {
		if(user.getUserId()==null)
			throw new MyException("userid is manadatory field");
		if(user.getPassword()==null || user.getPassword().length()<5)	
			throw new MyException("password is manadatory field and its length should be greater than 5");
		if(user.getUserName()==null)
			throw new MyException("username is manadatory field");
	}

	public void validateProduct(Book book) throws MyException {
		if(book.getBookId()==null)
			throw new MyException("bookId is manadatory field");
		if(book.getBookName()==null)
			throw new MyException("Book name is manadatory field");
		if(book.getUserId()==null)
			throw new MyException("UserId is manadatory field");
	
	}

	public void validateExchange(ExchangeDetails exchangeDetails) throws MyException {
		if(exchangeDetails.getBookId1()==null || exchangeDetails.getBookId2()==null || exchangeDetails.getUserId1()==null
			|| exchangeDetails.getUserId2()==null)
		{
			throw new MyException("Bothe userId and both bookid are manadatory fields");
		}
		
	}

	public void validateBorrowDetails(BorrowDetails borrowDetails) throws MyException {
		if(borrowDetails.getBookId()==null || borrowDetails.getOwnerId()==null || borrowDetails.getOwnerId()==null)
		{
			throw new MyException("Bothe userId and both bookid are manadatory fields");
		}
		
	}

}
