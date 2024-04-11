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
public class TradeDTO {
	
	private Long tradeNum;
	private int salesNum;
	private String consMemberId;
	private String consNationalId;
	private String prodMemberId;
	private String prodNationalId;
	private LocalDateTime agreedStartdate;
	private LocalDateTime agreedEndDate;
	private double agreedTotalAmount;
	private int agreedTotalPrice; // 원화이므로 소수점단위 사용하지 않음
	private LocalDateTime tradeDate; // 엔티티에서 초기화 작업을 합니다. 확인
	private String tradeDetail; // " + DEFAULT NULL
	private String status; // " + 범주형
	
	// 
	
	
}

