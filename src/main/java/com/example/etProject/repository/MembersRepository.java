package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.MembersEntity;
import java.util.List;
import java.util.Optional;


@Repository
public interface MembersRepository extends JpaRepository<MembersEntity,Long> {
	
	List<MembersEntity> findByMemberId(String memberId);

}
