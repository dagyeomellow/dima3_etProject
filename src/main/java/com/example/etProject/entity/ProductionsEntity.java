package com.example.etProject.entity;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

@Entity
@Table(name="PRODUCTIONS")
public class ProductionsEntity {

    @SequenceGenerator(
        name="PRODUCTION_SEQ",sequenceName = "PRODUCTION_SEQ",initialValue = 0,
        allocationSize = 1
    )

    @Id
    @GeneratedValue(generator = "PRODUCTION_SEQ")
    @Column(name="PRODUCTION_NUM")
    private Long productionNum;

    @ManyToOne
    @JoinColumn(name="PRODUCER_ID")
    private ProducersEntity producersEntity;

    @Column(name="PROD_DATE")
    private LocalDate prodDate;
    @Column(name="PROD_ELECTRICITY")
    private Double prodElectricity;
    @Column(name="PROD_DATE_MONTH")
    private int prodDateMonth;
}
