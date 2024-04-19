package com.example.etProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}
	
	// 회원가입
	
	// 마이페이지
	
	// 분석보고서
	
	// 플랫폼
}
