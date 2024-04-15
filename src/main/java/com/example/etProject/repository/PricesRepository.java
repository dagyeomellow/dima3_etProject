package com.example.etProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.etProject.entity.PricesEntity;

public interface PricesRepository extends JpaRepository<PricesEntity,Long> {
    @Query("SELECT p FROM PricesEntity p WHERE p.yearMonth = :yearMonth")
    PricesEntity findHousePricesByType(@Param("yearMonth") Long yearMonth);
}
