package com.example.bookexchange.repository;

import org.springframework.stereotype.Repository;

import com.example.bookexchange.pojo.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findById(String userId);
	
	User findByUserIdAndPassword(String userId,String password);

}
