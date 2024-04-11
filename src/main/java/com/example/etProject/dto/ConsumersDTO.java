package com.example.etProject.dto;

import com.example.etProject.entity.ConsumersEntity;

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
public class ConsumersDTO {
	
	private String consumerId;
	private String memberId;
	private String kepcoCustNum;
	private String customerType;
	private String contractType;
	
	public static ConsumersDTO toDTO(ConsumersEntity consumersEntity) {
		return ConsumersDTO.builder()
				.consumerId(consumersEntity.getConsumerId())
				.memberId(consumersEntity.getConsumerId())
				.kepcoCustNum(consumersEntity.getConsumerId())
				.customerType(consumersEntity.getCustomerType())
				.contractType(consumersEntity.getContractType())
				.build();
	}

}

/*
    CREATE TABLE CONSUMERS (
 
	CONSUMER_ID VARCHAR2(50) PRIMARY KEY NOT NULL,
	MEMBER_ID VARCHAR2(50) FOREIGN KEY,
	KEPCO_CUST_NUM VARCHAR2(50) NOT NULL,
	CUSTOMER_TYPE VARCHAR2(50) NOT NULL,
	CONTRACT_TYPE VARCHAR2(50) NOT NULL
);
CREATE SEQUENCE CONSUMER_SEQ;
*/