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
	
	
	public ConsumersDTO toDTO(ConsumersEntity consumersEntity) {
		return ConsumersDTO.builder()
				.consumerId(consumersEntity.getConsumerId())
				.memberId(consumersEntity.getMemberId())
				.kepcoCustNum(consumersEntity.getKepcoCustNum())
				.customerType(consumersEntity.getCustomerType())
				.contractType(consumersEntity.getContractType())
				.build();
	 }
	

}

