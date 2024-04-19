package com.example.etProject.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.etProject.service.AnalysisService;
import com.example.etProject.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;
    private final UserService userService;

    @GetMapping("/report")
    public String test(
        // @RequestParam(name="memberId") String memberId,
        Model model
    ){
        String memberId="test_nje";
        Boolean isProsumer = userService.isProsumer(memberId);
        model.addAttribute("isProsumer", isProsumer);
        return "/analysis/report";
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
}
