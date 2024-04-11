package com.example.etProject.entity;

import com.example.etProject.dto.ProducersDTO;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Entity
@Table(name = "PRODUCERS")
public class ProducersEntity {
	
	public class KeyGenerator {
		public static String generateProducerId() {
			Random random = new Random();
			int randomNumber = random.nextInt(900_000_000) + 100_000_000; // 100_000_000 ~ 999_999_999
			return "SPP" + randomNumber;
			}
		}
	
	
	@Id
	@Column(name = "PRODUCER_ID", nullable = false)
	// 전력생산회원 인조키, 예: SPP123456789(SP: 사이트약어, P: 생산, 숫자9자리: 난수발생), 중복불가
	private String producerId= KeyGenerator.generateProducerId();
	
	@OneToOne
	@JoinColumn(name = "MEMBER_ID")
	private MembersEntity membersEntity;
	
	@Column(name = "MEMBER_ADDR", nullable = false)
	private String memberAddr;
	
	@Column(name = "LOCATION_X", nullable = false)
	private double locationX;
	
	@Column(name = "LOCATION_Y", nullable = false)
	private double locationY;
	
	@Column(name = "INSTALLED_CAPACITY", nullable = false)
	private int installedCapacity; // 소수점 단위도 있다면 float/double 로 수정
	
	@Column(name = "MODULE_ID", nullable = false)
	private String moduleId; // @확인
	
	
	public static ProducersEntity toEntity(ProducersDTO producersDTO) {
		return ProducersEntity.builder()
				.producerId(producersDTO.getProducerId())
				.membersEntity(MembersEntity.builder().memberId(producersDTO.getMemberId()).build())
				.memberAddr(producersDTO.getMemberAddr())
				.locationX(producersDTO.getLocationX())
				.locationY(producersDTO.getLocationY())
				.installedCapacity(producersDTO.getInstalledCapacity())
				.moduleId(producersDTO.getModuleId())
				.build();
	}
	

	@OneToMany(mappedBy = "producersEntity",
	cascade = CascadeType.REMOVE,
	orphanRemoval = true,
	fetch = FetchType.LAZY)
	private List<ProductionsEntity> productionsEntity = new ArrayList<>();

}

