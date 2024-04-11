package com.example.etProject.dto;

import java.time.LocalDate;

import com.example.etProject.entity.ConsumersEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConsumptionsDTO {
	
	private ConsumersEntity consumer;
	private LocalDate consDate;
	private double consElectricity;
	private double consProg1;
	private double consProg2;
	private double consProg3;
	private double consSmly;
	private double billsElecTotal;
	private double billsBasic;
	private double billsElecAmount;
	private double billsClimate;
	private double billsFuel;
	
	//
	

}
