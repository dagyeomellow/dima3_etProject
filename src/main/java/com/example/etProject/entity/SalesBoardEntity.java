package com.example.etProject.entity;

import java.time.LocalDateTime;

import com.example.etProject.dto.SalesBoardDTO;

import jakarta.persistence.Column;
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
	
	@Column(name = "MEMBER_ID")
	private String memberId;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "CONTENTS")
	private String contents;
	
	@Column(name = "SALES_STARTMONTH")
	private LocalDateTime salesStartMonth;
	
	@Column(name = "SALES_ENDMONTH")
	private LocalDateTime salesEndMonth;
	
	@Column(name = "TOTAL_MONTH")
	private Number totalMonth;
	
	@Column(name = "TOTAL_AMOUNT")
	private float totalAmount;
	
	@Column(name = "TOTAL_PRICE")
	private float totalPrice;
	
	@Column(name = "AVG_AMOUNT")
	private float avgAmount;
	
	@Column(name = "STATUS")
	private boolean status= false; // DEFAULT 0
	
	@Column(name = "WRITE_DATE")
	private LocalDateTime writeDate= LocalDateTime.now(); // DEFAULT SYSDATE
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private MembersEntity membersEntity;
	
	public static SalesBoardEntity toEntity(SalesBoardDTO salesBoardDTO, MembersEntity memberEntity) {
		return SalesBoardEntity.builder()
				.salesNum(salesBoardDTO.getSalesNum())
				.memberId(salesBoardDTO.getMemberId())
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
				.membersEntity(memberEntity)
				.build();
	} 

}
