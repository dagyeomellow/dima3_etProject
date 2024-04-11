package com.example.etProject.service;

import org.springframework.stereotype.Service;

import com.example.etProject.dto.MembersDTO;
import com.example.etProject.entity.MembersEntity;
import com.example.etProject.repository.MembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final MembersRepository membersRepository;

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
