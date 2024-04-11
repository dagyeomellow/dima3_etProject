package com.example.etProject.dto;

import com.example.etProject.entity.ProducersEntity;

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
public class ProducersDTO {
	private String producerId;
	private String memberId;
	private String memberAddr;
	private double locationX;
	private double locationY;
	private int installedCapacity; 
	private String moduleId;
	
	public static ProducersDTO toDTO(ProducersEntity producersEntity) {
		return ProducersDTO.builder()
				.producerId(producersEntity.getProducerId())
				.memberId(producersEntity.getMemberId())
				.memberAddr(producersEntity.getMemberAddr())
				.locationX(producersEntity.getLocationX())
				.locationY(producersEntity.getLocationY())
				.installedCapacity(producersEntity.getInstalledCapacity())
				.moduleId(producersEntity.getModuleId())
				.build();
	}

}


