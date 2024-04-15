package com.example.etProject.controller;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/test")
    public String test(
        Model model
    ){
// 유저서비스
    // //회원가입 확인용(프로슈머)
        // MembersDTO membersDTO= new MembersDTO(
        //     "회원가입테스트1", "비번1", LocalDateTime.now(), "999999-9999999",
        //     "서울특별시 강남구", "삼성동 92-1", "ROLE_PROSUMER", true
        // );
        // userService.join(membersDTO,"주택용","저압",(double) 12);
        //  @ modelattribute와 @requestparam 같이 파라미터로 받으면됨

    //  //회원가입확인용(컨슈머)
        // MembersDTO membersDTO= new MembersDTO(
        //     "테스트컨슈머1", "비번1", LocalDateTime.now(), "999999-9999999",
        //     "서울특별시 강남구", "삼성동 92-1", "ROLE_PROSUMER", true
        // );
        // userService.join(membersDTO,"주택용","저압",null);


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
        Map<String,List> prediction = analysisService.readPrediction("test_ath", null);
        model.addAttribute("PredictProduction", prediction.get("PredictProduction"));
        model.addAttribute("PredictConsumption", prediction.get("PredictConsumption"));

    //손익분기점에 도달하는 개월 수 계산 및 이후 1년간의 이익 계산 서비스
        Map<String,List> breakeven= analysisService.calcBreakeven("test_ath", (double) 3, (double) 8000000);
        model.addAttribute("requiredMonths", breakeven.get("requiredMonths"));
        model.addAttribute("netRevenues", breakeven.get("netRevenues"));

    // 0~4개월 치 예상 전기요금(예측모델: LSTM)을 가져오는 서비스
        Map<String,List> pricePrediction = analysisService.getPricePrediction("test_ath");
        model.addAttribute("PricePrediction", pricePrediction);
        return "test_th";
    }
}
