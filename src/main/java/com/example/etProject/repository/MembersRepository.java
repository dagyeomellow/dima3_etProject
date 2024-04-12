package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.MembersEntity;
import java.util.List;
import java.util.Optional;


@Repository
<<<<<<< HEAD
public interface MembersRepository extends JpaRepository<MembersEntity,Long> {
	
	List<MembersEntity> findByMemberId(String memberId);
=======
public interface MembersRepository extends JpaRepository<MembersEntity,String> {
>>>>>>> ef81e0397825fd7abaaa48baf1e59c1268f31626

}
