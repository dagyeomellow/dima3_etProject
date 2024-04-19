package com.example.etProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.etProject.dto.MembersDTO;
import com.example.etProject.service.AnalysisService;
import com.example.etProject.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class myTestController {

    private final AnalysisService analysisService;
    private final UserService userService;

    @GetMapping("/report")
    public String test(){
        return "report";
    }

    @GetMapping("/join")
    public String join(
        // @ModelAttribute MembersDTO membersDTO,
        // @RequestParam(name="consumerType", defaultValue = "주택용") String consumerType,
        // @RequestParam(name="contractType", defaultValue = "저압") String contractType,
        // @RequestParam(name="installedCapacity", required = false, defaultValue = -1) int installedCapacity
    ){
// 유저서비스
    //회원가입 확인용(컨슈머)
    log.info("회원가입진행!");
    int installedCapacity = -1;
    MembersDTO membersTestConsumerDTO= new MembersDTO(
        "컨슈머테스트2", "비번1", LocalDateTime.now(), "999999-9999999",
        "서울특별시 강남구", "삼성동 92-1", "ROLE_CONSUMER", true
    );
    userService.join(membersTestConsumerDTO,"주택용","저압", installedCapacity);

    installedCapacity=6;    
    //회원가입확인용(프로슈머)
    MembersDTO membersTestProsumerDTO= new MembersDTO(
        "프로슈머테스트2", "비번1", LocalDateTime.now(), "999999-9999999",
        "서울특별시 강남구", "삼성동 92-1", "ROLE_PROSUMER", true
    );
    userService.join(membersTestProsumerDTO,"주택용","고압", installedCapacity);
    
    
    return "redirect:/";
    }

    @GetMapping("/pending") // 20240415 1132 잠시 사용하지 않으려고 펜딩으로 바꿔둠
    public String pending(
        Model model
    ){
// 분석보고서
    //실제 최근 1년치 전력 사용량을 가져오는 서비스
        Map<String, List> consActual = analysisService.readActualCons("test_ath");
        model.addAttribute("ConsumptionMonths", consActual.get("ConsumptionMonths"));
        model.addAttribute("ActualConsumption", consActual.get("ActualConsumption"));
        model.addAttribute("ConsumptionBills", consActual.get("ConsumptionBills"));

    //실제 최근 1년치 태양광 발전량을 가져오는 서비스
        Map<String,List> prodActual = analysisService.readActualProd("test_ath");
        model.addAttribute("ActualProduction", prodActual.get("ActualProduction"));

    //최근 1년치 추정 태양광 발전량(예측 모델: LGBM) 가져오는 서비스
    // + 0~4개월 치 예측 전력 소비량(예측 모델: ARIMA) 가져오는 서비스
        Map<String,List> prediction = analysisService.readPrediction("test_ath", 0);
        model.addAttribute("PredictProduction", prediction.get("PredictProduction"));
        model.addAttribute("PredictConsumption", prediction.get("PredictConsumption"));
        model.addAttribute("PredictConsumptionMonths", prediction.get("PredictConsumptionMonths"));

    //손익분기점에 도달하는 개월 수 계산 및 이후 1년간의 이익 계산 서비스
        Map<String,List> breakeven= analysisService.consumerAnanlysis("test_ath",5, 5000000);
        model.addAttribute("RequiredMonths", breakeven.get("requiredMonths"));
        model.addAttribute("NetRevenues", breakeven.get("netRevenues"));

    // 0~4개월 치 예상 전기요금(예측모델: LSTM)을 가져오는 서비스
        Map<String,List> pricePrediction = analysisService.getPricePrediction("test_ath");
        model.addAttribute("PredictPriceMonths", pricePrediction.get("PredictPriceMonths"));
        model.addAttribute("PredictPriceProg1", pricePrediction.get("PredictPriceProg1"));
        model.addAttribute("PredictPriceProg2", pricePrediction.get("PredictPriceProg2"));
        model.addAttribute("PredictPriceProg3", pricePrediction.get("PredictPriceProg3"));



        return "test_th";
    }

    /**
     * 프로슈머의 데이터를 반환하는 컨트롤러
     * @param memberId
     * @return
     */
    @GetMapping("/report/getProsumerData")
    @ResponseBody
    public Map<String,List> getProsumerData(
        @RequestParam(name="memberId") String memberId
    ){
        // 결과 담을 데이터
        Map<String,List> respData = new HashMap<>();

        // 소비량 전체 반환
        Map<String, List> consActual = analysisService.readActualCons(memberId);
        respData.put("ActualConsumptionMonths", consActual.get("ActualConsumptionMonths"));
        respData.put("ActualConsumption", consActual.get("ActualConsumption"));
        respData.put("ActualConsumptionBills", consActual.get("ActualConsumptionBills"));

        // 실제 생산량 및 추정 생산량 반환
        Map<String,List> prodActual= analysisService.readActualProd(memberId);
        Map<String,List> prediction= analysisService.readPrediction(memberId, 0);
        respData.put("ActualProduction", prodActual.get("ActualProduction"));
        respData.put("PredictProduction",prediction.get("PredictProduction"));
        respData.put("PredictProductionMonths",prediction.get("PredictProductionMonths"));
        
        // 예측 소비량 및 예측 가격 반환
        Map<String,List> pricePrediction=analysisService.getPricePrediction(memberId);
        respData.put("PredictConsumptionMonths", prediction.get("PredictConsumptionMonths"));
        respData.put("PredictConsumption", prediction.get("PredictConsumption"));
        respData.put("PredictPriceMonths", pricePrediction.get("PredictPriceMonths"));
        respData.put("PredictPriceProg1", pricePrediction.get("PredictPriceProg1"));
        respData.put("PredictPriceProg2", pricePrediction.get("PredictPriceProg2"));
        respData.put("PredictPriceProg3", pricePrediction.get("PredictPriceProg3"));

        return respData;
    }

    @GetMapping("/report/getConsumerData")
    @ResponseBody
    public Map<String,List> getConsumerData(
        @RequestParam(name="memberId") String memberId,
        @RequestParam(name="capacity") String capacityStr,
        @RequestParam(name="cost") String costStr
    ){
        int capacity; capacity = Integer.parseInt(capacityStr);
        int cost; cost = Integer.parseInt(costStr);


        // 결과 담을 데이터
        Map<String,List> respData = new HashMap<>();

        // 소비량 전체 반환
        Map<String, List> consActual = analysisService.readActualCons(memberId);
        respData.put("ActualConsumption", consActual.get("ActualConsumption"));
        respData.put("ActualConsumptionMonths", consActual.get("ActualConsumptionMonths"));

        // 손익분기점 반환
        Map<String,List> consumerPrediction= analysisService.consumerAnanlysis(memberId, capacity, cost);
        respData.put("RequiredMonths", consumerPrediction.get("RequiredMonths"));
        respData.put("NetRevenues", consumerPrediction.get("NetRevenues"));
        
        // 예측 소비량 및 예측 가격 반환
        respData.put("PredictConsumption", consumerPrediction.get("PredictConsumption"));
        respData.put("PredictConsumptionMonths", consumerPrediction.get("PredictConsumptionMonths"));

        Map<String,List> pricePrediction=analysisService.getPricePrediction(memberId);
        respData.put("PredictPriceMonths", pricePrediction.get("PredictPriceMonths"));
        respData.put("PredictPriceProg1", pricePrediction.get("PredictPriceProg1"));
        respData.put("PredictPriceProg2", pricePrediction.get("PredictPriceProg2"));
        respData.put("PredictPriceProg3", pricePrediction.get("PredictPriceProg3"));

        return respData;
    }
    
    
}
