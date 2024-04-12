package com.example.etProject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.etProject.service.AnalysisService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class myTestController {

    private final AnalysisService analysisService;

    @GetMapping("/test")
    public String test(
        Model model
    ){
        //실제 최근 1년치 태양광 발전량을 가져오는 서비스
        Map<String,List> prodActual = analysisService.readActualProd("test_ath");
        model.addAttribute("prodActual", prodActual);

        //최근 1년치 추정 태양광 발전량(예측 모델) 가져오는 서비스
        Map<String,String> prodPredict = analysisService.readPredProd("test_ath");
        model.addAttribute("prodPredict", prodPredict);

        return "test_th";
    }
}
