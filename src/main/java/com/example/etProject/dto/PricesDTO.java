package com.example.etProject.dto;

import java.time.LocalDateTime;

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
public class PricesDTO {
	
	private String priceCode;
	private String priceName;
	private int basicPrice;
	private double amountPrice;
	private LocalDateTime applyDate;
	
	
	/* public PricesDTO toDTO(PricesEntity pricesEntity) {
		return PricesDTO.builder()
	} */

}
