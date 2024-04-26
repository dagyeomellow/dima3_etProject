package com.example.etProject.handler;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request
        , HttpServletResponse response
        , AuthenticationException exception) throws IOException, ServletException {
		
		String errMessage = "";
		
		if(exception instanceof BadCredentialsException) {
			errMessage = exception.getMessage();
			errMessage +="\n아이디나 비밀번호가 잘못되었습니다.";
		} else {
			errMessage = exception.getMessage();
			errMessage +="\n로그인에 실패했습니다. 관리자에게 문의하세요";
		}
		errMessage = URLEncoder.encode(errMessage, "UTF-8");
		
		setDefaultFailureUrl("/member/login?error=true&errMessage="+errMessage);
		
		super.onAuthenticationFailure(request, response, exception);
	}
}
