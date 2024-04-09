package com.example.etProject.entity;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @ManyToOne
    @JoinColumn(name="CONSUMER_ID")
    private ConsumersEntity consumer;

    @Column(name="CONS_DATE")
    private LocalDate consDate;
    
    @Column(name="CONS_ELECTRICITY")
    private double consElectricity;

    @Column(name="CONS_PROG1")
    private double consProg1;
    @Column(name="CONS_PROG2")
    private double consProg2;
    @Column(name="CONS_PROG3")
    private double consProg3;
    
    @Column(name="CONS_SMLY")
    private double consSmly;

    @Column(name="BILLS_ELEC_TOTAL")
    private double billsElecTotal;
    @Column(name="BILLS_BASIC")
    private double billsBasic;
    @Column(name="BILLS_ELEC_AMOUNT")
    private double billsElecAmount;
    @Column(name="BILLS_CLIMATE")
    private double billsClimate;
    @Column(name="BILLS_FUEL")
    private double billsFuel;
}
