package com.example.etProject.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.etProject.entity.PricePredictionEntity;
import com.example.etProject.entity.PricesEntity;
import com.example.etProject.repository.ConsumersRepository;
import com.example.etProject.repository.ConsumptionsRepository;
import com.example.etProject.repository.MembersRepository;
import com.example.etProject.repository.PricePredictionRepository;
import com.example.etProject.repository.PricesRepository;
import com.example.etProject.repository.ProducersRepository;
import com.example.etProject.repository.ProductionsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisService {
    // 레포지토리 목록;
    private final MembersRepository membersRepository;
    private final ConsumersRepository consumersRepository;
    private final ConsumptionsRepository consumptionsRepository;
    private final ProducersRepository producersRepository;
    private final ProductionsRepository productionsRepository;
    private final RestTemplate restTemplate;
    private final PricesRepository pricesRepository;
    private final PricePredictionRepository pricePredictionRepository;
    // 멤버변수;
    @Value("${et.predict.server}")
    String url;

    //메소드;

    /**
     * memberId를 받아서 그 사용자의 1년치 실제 전력 소비량을 조회하는 메소드
     * 키: ConsumptionMonths, ActualConsumption, ConsumptionBills
     * @param memberId
     * @return
     */
    public Map<String,List> readActualCons(String memberId){
        String consumerId = consumersRepository.findConsumerIdByMemberId(memberId);
        List<Object[]> consumptions = consumptionsRepository.findAllConsumptionsByConsumerId(consumerId);

        Map<String,List> consResult= new HashMap<>();
        List consDateList = new ArrayList<>();
        List consElectricityList = new ArrayList<>();
        // List consBillsElecList = new ArrayList<>();
        int cnt=0;
        for (Object[] ce: consumptions){
            if (cnt>=12) break;
            consDateList.add(ce[0]);
            consElectricityList.add(ce[1]);
            // consBillsElecList.add(ce[2]);
        }
        consResult.put("ActualConsumption", consElectricityList);
        consResult.put("ActualConsumptionMonths", consDateList);
        // consResult.put("ActualConsumptionBills", consBillsElecList);

        return consResult;
    }

    /**
     * memberId를 받아서 그 사용자의 1년치 실제 태양광 발전량을 조회하는 메소드
     * 키: ActualProduction
     * @param memberId
     * @return
     */
    public Map<String, List> readActualProd(
        String memberId
    ){
        log.info(memberId);
        String producerId = producersRepository.findProducerIdByMemberId(memberId);
        List<Double> prodList= productionsRepository.findProdElectricityByProducerId(producerId);
        Map<String, List> prodResult = new HashMap<>();
        prodResult.put("ActualProduction", prodList);

        return prodResult;
    }

    /**
     * memberId를 받아서 그 사용자의 과거 12개월치 추정 발전량(오름차순 2023.04 - 2024.04) 및
     * 미래 4개월치 예측 소비량(오름차순 2024.04 - 2024.07)를 조회하는 메소드
     * 키 이름: PredictProduction, PredictConsumption
     * @param memberId
     * @return
     */
    public Map<String, List> readPrediction(String memberId, int capacity){
        // 경도,위도,설비용량 조회
        Double LocationX = producersRepository.findLocationXByMemberId(memberId);
        Double LocationY = producersRepository.findLocationYByMemberId(memberId);
        int installedCapacity;
        installedCapacity = producersRepository.findInstalledCapacityByMemberId(memberId);
        if (installedCapacity == -1){
            installedCapacity= capacity;
        }
        
        Map<String, Object> producerInfo = new HashMap<>();
        producerInfo.put("locationX", LocationX);
        producerInfo.put("locationY", LocationY);
        producerInfo.put("installedCapacity", installedCapacity);
        producerInfo.put("memberId",memberId);

        Map<String,List> error = new HashMap<>(); //에러용
        Map<String,List> respData = null;//정상용
        
        // try{
            //헤더설정 //임포트는 스프링걸로
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            log.info(url);
            ResponseEntity<Map> response = restTemplate.postForEntity(url,producerInfo, Map.class);

            log.info(response.toString());
            respData = response.getBody();

        // } catch(Exception e){
        //     error.put("errorCode", "444");
        //     error.put("errorContent", "몰라");
        //     return error;
        // }

        return respData;
    }

    /**
     * 손익분기점 계산 기능. 손익분기점에 도달하는 누적개월수 및 이후 1년간의 수익 계산해줌.
     * 개월수 리스트와 누적 순이익 리스트 반환
     * 키 이름: requiredMonths, netRevenues
     * @param memberId
     * @param capacity
     * @param cost
     * @return
     */
    public Map<String, List> consumerAnanlysis(String memberId, int capacity, int cost){
        // 결과 담을 객체
        Map<String,List> respData = new HashMap<>();

        // 생산량 및 소비량 예측 수행
        Map<String,List> prediction =readPrediction(memberId, capacity);
        // 소비량은 바로 담아주기
        respData.put("PredictConsumption",prediction.get("PredictConsumption"));
        respData.put("PredictConsumptionMonths", prediction.get("PredictConsumptionMonths"));

        // 과거 12개월치의 추정생산량 기반 월평균 생산량 도출
        List<Double> predictProduction = prediction.get("PredictProduction");
        Map<String,List> calc = calcBreakeven(memberId, capacity, cost, predictProduction);
        respData.put("RequiredMonths",calc.get("RequiredMonths"));
        respData.put("NetRevenues",calc.get("NetRevenues"));

        return respData;
    }
    public Map<String,List> calcBreakeven(String memberId, int capacity, int cost, List<Double> prodPrediction ){
        double sum = 0.0;
        for (Object value : prodPrediction) {
            sum += (Double) value;
        }
        double avgProd = sum / prodPrediction.size();

        // 현재 전기가격 리스트 가져오기 
        LocalDate date = LocalDate.now(); // 현재날짜를 Long 형태의 202404 처럼 바꿔주기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String formattedDate = date.format(formatter);
        Long yearMonth = Long.parseLong(formattedDate);
        PricesEntity pricesEntity= pricesRepository.findHousePricesByType(yearMonth); // 현재의 가격정보 가져오기
        // log.info("프라이스엔티티는 가져왔니?!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // 가중평균을 통한 월평균 상계전기가격 계산
        String consumerId = consumersRepository.findConsumerIdByMemberId(memberId);
        // log.info("consumerid 가져왔어!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+consumerId);
        String contractType = consumersRepository.findContractTypeByConsumerId(consumerId);
        double price=0.0;
        if (contractType.equals("저압")){
            price=(pricesEntity.getHleBetween() * 5 + pricesEntity.getHlsBetween())/6; // 12개월중 10개월은 기타 요금이, 2개월은 하계 요금이 적용되므로
        }
        else if (contractType.equals("고압")){
            price=(pricesEntity.getHhsBetween()*5 + pricesEntity.getHhsBetween())/6; // 누진 2구간을 기준으로 잡음
        }

        // 총비용과 총수입 비교하여 총수입이 더 커지는 기간 계산(월 단위)
        // 걸리는 개월수 [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14...]
        // 순이익 [-100000000,-9999999999,-999999998 ....]
        int month = 0;
        double monthlyRevenue= price * avgProd;
        double netRevenue=0.0 - cost;
        ArrayList<Integer> monthList = new ArrayList<>();
        ArrayList<Double> netRevenueList = new ArrayList<>();
        while (true){
            monthList.add(month); month+=1;
            netRevenueList.add(netRevenue); netRevenue+=monthlyRevenue;
            if(netRevenue>=0) break;
        }
        for (int i = 0; i<12; ++i){
            monthList.add(month); month+=1;
            netRevenueList.add(netRevenue); netRevenue+=monthlyRevenue;
        }

        // Map 객체에 담아서 반환
        Map<String,List> respData= new HashMap<>();
        respData.put("RequiredMonths", monthList);
        respData.put("NetRevenues", netRevenueList);

        return respData;
    }

    /**
     * 현재 개월 부터 +4개월의 한전의 예상 전기요금을 조회하는 메소드
     * 키 PricePrediction (이중리스트): [[0번째달누진1구간,0번째달누진2구간,0번째달누진3구간],...,[3번째달누진1구간,3번째달누진2구간,3번째달누진3구간]]
     * @param memberId
     * @return
     */
    public Map<String,List> getPricePrediction(String memberId){
        String consumerId = consumersRepository.findConsumerIdByMemberId(memberId);
        String contractType = consumersRepository.findContractTypeByConsumerId(consumerId); //"고압" or "저압"
        
        List<PricePredictionEntity> ppEntityList= pricePredictionRepository.findAll();
        List<String> predictMonthList = new ArrayList<>(); // 예측시점 문자열 담을 리스트 ("4월_예측","5월_예측")
        List<Double> prog1PriceList = new ArrayList<>(); // 누진 1구간 요금 담을 리스트
        List<Double> prog2PriceList = new ArrayList<>(); // 누진 2구간 요금 담을 리스트
        List<Double> prog3PriceList = new ArrayList<>(); // 누진 3구간 요금 담을 리스트

        
        for (PricePredictionEntity ppe: ppEntityList){
            predictMonthList.add(ppe.getPredictMonth());
            if(contractType.equals("저압")){
                if (isSummer(ppe.getPredictMonth())){ // 저압 그리고 7월이나 8월인 경우
                    prog1PriceList.add(ppe.getHlsUnder300());
                    prog2PriceList.add(ppe.getHlsBetween());
                    prog3PriceList.add(ppe.getHlsOver450());
                } else{
                    prog1PriceList.add(ppe.getHleUnder200());
                    prog2PriceList.add(ppe.getHleBetween());
                    prog3PriceList.add(ppe.getHleOver400());
                }
            } else if(contractType.equals("고압")){
                if(isSummer(ppe.getPredictMonth())){
                    prog1PriceList.add(ppe.getHhsUnder300());
                    prog2PriceList.add(ppe.getHhsBetween());
                    prog3PriceList.add(ppe.getHhsOver450());
                } else{
                    prog1PriceList.add(ppe.getHheUnder200());
                    prog2PriceList.add(ppe.getHheBetween());
                    prog3PriceList.add(ppe.getHheOver400());
                }
            }
        }//for문
        Map<String,List> respData = new HashMap<>();

        respData.put("PredictPriceMonths",predictMonthList);
        respData.put("PredictPriceProg1", prog1PriceList);
        respData.put("PredictPriceProg2", prog2PriceList);
        respData.put("PredictPriceProg3", prog3PriceList);
        
        return respData;
    }
    public Boolean isSummer(String predictMonth){
        if ((predictMonth.endsWith("7")||(predictMonth.endsWith("8")))){
            return true;
        }
        return false;
    }
}
