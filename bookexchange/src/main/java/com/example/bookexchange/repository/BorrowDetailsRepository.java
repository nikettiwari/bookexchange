package com.example.bookexchange.repository;

import org.springframework.stereotype.Repository;

import com.example.bookexchange.pojo.BorrowDetails;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BorrowDetailsRepository extends JpaRepository<BorrowDetails, Long> {
	
	BorrowDetails findByBookId(String bookId);
	BorrowDetails findByBookIdAndBorrowerIdAndOwnerId(String bookId,String borrowerId,String ownerId);
}
