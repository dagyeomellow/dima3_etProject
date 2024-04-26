package com.example.etProject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.etProject.entity.MembersEntity;
import com.example.etProject.repository.MembersRepository;

@SpringBootTest
class EtProjectApplicationTests {

    @Test
    void test(){

    }

    }


    // @Autowired
    // private BCryptPasswordEncoder passwordEncoder;

    // @Test
    // void encodeExistingPasswords() {
    //     // 모든 멤버를 가져옵니다.
    //     List<MembersEntity> members = membersRepository.findAll();

    //     // 각 멤버의 비밀번호를 인코딩합니다.
    //     for (MembersEntity member : members) {
    //         String rawPassword = member.getMemberPw();
    //         String encodedPassword = passwordEncoder.encode(rawPassword);
    //         member.setMemberPw(encodedPassword);
    //         membersRepository.save(member);
    //     }
    // }


