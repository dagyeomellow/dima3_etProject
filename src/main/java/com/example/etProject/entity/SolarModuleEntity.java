package com.example.etProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "SOLAR_MODULE")
public class SolarModuleEntity {
	
	// 두 컬럼은 참조하는 컬럼이 갖는 not null 조건을 계승한다.
	
	@Id
	@OneToOne
	@JoinColumn(name = "poducerId") // 참조하는 엔티티의 컬럼명
	private ProducersEntity producersEntity1; // REFERENCES PRODUCERS(PRODUCER_ID: 인조키)
	
	@OneToOne
	@JoinColumn(name = "moduleId")
	private ProducersEntity producersEntity2; // REFERENCES PRODUCERS(MODULE_ID)
}


/*

//db상 SOLAR_MODULE:
* PRODUCER_ID -> PRODUCER(PRODUCER_ID) 참조하는 SOLAR_MODULE 엔티티의 주키. NN,PK,FK
* MODULE_NAME -> 태양광 모듈의 모델명. NN, FK

//ddl상 서술:
CREATE TABLE SOLAR_MODULE(
	MODULE_ID VARCHAR2(30) PRIMARY KEY NOT NULL REFERENCES PRODUCERS(PRODUCER_ID)
	, MODULE_NAME VARCHAR2(20) NOT NULL
	, MODULE_SOH NUMBER
	
//db상 PRODUCERS의 컬럼설계와 db상 SOLAR_MODULE의 컬럼설계, ddl 서술을 고려해서,
일단 PRODUCERS에 종속적인 SOLAR_MODULE로 코딩 => 
 
 solar module은 PRODUCERS의 PRODUCER_ID를 참조하는 FK 주키 producerId(fk는 컬럼명이 같아야 한다),
				PRODUCERS의 MODULE_ID를 참조하는 FK moduleId로 코딩


*/