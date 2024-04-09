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
    @JoinColumn(name="PRODUCER_ID")
    private ProducersEntity producer;

    @Column(name="PROD_DATE")
    private LocalDate prodDate;
    @Column(name="PROD_ELECTRICITY")
    private double prodElectricity;
    @Column(name="PROD_DATE_MONTH")
    private int prodDateMonth;

}
