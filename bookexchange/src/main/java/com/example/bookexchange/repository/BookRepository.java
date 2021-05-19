package com.example.bookexchange.repository;

import org.springframework.stereotype.Repository;

import com.example.bookexchange.pojo.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BookRepository extends JpaRepository<Book,String> {
	
	Optional<Book> findById(String bookId);
	Book findByBookIdAndUserId(String bookId,String userId);
	
	@Query(value ="Select * from Book  where userId=:userId and isExchangable=true",nativeQuery = true)
	List<Book> findAllExchangableBooksByUser(@Param("userId") String userId);
	
	@Query(value ="Select * from Book where isExchangable=true",nativeQuery = true)
	List<Book> findAllExchangableBooks();
	

}
