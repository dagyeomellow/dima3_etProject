package com.example.etProject.entity;

import java.util.List;
import java.util.Random;

import com.example.etProject.dto.ConsumersDTO;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "CONSUMERS")
public class ConsumersEntity {
	
	// * 아주 낮은 확률로 중복가능성이 있으므로 어떻게 처리하면 좋을지 다시 생각해보기
	public class KeyGenerator {
		public static String generateCustomerId() {
			Random random = new Random();
			int randomNumber = random.nextInt(900_000_000) + 100_000_000; // 100_000_000 ~ 999_999_999
			return "SPC" + randomNumber;
			}
		}
	
	
	@Id
	@Column(name = "CONSUMER_ID", nullable = false)
	// 전력소비회원 인조키, 예: SPC123456789(SP: 사이트약어, C: 소비, 숫자9자리: 난수발생), 중복불가
	private String consumerId= KeyGenerator.generateCustomerId();
	
	
	@OneToOne
	@JoinColumn(name = "memberId", nullable = false)
	private MembersEntity membersEntity;
	
	
	@Column(name = "KEPCO_CUST_NUM", nullable = false)
	private String kepcoCustNum;
	
	public enum CustomerType {주택용, 일반용};
	
	@Column(name = "CUSTOMER_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private CustomerType customerType;
	
	
	public enum ContractType {저압, 고압, 갑1, 갑2, 을};
	
	@Column(name = "CONTRACT_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private ContractType contractType;

	
	@OneToMany(mappedBy = "consumersEntity",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private List<ConsumptionsEntity> consumptionsEntity= new ArrayList<>();
	
	
	public static ConsumersEntity toEntity(ConsumersDTO consumersDTO) {
		return ConsumersEntity.builder()
				.consumerId(consumersDTO.getConsumerId())
				.membersEntity(MembersEntity.builder().memberId(consumersDTO.getMemberId()).build())
				.kepcoCustNum(consumersDTO.getKepcoCustNum())
				.customerType(consumersDTO.getCustomerType())
				.contractType(consumersDTO.getContractType())
				.build();
	}


}



