package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ConsumersEntity;

@Repository
public interface ConsumersRepository extends JpaRepository<ConsumersEntity, String> {

}
