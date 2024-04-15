package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.etProject.entity.PricePredictionEntity;

public interface PricePredictionRepository extends JpaRepository<PricePredictionEntity,Long> {

}
