package com.example.etProject.service;

import java.util.List;

import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.example.etProject.entity.MembersEntity;
import com.example.etProject.repository.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final MembersRepository membersRepository;

	public  boolean idCheck(String idUnChecked) {
		// TODO Auto-generated method stub
		
		List<MembersEntity> membersEntity= membersRepository.findByMemberId(idUnChecked);
		
		if (membersEntity.isEmpty()) {
			return true;
		}
		else return false;
	}
=======
import com.example.etProject.dto.MembersDTO;
import com.example.etProject.entity.MembersEntity;
import com.example.etProject.repository.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final MembersRepository membersRepository;
>>>>>>> ef81e0397825fd7abaaa48baf1e59c1268f31626

	/**
	 * 소비자의 회원가입을 처리하는 메소드
	 * @param membersDTO
	 */
	public void  consumerJoin(
		MembersDTO membersDTO
	){
		membersRepository.save(MembersEntity.toEntity(membersDTO));
	}
}
