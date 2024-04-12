package com.example.etProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.etProject.entity.MembersEntity;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity,String> {

}
