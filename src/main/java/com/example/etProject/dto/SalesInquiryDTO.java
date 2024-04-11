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
public class SalesInquiryDTO {
	
	private Long salesNum; // 참고하는 SALES_BOARD: SALES_NUM의 type Long
	private String memberId; // 참고하는 MEMBERS: MEMBER_ID의 type String
	private boolean isSeller;
	private String content;
	private LocalDateTime inquiryDate;
	private boolean isRead;
	

}


/*

 CREATE TABLE SALES_INQUIRY(
	SALES_NUM NUMBER PRIMARY KEY REFERENCES SALES_BOARD(SALES_NUM)
	, MEMBER_ID VARCHAR2(20) REFERENCES MEMBERS(MEMBER_ID)
	, IS_SELLER CHAR(1) NOT NULL
	, CONTENT VARCHAR2(4000) NOT NULL
	, INQUIRY_DATE DATE DEFAULT SYSDATE
	, IS_READ CHAR(1) 
);
 
 */
