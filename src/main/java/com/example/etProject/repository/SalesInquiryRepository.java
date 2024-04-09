package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.SalesInquiryEntity;

@Repository
public interface SalesInquiryRepository extends JpaRepository<SalesInquiryEntity,Long>{

}
