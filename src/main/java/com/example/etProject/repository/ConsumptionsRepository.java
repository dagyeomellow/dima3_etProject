package com.example.etProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ConsumptionsEntity;

@Repository
public interface ConsumptionsRepository extends JpaRepository<ConsumptionsEntity,Long> {

    @Query("SELECT c.consDate, c.consElectricity, c.billsElecAmount FROM ConsumptionsEntity c WHERE c.consumersEntity.consumerId = :consumerId ORDER BY c.consDate DESC")
    List<Object[]> findAllConsumptionsByConsumerId(@Param("consumerId") String consumerId);
}
