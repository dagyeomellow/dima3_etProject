package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ProducersEntity;

@Repository
public interface ProducersRepository extends JpaRepository<ProducersEntity, String>{

}
