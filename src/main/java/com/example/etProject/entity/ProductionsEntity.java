package com.example.etProject.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="PRODUCTIONS")
public class ProductionsEntity {

	
	@ManyToOne
	@Column(name = "producerId")
	private ProducersEntity producersEntity;

    @Column(name="PROD_DATE", nullable = false)
    private LocalDate prodDate;
    
    @Column(name="PROD_ELECTRICITY", nullable = false)
    private double prodElectricity;
    
    @Column(name="PROD_DATE_MONTH")
    private int prodDateMonth;

}


/*

CREATE TABLE PRODUCTIONS(
	PRODUCER_ID VARCHAR2(13) PRIMARY KEY REFERENCES PRODUCERS(PRODUCER_ID)
	, PROD_DATE DATE NOT NULL
	, PROD_ELECTRICITY NUMBER NOT NULL
	, PROD_DATE_MONTH CHAR(2)	
);
 
 */