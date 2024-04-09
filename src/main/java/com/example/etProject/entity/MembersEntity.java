package com.example.etProject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.etProject.dto.MembersDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// sales board, sales inquiry, consumers 관계설정

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="MEMBERS")
public class MembersEntity {
	
	@SequenceGenerator(
			name="members_seq",
			sequenceName = "MEMBERS_SEQ",
			initialValue = 1,
			allocationSize = 1
			)
	
	
	@Id
	@Column(name="MEMBERS_NUM")
	@GeneratedValue(generator = "members_seq")
	private Long membersNum;
	
	@Column(name="MEMBER_ID")
	private String memberId;
	
	@Column(name="MEMBER_PW")
	private String memberPw;
	
	@Column(name="JOIN_DATE")
	private LocalDateTime joinDate;
	
	@Column(name="NATIONL_ID")
	private String nationalId;
	
	@Column(name="MEMBER_ADDR")
	private String memberAddr;
	
	@Column(name="MEMBER_ADDR_DETAIL")
	private String memberAddrDetail;
	
	@Column(name="MEMBER_ROLE")
	private String memberRole= "CONSUMER"; // DEFAULT 'CONSUMER'
	
	@Column(name="IS_AGREE")
	private boolean isAgree= true; // DEFAULT 1

	
	public static MembersEntity toEntity(MembersDTO membersDTO) {
		return MembersEntity.builder()
				.membersNum(membersDTO.getMembersNum())
				.memberId(membersDTO.getMemberId())
				.memberPw(membersDTO.getMemberPw())
				.joinDate(membersDTO.getJoinDate())
				.nationalId(membersDTO.getNationalId())
				.memberAddr(membersDTO.getMemberAddr())
				.memberAddrDetail(membersDTO.getMemberAddrDetail())
				.memberRole(membersDTO.getMemberRole())
				.isAgree(membersDTO.isAgree())
				.build();
	}
	
	// SALES_BOARD 일대다 관계설정
	@OneToMany(mappedBy = "membersEntity",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private List<SalesBoardEntity> salesBoardEntity= new ArrayList<>();
	
}
