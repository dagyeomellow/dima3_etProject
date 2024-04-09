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
	
	private String consumerId;
	private LocalDateTime consDate;
	private double consElectricity;
	private double consProg1;
	private double consProg2;
	private double consProg3;
	private double consSmly;
	private double billsElecTotal;
	private int billsBasic;
	private double billsElecAmount;
	private int billsClimate;
	private int billsFuel;
	
	public ConsumptionsDTO toDTO(ConsumptionsEntity consumptionsEntity) {
		return ConsumptionsDTO.builder()
				.consumerId(consumptionsEntity.getConsumerId())
				.consDate(consumptionsEntity.getConsDate())
				.consElectricity(consumptionsEntity.getConsElectricity())
				.consProg1(consumptionsEntity.getConsProg1())
				.consProg2(consumptionsEntity.getConsProg2())
				.consProg3(consumptionsEntity.getConsProg3())
				.consSmly(consumptionsEntity.getConsSmly())
				.billsElecTotal(consumptionsEntity.getBillsElecTotal())
				.billsBasic(consumptionsEntity.getBillsBasic())
				.billsElecAmount(consumptionsEntity.getBillsElecAmount())
				.billsClimate(consumptionsEntity.getBillsClimate())
				.billsFuel(consumptionsEntity.getBillsFuel())
				.build();
	}
	
}
