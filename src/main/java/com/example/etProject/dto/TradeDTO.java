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
public class TradeDTO {
	
	// private 
}


/*
 * 컬럼명	컬럼 ID	타입 및 길이	Not Null	PK	FK	IDX	기본값	설명
								
TRADE_NUM		NUMBER	1	1				TRADE_SEQ에 의해 자동 생성된 TRADE 엔티티의 주키
SALES_NUM		NUMBER	1		1			거래의 대상이 된 게시글의 번호
CONS_MEMBER_ID		VARCHAR2(20)	1		1			구매자의 MEMBER_ID(MEMBER(MEMBER_ID) 참조
CONS_NATIONAL_ID		VARCHAR2(15)	1		1			구매자의 주민번호, MEMBER(NATIONAL_ID)참조
PROD_MEMBER_ID		VARCHAR2(20)	1		1			판매자의 MEMBER_ID(MEMBER(MEMBER_ID) 참조
PROD_NATIONAL_ID		VARCHAR2(15)	1		1			판매자의 주민번호, MEMBER(NATIONAL_ID)참조
AGREED_STARTDATE		DATE	1					합의된 전력공급 시작 년월
AGREED_ENDDATE		DATE	1					합의된 전력공급 종료 년월
AGREED_TOTAL_AMOUNT		NUMBER	1					합의된 총전력공급량
AGREED_TOTAL_PRICE		NUMBER	1					 합의된 총금액
TRADE_DATE		DATE	1				SYSDATE	합의 일자(년월일)
TRADE_DETAIL		VARCHAR2(4000)					NULL	구체적인 거래 내용(조건이나 위반시 문제 등)
STATUS		VARCHAR2(20)					AGREED	거래상태: AGREED: 계약완료, CANCELED: 거래취소, COMPLETED: 거래완료(취소불가)
 * 
 * 
 */