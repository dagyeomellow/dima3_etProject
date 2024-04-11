package com.example.etProject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="SALES_INQUIRY")
public class SalesInquiryEntity {

    @Id
    @OneToOne
    @JoinColumn(name = "salesNum")
    private SalesBoardEntity salesBoardEntity; // REFERENCES SALES_BOARD(SALES_NUM: value= seq)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private MembersEntity membersEntity; // REFERENCES MEMBERS(MEMBER_ID)
    
    @Column(name="IS_SELLER", nullable = false)
    private boolean isSeller;
    
    @Column(name="CONTENT", nullable = false)
    private String content;
    
    @Column(name="INQUIRY_DATE")
    private LocalDateTime inquiryDate= LocalDateTime.now(); // DEFAULT SYSDATE
    
    @Column(name="IS_READ")
    private boolean isRead;
    
    
    // public 

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


