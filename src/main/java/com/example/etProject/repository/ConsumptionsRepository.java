package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ConsumptionsEntity;

@Repository
public interface ConsumptionsRepository extends JpaRepository<ConsumptionsEntity,String>{

}
