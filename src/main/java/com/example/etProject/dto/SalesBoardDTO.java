package com.example.etProject.dto;

import java.time.LocalDateTime;

import com.example.etProject.entity.SalesBoardEntity;

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
public class SalesBoardDTO {
	private Long salesNum;
	private String memberId;
	private String title;
	private String contents;
	private LocalDateTime salesStartMonth;
	private LocalDateTime salesEndMonth;
	private Number totalMonth;
	private float totalAmount; //*용량절약 확인
	private float totalPrice;
	private float avgAmount;
	private boolean status; 
	private LocalDateTime writeDate;
	
	public static SalesBoardDTO toDTO(SalesBoardEntity salesBoardEntity) {
		return SalesBoardDTO.builder()
				.salesNum(salesBoardEntity.getSalesNum())
				.memberId(salesBoardEntity.getMemberId())
				.title(salesBoardEntity.getTitle())
				.contents(salesBoardEntity.getContents())
				.salesStartMonth(salesBoardEntity.getSalesStartMonth())
				.salesEndMonth(salesBoardEntity.getSalesEndMonth())
				.totalMonth(salesBoardEntity.getTotalMonth())
				.totalAmount(salesBoardEntity.getTotalAmount())
				.totalPrice(salesBoardEntity.getTotalPrice())
				.avgAmount(salesBoardEntity.getAvgAmount())
				.status(salesBoardEntity.isStatus())
				.writeDate(salesBoardEntity.getWriteDate())
				.build();
			
	}
	
}

