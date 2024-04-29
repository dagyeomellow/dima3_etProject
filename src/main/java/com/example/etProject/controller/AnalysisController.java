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
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {
    private final AnalysisService analysisService;
    private final UserService userService;
    
    private Map<String,List> prodActual=null;
    private Map<String,List> prediction=null;
    
    @GetMapping("/consult")
    public String consult(){

        return "/analysis/consult";
    }

    @GetMapping("/report")
    public String report(){
        return "/analysis/report";
    }

    @GetMapping("/report/prepare")
    @ResponseBody
    public Map<String,Boolean> prepare(
        @RequestParam(name="memberId") String memberId
    ){
        prodActual= analysisService.readActualProd(memberId);
        prediction= analysisService.readPrediction(memberId, 0);
        Map<String,Boolean> resp = new HashMap<>();
        resp.put("result",true);
        return resp;
    }

    /**
     * 실제 소비량 가져오는 함수
     * @param memberId
     * @return "ActualConsumptionMonths", "ActualConsumption"
     */
    @GetMapping("/report/getActualConsumptionData")
    @ResponseBody
    public Map<String, List> getActualConsumptionData(
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List> respData = new HashMap<>();
        Map<String, List> consActual = analysisService.readActualCons(memberId);
        respData.put("ActualConsumptionMonths", consActual.get("ActualConsumptionMonths"));
        respData.put("ActualConsumption", consActual.get("ActualConsumption"));

        return respData;
    }
    
    @GetMapping("/report/getPredictProductionData")
    @ResponseBody
    public Map<String,List> getgetPredictProductionData(
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List> respData = new HashMap<>();

        respData.put("ActualProduction", prodActual.get("ActualProduction"));
        respData.put("PredictProduction",prediction.get("PredictProduction"));
        respData.put("PredictProductionMonths",prediction.get("PredictProductionMonths"));

        return respData;
    }
    
    @GetMapping("/report/getFutureData")
    @ResponseBody
    public Map<String,List> getPredictionData(
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List> respData = new HashMap<>();
        Map<String,List> pricePrediction=analysisService.getPricePrediction(memberId);
        respData.put("PredictConsumptionMonths", prediction.get("PredictConsumptionMonths"));
        respData.put("PredictConsumption", prediction.get("PredictConsumption"));
        respData.put("PredictPriceMonths", pricePrediction.get("PredictPriceMonths"));
        respData.put("PredictPriceProg1", pricePrediction.get("PredictPriceProg1"));
        respData.put("PredictPriceProg2", pricePrediction.get("PredictPriceProg2"));
        respData.put("PredictPriceProg3", pricePrediction.get("PredictPriceProg3"));
        return respData;
    }

    @GetMapping("/report/getBreakevenData")
    @ResponseBody
    public Map<String,List> getBreakevenData(
        @RequestParam(name="cost") String costStr,
        @RequestParam(name="capacity") String capacityStr,
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List> respData = new HashMap<>();
        int capacity; capacity = Integer.parseInt(capacityStr);
        int cost; cost = Integer.parseInt(costStr);

        Map<String,List> consumerPrediction= analysisService.consumerAnanlysis(memberId, capacity, cost);
        respData.put("RequiredMonths", consumerPrediction.get("RequiredMonths"));
        respData.put("NetRevenues", consumerPrediction.get("NetRevenues"));

        return respData;
    }

    /**
     * 프로슈머의 데이터를 반환하는 컨트롤러
     * @param memberId
     * @return
     */
    @GetMapping("/pending/getProsumerData")
    @ResponseBody
    public Map<String,List> getProsumerData(
        @RequestParam(name="memberId") String memberId
    ){
        log.info("백단도착");
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
    
    @GetMapping("/pending/getConsumerData")
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
