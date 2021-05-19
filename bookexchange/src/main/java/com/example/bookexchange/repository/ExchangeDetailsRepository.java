package com.example.bookexchange.repository;

import org.springframework.stereotype.Repository;

import com.example.bookexchange.pojo.ExchangeDetails;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ExchangeDetailsRepository extends JpaRepository<ExchangeDetails, Long> {

}
