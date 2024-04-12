package com.example.etProject.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

}
