package com.example.etProject.entity;

import java.time.LocalDateTime;

import com.example.etProject.dto.SalesBoardDTO;

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

//sales board, sales inquiry, consumers 관계설정


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "SALES_BOARD")
public class SalesBoardEntity {
	
	@SequenceGenerator(
			name = "sales_seq",
			sequenceName = "SALES_SEQ",
			initialValue = 1,
			allocationSize = 1
			)
	
	@Id
	@Column(name = "SALES_NUM")
	@GeneratedValue(generator = "sales_seq")
	private Long salesNum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId")
	private MembersEntity membersEntity; // REFERENCES MEMBERS(MEMBER_ID)
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "CONTENTS")
	private String contents;
	
	@Column(name = "SALES_STARTMONTH", nullable = false)
	private LocalDateTime salesStartMonth;
	
	@Column(name = "SALES_ENDMONTH", nullable = false)
	private LocalDateTime salesEndMonth;
	
	@Column(name = "TOTAL_MONTH")
	private Number totalMonth;
	
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private float totalAmount;
	
	@Column(name = "TOTAL_PRICE", nullable = false)
	private float totalPrice;
	
	@Column(name = "AVG_AMOUNT")
	private float avgAmount;
	
	@Column(name = "STATUS")
	private boolean status= false; // DEFAULT 0
	
	@Column(name = "WRITE_DATE")
	private LocalDateTime writeDate= LocalDateTime.now(); // DEFAULT SYSDATE
	
	
	public static SalesBoardEntity toEntity(SalesBoardDTO salesBoardDTO) {
		return SalesBoardEntity.builder()
				.salesNum(salesBoardDTO.getSalesNum())
				.membersEntity(MembersEntity.builder().memberId(salesBoardDTO.getMemberId()).build())
				.title(salesBoardDTO.getTitle())
				.contents(salesBoardDTO.getContents())
				.salesStartMonth(salesBoardDTO.getSalesStartMonth())
				.salesEndMonth(salesBoardDTO.getSalesEndMonth())
				.totalMonth(salesBoardDTO.getTotalMonth())
				.totalAmount(salesBoardDTO.getTotalAmount())
				.totalPrice(salesBoardDTO.getTotalPrice())
				.avgAmount(salesBoardDTO.getAvgAmount())
				.status(salesBoardDTO.isStatus())
				.writeDate(salesBoardDTO.getWriteDate())
				.build();
	} 

}
