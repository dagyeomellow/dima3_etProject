package com.example.etProject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "CONSUMPTIONS")
public class ConsumptionsEntity {
	
	@SequenceGenerator(
			name = "consumption_seq",
			sequenceName = "CONSUMPTION_SEQ",
			initialValue = 0,
			allocationSize = 1
			)
	
	
	@Id
	@GeneratedValue(generator = "consumption_seq")
	@Column(name = "CONSUMPTION_NUM")
	private Long consumptionNum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONSUMER_ID") // FK
	private ConsumersEntity consumersEntity;
	
	@Column(name = "CONS_DATE")
	private int consDate;
	
	@Column(name = "CONS_ELECTRICITY")
	private double consElectricity;
	
	@Column(name = "CONS_PROG1")
	private double consProg1;
	
	@Column(name = "CONS_PROG2")
	private double consProg2;
	
	@Column(name = "CONS_PROG3")
	private double consProg3;
	
	@Column(name = "CONS_SMLY")
	private double consSmly;
	
	@Column(name = "BILLS_ELEC_TOTAL", nullable = false)
	private double billsElecTotal;
	
	@Column(name = "BILLS_BASIC", nullable = false)
	private int billsBasic;
	
	@Column(name = "BILLS_ELEC_AMOUNT", nullable = false)
	private int billsElecAmount;
	
	@Column(name = "BILLS_CLIMATE")
	private int billsClimate;
	
	@Column(name = "BILLS_FUEL")
	private int billsFuel;
	
	
	

}

/*CREATE TABLE CONSUMPTIONS (
	CONSUMPTION_NUM NUMBER PRIMARY KEY NOT NULL,
	CONSUMER_ID VARCHAR2(13) FOREIGN KEY NOT NULL,
	CONS_DATE DATE NOT NULL,
	CONS_ELECTRICITY NUMBER NOT NULL,
	CONS_PROG1 NUMBER,
	CONS_PROG2 NUMBER,
	CONS_PROG3 NUMBER,
	CONS_SMLY NUMBER,
	BILLS_ELEC_TOTAL NUMBER NOT NULL,
	BILLS_BASIC NUMBER NOT NULL,
	BILLS_ELEC_AMOUNT NUMBER NOT NULL,
	BILLS_CLIMATE NUMBER,
	BILLS_FUEL NUMBER
);
CREATE SEQUENCE CONSUMPTION_SEQ;
 * */






