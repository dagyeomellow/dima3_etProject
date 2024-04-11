package com.example.etProject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

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
			name="member_seq",
			sequenceName = "MEMBER_SEQ",
			initialValue = 0,
			allocationSize = 1
			)
	
	
	@Id
	@Column(name="MEMBER_NUM")
	@GeneratedValue(generator = "member_seq")
	private Long memberNum;
	
	@Column(name="MEMBER_ID", nullable = false)
	private String memberId;
	
	
	@Column(name="MEMBER_PW", nullable = false)
	private String memberPw;
	
	@Column(name="JOIN_DATE", nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP") // DEFAULT SYSDATE. 서버처리 딜레이를 줄이기 위해 @CreationTimestamp 사용x
	private LocalDateTime joinDate;
	
	@Column(name="NATIONL_ID", nullable = false)
	private String nationalId;
	
	@Column(name="MEMBER_ADDR", nullable = false)
	private String memberAddr;
	
	@Column(name="MEMBER_ADDR_DETAIL")
	private String memberAddrDetail;
	
	@Column(name="MEMBER_ROLE", nullable = false)
	@ColumnDefault("ROLE_CONSUMER") // DEFAULT 'ROLE_CONSUMER'
	private String memberRole; 
	
	@Column(name="IS_AGREE", nullable = false)
	@ColumnDefault("1")// DEFAULT 1
	private boolean isAgree;

	
	public static MembersEntity toEntity(MembersDTO membersDTO) {
		return MembersEntity.builder()
				.memberNum(membersDTO.getMemberNum())
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
}


