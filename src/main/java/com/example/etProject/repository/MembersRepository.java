package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.MembersEntity;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity,String> {
    @Query("SELECT m.memberRole FROM MembersEntity m WHERE m.memberId = :memberId")
    String findMemberRoleByMemberId(@Param("memberId") String memberId);
}
