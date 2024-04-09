package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.SalesBoardEntity;

@Repository
public interface SalesBoardRepository extends JpaRepository<SalesBoardEntity,Long>{

}
