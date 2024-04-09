package com.example.etProject.entity;

import java.time.LocalDateTime;

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
@Table(name="SALES_INQUIRY")
public class SalesInquiryEntity {

    @SequenceGenerator(
        name="sales_seq", sequenceName="sales_seq"
        , initialValue=0, allocationSize=1
    )

    @Id
    @GeneratedValue(generator ="sales_seq")
    private Long salesNum;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private String memberId;
    @Column(name="IS_SELLER")
    private Boolean isSeller;
    @Column(name="CONTENT")
    private String content;
    @Column(name="INQUIRY_DATE")
    private LocalDateTime inquiryDate;
    @Column(name="IS_READ")
    private Boolean isRead;

}
