package com.example.etProject.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.etProject.dto.LoginUserDetails;
import com.example.etProject.dto.MembersDTO;
import com.example.etProject.entity.MembersEntity;
import com.example.etProject.repository.MembersRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
	private final MembersRepository membersRepository;


	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		MembersEntity memberEntity= membersRepository.findById(memberId)
			.orElseThrow(
				() -> {
					throw new UsernameNotFoundException("유저아이디 못찾음");
				}
			);
		MembersDTO memberDTO = MembersDTO.toDTO(memberEntity);
		LoginUserDetails loginMemberDTO = new LoginUserDetails(memberDTO);
		// log.info(loginMemberDetails.toString());
		return loginMemberDTO;
	}

}