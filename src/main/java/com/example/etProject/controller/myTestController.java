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

@Controller
@RequiredArgsConstructor
public class myTestController {

    private final AnalysisService analysisService;
    private final UserService userService;

    @GetMapping("/test")
    public String test(
        Model model
    ){
        //실제 최근 1년치 태양광 발전량을 가져오는 서비스
        Map<String,List> prodActual = analysisService.readActualProd("test_ath");
        model.addAttribute("prodActual", prodActual);

        //최근 1년치 추정 태양광 발전량(예측 모델: LGBM) 가져오는 서비스
        // + 0~4개월 치 예측 전력 소비량(예측 모델: ARIMA) 가져오는 서비스
        Map<String,List> prediction = analysisService.readPredProd("test_ath");
        model.addAttribute("prediction", prediction);

        // 회원가입 확인용
        // MembersDTO membersDTO= new MembersDTO(
        //     "회원가입테스트1", "비번1", LocalDateTime.now(), "999999-9999999",
        //     "서울특별시 강남구", "삼성동 92-1", "ROLE_PROSUMER", true
        // );
        // userService.join(membersDTO,"주택용","저압",(double) 12);
        //  @ modelattribute와 @requestparam 같이 파라미터로 받으면됨

        return "test_th";
    }
}
