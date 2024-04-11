package com.example.etProject.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="CONSUMPTIONS")
public class ConsumptionsEntity {

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumerId") // not null 계승
    private ConsumersEntity consumersEntity;

    @Column(name="CONS_DATE", nullable = false)
    private LocalDate consDate;
    
    @Column(name="CONS_ELECTRICITY", nullable = false)
    private double consElectricity;

    @Column(name="CONS_PROG1")
    private double consProg1;
    
    @Column(name="CONS_PROG2")
    private double consProg2;
    
    @Column(name="CONS_PROG3")
    private double consProg3;
    
    @Column(name="CONS_SMLY")
    private double consSmly;

    @Column(name="BILLS_ELEC_TOTAL", nullable = false)
    private double billsElecTotal;
    
    @Column(name="BILLS_BASIC", nullable = false)
    private double billsBasic;
    
    @Column(name="BILLS_ELEC_AMOUNT", nullable = false)
    private double billsElecAmount;
    
    @Column(name="BILLS_CLIMATE", nullable = false)
    private double billsClimate;
    
    @Column(name="BILLS_FUEL", nullable = false)
    private double billsFuel;
    
    
    //
    
    
    
    
}
