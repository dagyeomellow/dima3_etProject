package com.example.etProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name="TRADE")
public class TradeEntity {

    @SequenceGenerator(
        name = "trade_seq", sequenceName = "trade_seg",
        initialValue = 0, allocationSize = 1
    )

    @Id
    @GeneratedValue(generator = "trade_seq")
    private Long tradeNum;

    @ManyToOne
    @JoinColumn(name="SALES_NUM")
    private Long salesNum;

    private String consMemberId;
    private String consNationalId;
    private String prodMemberId;
    private String prodNationalId;
    private LocalDate agreedStartDate;
    private LocalDate agreedEndDate;
    private double agreedTotalAmount;
    private double agreedTotalPrice;
    private LocalDateTime tradeDate;
    private String tradeDetail;
    private String status;
}
