package com.example.etProject.dto;

import java.time.LocalDateTime;

import com.example.etProject.entity.MembersEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembersDTO {
	private Long membersNum;
	private String memberId;
	private String memberPw;
	private LocalDateTime joinDate;
	private String nationalId;
	private String memberAddr;
	private String memberAddrDetail;
	private String memberRole; // 확인
	private boolean isAgree;
	
	public static MembersDTO toDTO(MembersEntity membersEntity) {
		return MembersDTO.builder()
				.membersNum(membersEntity.getMembersNum())
				.memberId(membersEntity.getMemberId())
				.memberPw(membersEntity.getMemberPw())
				.joinDate(membersEntity.getJoinDate())
				.nationalId(membersEntity.getNationalId())
				.memberAddr(membersEntity.getMemberAddr())
				.memberAddrDetail(membersEntity.getMemberAddrDetail())
				.memberRole(membersEntity.getMemberRole())
				.isAgree(membersEntity.isAgree())
				// .memberRole(membersEntity.getMemberRole())
				// .enabled 보안설정 후 속성설정
				.build();
	}
	
}


