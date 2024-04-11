package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ProductionsEntity;

@Repository
public interface ProductionsRepository extends JpaRepository<ProductionsEntity, Long>{

}
