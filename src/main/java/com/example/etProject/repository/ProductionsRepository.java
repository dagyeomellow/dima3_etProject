package com.example.etProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ProductionsEntity;

@Repository
public interface ProductionsRepository extends JpaRepository<ProductionsEntity, Long>{

    @Query("SELECT p.prodElectricity FROM ProductionsEntity p WHERE p.producersEntity.producerId = :producerId")
    List<Double> findProdElectricityByProducerId(@Param("producerId") String producerId);
}
/*
 * @Query("SELECT p.producerId FROM ProducersEntity p WHERE p.membersEntity.memberId = :memberId")
String findProducerIdByMemberId(@Param("memberId") String memberId);
 */