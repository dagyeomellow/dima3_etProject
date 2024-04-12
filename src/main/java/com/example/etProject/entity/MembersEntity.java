package com.example.etProject.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.example.etProject.dto.MembersDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
	
	// @SequenceGenerator(
	// 		name="MEMBER_SEQ",
	// 		sequenceName = "MEMBER_SEQ",
	// 		initialValue = 0,
	// 		allocationSize = 1
	// 		)
	
	

	// @Column(name="MEMBER_NUM")
	// @GeneratedValue(generator = "MEMBER_SEQ")
	// private Long memberNum;
	
	@Id
	@Column(name="MEMBER_ID")
	private String memberId;
	
	
	@Column(name="MEMBER_PW")
	private String memberPw;
	
	@Column(name="JOIN_DATE")
	@CreationTimestamp
	private LocalDateTime joinDate; // DEFAULT SYSDATE
	
	@Column(name="NATIONL_ID")
	private String nationalId;
	
	@Column(name="MEMBER_ADDR")
	private String memberAddr;
	
	@Column(name="MEMBER_ADDR_DETAIL")
	private String memberAddrDetail;
		
	@Column(name="MEMBER_ROLE")
	@ColumnDefault("ROLE_CONSUMER")
	private String memberRole;
	
	@Column(name="IS_AGREE", nullable = false)
	@ColumnDefault("1")
	private boolean isAgree; // DEFAULT 1

	
	public static MembersEntity toEntity(MembersDTO membersDTO) {
		return MembersEntity.builder()
				// .memberNum(membersDTO.getMemberNum())
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
	@OneToOne(mappedBy = "membersEntity",
		cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	private ProducersEntity producersEntity = new ProducersEntity();
	// @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval=true)
    // @OrderBy("reply_num desc")// 댓글을 정렬할건지
    // private List<ReplyEntity> replyList = new ArrayList<>();
}


