package com.example.etProject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.etProject.dto.MembersDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	
	@Column(name="MEMBER_ID", nullable = false, unique = true)
	private String memberId;
	
	@Column(name="MEMBER_PW", nullable = false)
	private String memberPw;
	
	@Column(name="JOIN_DATE", nullable = false)
	private LocalDateTime joinDate= LocalDateTime.now(); ; // DEFAULT SYSDATE
	
	@Column(name="NATIONL_ID", nullable = false, unique = true)
	private String nationalId;
	
	@Column(name="MEMBER_ADDR", nullable = false)
	private String memberAddr;
	
	@Column(name="MEMBER_ADDR_DETAIL")
	private String memberAddrDetail;
	
	public enum memberRoles {ROLE_CONSUMER, ROLE_PROSUMER};
	
	@Column(name="MEMBER_ROLE", nullable = false)
	@Enumerated(EnumType.STRING)
	private memberRoles memberRole= memberRoles.ROLE_CONSUMER; // DEFAULT 'CONSUMER'
	
	@Column(name="IS_AGREE", nullable = false)
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
	
	// SALES_INQUIRY 일대다 관계설정
	@OneToMany(mappedBy = "membersEntity",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private List<SalesInquiryEntity> salesInquiryEntity= new ArrayList<>();
	
	
	
	
}
