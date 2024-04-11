package com.example.etProject.dto;

import java.time.LocalDateTime;

import com.example.etProject.entity.ConsumptionsEntity;

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
public class ConsumptionsDTO {
	
	private Long consumptionNum;
	private String consumerId;
	private LocalDateTime consDate;
	private double consElectricity;
	private double consProg1;
	private double consProg2;
	private double consProg3;
	private double consSmly;
	private double billsElecTotal;
	private int billsBasic;
	private int billsElecAmount; // 원화결제 기준이므로 소수점 없음
	private int billsClimate;
	private int billsFuel;
	
	/*
	public static ConsumptionsDTO toDTO(ConsumptionsEntity consumptionsEntity) {
		return ConsumptionsDTO.builder()
				.consumptionNum(consumptionsEntity)
	} */
	

}

