package com.example.etProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.etProject.handler.CustomFailureHandler;

import lombok.RequiredArgsConstructor;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomFailureHandler cfHandler;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .authorizeHttpRequests(
            (auth) -> auth
            // .requestMatchers(
            //     "/analysis/report/getProsumerData"
            // ).hasRole("PROSUMER")
            // .requestMatchers(
            //     "/analysis/report/getConsumerData"
            // ).hasRole("CONSUMER")
            .anyRequest().permitAll()
        );

        http
        .formLogin(
            (auth) -> auth
                .loginPage("/members/loginForm")
                .failureHandler(cfHandler)
                .usernameParameter("memberId")
                .passwordParameter("memberPw")
                .loginProcessingUrl("/members/loginProc")
                .defaultSuccessUrl("/").permitAll()
        );

        http
        .logout(
            (auth) -> auth
                .logoutUrl("/members/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONONID")
        );

        http
        .csrf(
            (auth)->auth.disable()
        );
        return http.build();
    }
    @Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
