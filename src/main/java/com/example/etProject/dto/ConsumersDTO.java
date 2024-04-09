package com.example.etProject.dto;

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
	
	// 
	

}

