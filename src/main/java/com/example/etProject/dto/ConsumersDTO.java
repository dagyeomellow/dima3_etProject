package com.example.etProject.dto;

import com.example.etProject.entity.ConsumersEntity;
import com.example.etProject.entity.ConsumersEntity.ContractType;
import com.example.etProject.entity.ConsumersEntity.CustomerType;

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
	private CustomerType customerType;
	private ContractType contractType;
	
	
	public static ConsumersDTO toDTO(ConsumersEntity consumersEntity) {
		return ConsumersDTO.builder()
				.consumerId(consumersEntity.getConsumerId())
				// nullable false이므로 null값이 올 경우는 가정하지 않는다.
				.memberId(consumersEntity.getMembersEntity().getMemberId())
				.kepcoCustNum(consumersEntity.getKepcoCustNum())
				.customerType(consumersEntity.getCustomerType())
				.contractType(consumersEntity.getContractType())
				.build();
	 }
	

}

