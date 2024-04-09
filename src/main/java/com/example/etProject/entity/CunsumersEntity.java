package com.example.etProject.entity;

import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//sales board, sales inquiry, consumers 관계설정


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "CUNSUMERS")
public class CunsumersEntity {
	
	// * 아주 낮은 확률로 중복가능성이 있으므로 어떻게 처리하면 좋을지 다시 생각해보기
	public class KeyGenerator {
		public static String generateCustomerId() {
			Random random = new Random();
			int randomNumber = random.nextInt(900_000_000) + 100_000_000; // 100_000_000 ~ 999_999_999
			return "SPC" + randomNumber;
			}
		}
	
	
	@Id
	@Column(name = "CONSUMER_ID")
	// 전력소비회원 인조키, 예: SPC123456789(SP: 사이트약어, C: 소비, 숫자9자리: 난수발생), 중복불가
	private String consumerId= KeyGenerator.generateCustomerId();
	
	
	@Column(name = "MEMBER_ID")
	private String memberId;
	
	@Column(name = "KEPCO_CUST_NUM")
	private String kepcoCustNum;
	
	
	public enum CustomerType {주택용, 일반용};
	
	@Column(name = "CUSTOMER_TYPE")
	@Enumerated(EnumType.STRING)
	private String customerType;
	
	
	public enum ContractType {저압, 고압, 갑1, 갑2, 을};
	
	@Column(name = "CONTRACT_TYPE")
	@Enumerated(EnumType.STRING)
	private String contractType;
	
	@OneToOne
	@JoinColumn(name = "MEMBER_ID")
	private MembersEntity membersEntity; //@ 확인
	
}



