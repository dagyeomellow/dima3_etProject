package com.example.etProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.etProject.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	
	private final UserService userService;
	
	@GetMapping({"","/"})
<<<<<<< HEAD
	public String idCheck() {
		return "idCheck";
=======
	public String test_th() {
		return "index";
>>>>>>> ef81e0397825fd7abaaa48baf1e59c1268f31626
	}
	
	/*
	@GetMapping("/idCheck")
	@ResponseBody
	public boolean test_th(
			@RequestParam(name = "uncheckedId") String uncheckedId) 
	// , Model model)
		{
		
		log.info("=============================", uncheckedId);
		
		// boolean result= userService.idCheck(uncheckedId);
		
		// model.addAttribute("중복확인결과", result);
		
		return userService.idCheck(uncheckedId);
	} */
	
	@GetMapping("/params")
	public 
	
	// 회원가입
	
	// 마이페이지
	
	// 분석보고서
	
	// 플랫폼
}
