package com.example.etProject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.etProject.dto.ConsumersDTO;
import com.example.etProject.dto.MembersDTO;
import com.example.etProject.dto.ProducersDTO;
import com.example.etProject.entity.ConsumersEntity;
import com.example.etProject.entity.MembersEntity;
import com.example.etProject.entity.ProducersEntity;
import com.example.etProject.repository.ConsumersRepository;
import com.example.etProject.repository.MembersRepository;
import com.example.etProject.repository.ProducersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final MembersRepository membersRepository;
	private final ConsumersRepository consumersRepository;
	private final ProducersRepository producersRepository;

	/**
	 * 회원가입 기능
	 * @param membersDTO
	 * @param customerType
	 * @param contractType
	 * @param installedCapacity
	 */
	public void join(MembersDTO membersDTO, String customerType, String contractType, int installedCapacity) {
		if(installedCapacity==-1){
			membersDTO.setMemberRole("ROLE_CONSUMER");
		} else{
			membersDTO.setMemberRole("ROLE_PROSUMER");
		}
		membersDTO.setAgree(true);
		// Members에 저장
		MembersEntity membersEntity = saveMember(membersDTO);
	
		// Consumers에 저장
		saveConsumer(membersEntity, customerType, contractType);
	
		// Producers에 저장
		saveProducer(membersEntity, installedCapacity);

	}
	

	/**
	 * 멤버 엔티티에 저장하는 메소드
	 * @param membersDTO
	 * @return
	 */
	private MembersEntity saveMember(MembersDTO membersDTO) {
		return membersRepository.save(MembersEntity.toEntity(membersDTO));
	}

	/**
	 * 소비자 엔티티에 저장하는 메소드
	 * @param membersEntity
	 * @param customerType
	 * @param contractType
	 */
	private void saveConsumer(MembersEntity membersEntity, String customerType, String contractType) {
		ConsumersDTO consumerDTO = new ConsumersDTO();
		consumerDTO.setConsumerId(generateKey(false));
		consumerDTO.setMemberId(membersEntity.getMemberId());
		consumerDTO.setKepcoCustNum(generateKepco());
		consumerDTO.setContractType(contractType);
		consumerDTO.setCustomerType(customerType);
		consumersRepository.save(ConsumersEntity.toEntity(consumerDTO, membersEntity));
	}

	/**
	 * 생산자 엔티티에 저장하는 메소드
	 * @param membersEntity
	 * @param installedCapacity
	 */
	private void saveProducer(MembersEntity membersEntity, int installedCapacity) {
		ProducersDTO producerDTO = new ProducersDTO();
		producerDTO.setProducerId(generateKey(true));
		producerDTO.setMemberId(membersEntity.getMemberId());
		producerDTO.setInstalledCapacity(installedCapacity);
		if(installedCapacity== -1){
			producerDTO.setIsProduce(false);
		} else{
			producerDTO.setIsProduce(true);
		}

		List<Double> location = randomLocation();
		producerDTO.setLocationX(location.get(0)); // 경도
		producerDTO.setLocationY(location.get(1)); // 위도
		
		
		producersRepository.save(ProducersEntity.toEntity(producerDTO, membersEntity));
	}

	public Boolean isProsumer(String memberId){
		String memberRole= membersRepository.findMemberRoleByMemberId(memberId);


		if(memberRole.equals("ROLE_PROSUMER")){
			return true;
		}
		return false;
	}

	/**
	 * 소비자 ID, 생산자 ID 인조키 만드는 메소드
	 * @param isProducer
	 * @return
	 */
	public String generateKey(Boolean isProducer){
		Random random = new Random();
		StringBuilder sb;
		if (isProducer){
			sb = new StringBuilder("SPP");
		}
		else{
			sb = new StringBuilder("SPC");
		}
		for (int i=0; i<9 ; ++i){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * 주민등록번호를 받아서, 한전으로부터 한전고객번호를 가져오는 부분이지만,
	 * 현재는 불가능한 상황이므로 임의의 난수로 대체.
	 * @return
	 */
	public String generateKepco() {
        Random random = new Random();
        int part1 = random.nextInt(100);  // 0부터 99까지의 난수 생성
        int part2 = random.nextInt(10000);  // 0부터 9999까지의 난수 생성
        int part3 = random.nextInt(10000);  // 0부터 9999까지의 난수 생성

        return String.format("%02d-%04d-%04d", part1, part2, part3);
    }

	/**
	 * 임시 메소드: 로케이션 랜덤으로 설정
	 * 변경할 방향: Geocoding API이용해서 주소값 파라미터로 받아서 위경도 값 리턴하도록.
	 * 만약 자바스크립트에서 해결해서 올 수 있다면, 해올것
	 * @return
	 */
	public List<Double> randomLocation(){
		List<Double> loc = new ArrayList<Double>();
		// 위도 범위: 33 ~ 43
        // 경도 범위: 124 ~ 132
        double minLatitude = 33.0;
        double maxLatitude = 43.0;
        double minLongitude = 124.0;
        double maxLongitude = 132.0;

        Random random = new Random();

        double randomLatitude = minLatitude + (maxLatitude - minLatitude) * random.nextDouble();
        double randomLongitude = minLongitude + (maxLongitude - minLongitude) * random.nextDouble();

		loc.add(randomLongitude); loc.add(randomLatitude);

		return loc;
    }
}