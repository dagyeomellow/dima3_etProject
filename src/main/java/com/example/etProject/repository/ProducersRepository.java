package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.ProducersEntity;

@Repository
public interface ProducersRepository extends JpaRepository<ProducersEntity, String> {

    @Query("SELECT PRODUCER_ID FROM PRODUCERS P WHERE P.MEMBER_ID = %:memberId%")
    String findProducerIdByMemberId(@Param("memberId") String memberId);
}

/*
 * @Query("SELECT b FROM BoardEntity b WHERE b.contents LIKE %:searchWord%")
    List<BoardEntity> findByContents(@Param("searchWord") String searchWord);
 */
