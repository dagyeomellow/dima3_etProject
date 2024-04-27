package com.example.etProject;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.etProject.dto.MembersDTO;
import com.example.etProject.entity.MembersEntity;
import com.example.etProject.repository.MembersRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class EtProjectApplicationTests {

    private final MembersRepository membersRepository;
    private final MembersEntity MembersEntity;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Test
    void test(){
        MembersDTO membersDTO = new MembersDTO("test_jdg", passwordEncoder.encode("1234"),LocalDateTime.now(),"999999-2222222","경상북도 울산시",null,"ROLE_CONSUMER",true);
        membersRepository.save(MembersEntity.toEntity(membersDTO));
    }

}


    

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


