package com.example.etProject.service;

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

import com.example.etProject.repository.ProducersRepository;
import com.example.etProject.repository.ProductionsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisService {
    // 레포지토리 목록;
    private final ProducersRepository producersRepository;
    private final ProductionsRepository productionsRepository;
    private final RestTemplate restTemplate;
    // 멤버변수;
    @Value("${et.predict.server}")
    String url;

    //메소드;
    /**
     * memberId를 받아서 그 사용자의 1년치 실제 태양광 발전량을 조회하는 메소드
     * @param memberId
     * @return
     */
    @Transactional
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

    public Map<String, List> readPredProd(String memberId){
        // 경도,위도,설비용량 조회
        Double LocationX = producersRepository.findLocationXByMemberId(memberId);
        Double LocationY = producersRepository.findLocationYByMemberId(memberId);
        Double installedCapacity = producersRepository.findInstalledCapacityByMemberId(memberId);
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
}


/*
 * private final RestTemplate restTemplate;

    // application properties에서 
    @Value("${iris.predict.server}")
    String url;

    public Map<String,String> predictRest(IrisDTO irisDTO){
        // RestTemplate => config/AppConfig 만듬
        log.info(irisDTO.toString());
        Map<String,String> error = new HashMap<>(); //에러용
        Map<String,String> respData = null;//정상요
        
        // try{
            //헤더설정 //임포트는 스프링걸로
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            log.info(url);
            // ResponseEntity<Map<String, String>> response= restTemplate.postForEntity(url, irisDTO, Map<>.class);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, irisDTO, Map.class);

            log.info(response.toString());
            respData = response.getBody();

        // } catch(Exception e){
        //     error.put("errorCode", "444");
        //     error.put("errorContent", "몰라");
        //     return error;
        // }

        return respData;
    }
 */