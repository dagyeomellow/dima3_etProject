package com.example.etProject.entity;

import com.example.etProject.dto.ProducersDTO;

import jakarta.persistence.Column;
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
@Table(name = "PRODUCERS")
public class ProducersEntity {
	
	@Id
	@Column(name = "PRODUCER_ID")
	private String producerId;
	
	@Column(name = "MEMBER_ID")
	private String memberId;
	
	@Column(name = "MEMBER_ADDR")
	private String memberAddr;
	
	@Column(name = "LOCATION_X")
	private double locationX;
	
	@Column(name = "LOCATION_Y")
	private double locationY;
	
	@Column(name = "INSTALLED_CAPACITY")
	private Number installedCapacity; // 소수점 단위도 있다면 float/double 로 수정
	
	@Column(name = "MODULE_ID")
	private String moduleId;
	
	
	public static ProducersEntity toEntity(ProducersDTO producersDTO) {
		return ProducersEntity.builder()
				.producerId(producersDTO.getProducerId())
				.memberId(producersDTO.getMemberId())
				.memberAddr(producersDTO.getMemberAddr())
				.locationX(producersDTO.getLocationX())
				.locationY(producersDTO.getLocationY())
				.installedCapacity(producersDTO.getInstalledCapacity())
				.moduleId(producersDTO.getModuleId())
				.build();
	}
	
	@OneToOne
	@JoinColumn(name = "MEMBER_ID")
	private MembersEntity membersEntity;

}

