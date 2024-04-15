package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ConsumersEntity;

@Repository
public interface ConsumersRepository extends JpaRepository<ConsumersEntity, String> {

    @Query("SELECT c.consumerId FROM ConsumersEntity c WHERE c.membersConsumersEntity.memberId = :memberId")
    String findConsumerIdByMemberId(@Param("memberId") String memberId);

    @Query("SELECT c.contractType FROM ConsumersEntity c WHERE c.consumerId =:consumerId")
    String findContractTypeByConsumerId(@Param("consumerId") String consumerId);
}
