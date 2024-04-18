package com.example.etProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ProducersEntity;

@Repository
public interface ProducersRepository extends JpaRepository<ProducersEntity, String> {

    @Query("SELECT p.producerId FROM ProducersEntity p WHERE p.membersProducersEntity.memberId = :memberId")
    String findProducerIdByMemberId(@Param("memberId") String memberId);

    @Query("SELECT p.locationX FROM ProducersEntity p WHERE p.membersProducersEntity.memberId = :memberId")
    Double findLocationXByMemberId(@Param("memberId") String memberId);
    @Query("SELECT p.locationY FROM ProducersEntity p WHERE p.membersProducersEntity.memberId = :memberId")
    Double findLocationYByMemberId(@Param("memberId") String memberId);
    @Query("SELECT p.installedCapacity FROM ProducersEntity p WHERE p.membersProducersEntity.memberId = :memberId")
    int findInstalledCapacityByMemberId(@Param("memberId") String memberId);
}

/*
 * @Query("SELECT b FROM BoardEntity b WHERE b.contents LIKE %:searchWord%")
    List<BoardEntity> findByContents(@Param("searchWord") String searchWord);
 */
