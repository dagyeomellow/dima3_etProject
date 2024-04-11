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
public class ProductionsDTO {
	
	
	private String producerId;
	private LocalDateTime prodDate;
	private double prodElectricity;
	private int prodDateMonth;
	
	
	// 

}


/*

CREATE TABLE PRODUCTIONS(
	PRODUCER_ID VARCHAR2(13) PRIMARY KEY REFERENCES PRODUCERS(PRODUCER_ID)
	, PROD_DATE DATE NOT NULL
	, PROD_ELECTRICITY NUMBER NOT NULL
	, PROD_DATE_MONTH CHAR(2)	
);
 
 */
