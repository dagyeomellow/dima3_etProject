package com.example.etProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.etProject.dto.MembersDTO;
import com.example.etProject.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MembersController {

    private final UserService userService;

    @GetMapping("/joinForm")
    public String joinProc(){
        return "/members/joinForm";
    };

    @PostMapping("/joinForm")
    @ResponseBody
    public String joinProc(
        @ModelAttribute MembersDTO membersDTO,
        @RequestParam(name="customerType") String customerType,
        @RequestParam(name="contractType") String contractType,
        @RequestParam(name="installedCapacity", required=false, defaultValue = "-1") int installedCapacity,
        @RequestParam(name="memberName") String memberName
    ){
        userService.join(membersDTO,customerType,contractType, installedCapacity);
        return memberName;
    };

    @GetMapping("/loginForm")
    public String login(
        @RequestParam(value="error", required=false) String error,
        @RequestParam(value="errMessage", required=false) String exception,
        Model model
        ){
        model.addAttribute("error",error);
        model.addAttribute("errMessage",exception);
        return "/members/login";
    };
}
