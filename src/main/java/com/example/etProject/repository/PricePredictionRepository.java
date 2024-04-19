package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.PricePredictionEntity;

@Repository
public interface PricePredictionRepository extends JpaRepository<PricePredictionEntity,String> {

}
