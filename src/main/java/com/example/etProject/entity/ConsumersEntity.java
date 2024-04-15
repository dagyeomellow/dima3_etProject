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
	
	@Id
	@Column(name = "CONSUMER_ID")
	private String consumerId;
	

	@OneToOne
	@JoinColumn(name = "MEMBER_ID")
	private MembersEntity membersConsumersEntity;
	
	@Column(name = "KEPCO_CUST_NUM")
	private String kepcoCustNum;
	
	@Column(name = "CUSTOMER_TYPE")
	private String customerType;
	
	@Column(name = "CONTRACT_TYPE")
	private String contractType;

	public static ConsumersEntity toEntity(ConsumersDTO consumersDTO, MembersEntity membersEntity){
		ConsumersEntity consumersEntity = ConsumersEntity.builder()
			.consumerId(consumersDTO.getConsumerId())
			.membersConsumersEntity(membersEntity)
			.kepcoCustNum(consumersDTO.getKepcoCustNum())
			.customerType(consumersDTO.getCustomerType())
			.contractType(consumersDTO.getContractType())
			.build();
		return consumersEntity;
	}

	
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
