package com.example.etProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="TRADE")
public class TradeEntity {

    @SequenceGenerator(
        name = "trade_seq", sequenceName = "TRADE_SEQ",
        initialValue = 1, allocationSize = 1
    )

    @Id
    @GeneratedValue(generator = "trade_seq")
    @Column(name = "TRADE_NUM")
    private Long tradeNum;

    @OneToOne
    @JoinColumn(name="salesNum")
    private SalesBoardEntity salesBoardEntity; // REFERENCES SALES_BOARD(SALES_NUM)
    
    @Column(name = "CONS_MEMBER_ID", nullable = false)
    private String consMemberId;
    
    @Column(name = "CONS_NATIONAL_ID", nullable = false)
    private String consNationalId;
    
    @Column(name = "PROD_MEMBER_ID", nullable = false)
    private String prodMemberId;
    
    @Column(name = "PROD_NATIONAL_ID", nullable = false)
    private String prodNationalId;
    
    @Column(name = "AGREED_STARTDATE", nullable = false)
    private LocalDate agreedStartDate;
    
    @Column(name = "AGREED_ENDDATE", nullable = false)
    private LocalDate agreedEndDate;
    
    @Column(name = "AGREED_TOTAL_AMOUNT", nullable = false)
    private double agreedTotalAmount;
    
    @Column(name = "AGREED_TOTAL_PRICE", nullable = false)
    private int agreedTotalPrice; // 총가격 원화기준이므로 double 대신 int
    
    @Column(name = "TRADE_DATE")
    private LocalDateTime tradeDate= LocalDateTime.now(); // DEFAULT SYSDATE
    
    @Column(name = "TRADE_DETAIL")
    private String tradeDetail= null; // DEFAULT NULL, 항상 필드를 생성하는 것은 아니므로 "" 대신 null로 초기화.
    
    public enum statusCategory {AGREED, CANCELED, COMPLETED};
    
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private statusCategory status= statusCategory.AGREED; // DEFAULT 'AGREED'
    
    
    //
}

/*
 CREATE SEQUENCE TRADE_SEQ;
CREATE TABLE TRADE(
	TRADE_NUM NUMBER PRIMARY KEY
	, SALES_NUM NUMBER REFERENCES SALES_BOARD(SALES_NUM)
	, CONS_MEMBER_ID VARCHAR2(20) NOT NULL
	, CONS_NATIONAL_ID VARCHAR2(15) NOT NULL
	, PROD_MEMBER_ID VARCHAR2(20) NOT NULL
	, PROD_NATIONAL_ID VARCHAR2(15) NOT NULL
	, AGREED_STARTDATE DATE NOT NULL
	, AGREED_ENDDATE DATE NOT NULL
	, AGREED_TOTAL_AMOUNT NUMBER NOT NULL
	, AGREED_TOTAL_PRICE NUMBER NOT NULL
	, TRADE_DATE DATE DEFAULT SYSDATE
	, TRADE_DETAIL VARCHAR2(4000) DEFAULT NULL
	, STATUS VARCHAR2(20) DEFAULT 'AGREED' CHECK(STATUS IN ('AGREED','CANCELED','COMPLETED'))
)
 */

