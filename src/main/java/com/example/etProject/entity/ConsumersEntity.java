package com.example.etProject.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.etProject.dto.ConsumersDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
	/*
	public class KeyGenerator {
		public static String generateCustomerId() {
			Random random = new Random();
			int randomNumber = random.nextInt(900_000_000) + 100_000_000; // 100_000_000 ~ 999_999_999
			return "SPC" + randomNumber;
			}
		}
		
	*/
	
	@Id
	@Column(name = "CONSUMER_ID")
	private String consumerId;
	

	@OneToMany(
			mappedBy = "consumersEntity",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true,
			fetch = FetchType.LAZY
			)
	private List<ConsumptionsEntity> consumptionsEntity= new ArrayList<>();
	
	
	@OneToOne
	@JoinColumn(name = "MEMBER_ID")
	private MembersEntity membersEntity;
	
	@Column(name = "KEPCO_CUST_NUM")
	private String kepcoCustNum;
	
	@Column(name = "CUSTOMER_TYPE")
	private String customerType;
	
	@Column(name = "CONTRACT_TYPE")
	private String contractType;
	
	/*
	public static ConsumersEntity toEntity(ConsumersDTO consumersDTO) {
		return ConsumersEntity.builder()
				.consumerId(consumersDTO.getConsumerId())
				
	}
	*/
	
}

/*
CREATE TABLE CONSUMERS (

CONSUMER_ID VARCHAR2(50) PRIMARY KEY NOT NULL,
MEMBER_ID VARCHAR2(50) FOREIGN KEY, 구문수정
KEPCO_CUST_NUM VARCHAR2(50) NOT NULL,
CUSTOMER_TYPE VARCHAR2(50) NOT NULL,
CONTRACT_TYPE VARCHAR2(50) NOT NULL
);
CREATE SEQUENCE CONSUMER_SEQ;
*/
