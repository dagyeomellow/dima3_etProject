package com.example.etProject.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.etProject.repository.MembersRepository;
import com.example.etProject.repository.ProducersRepository;
import com.example.etProject.repository.ProductionsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final MembersRepository membersRepository;
    private final ProducersRepository producersRepository;
    private final ProductionsRepository productionsRepository;

    public Map<String, List> readProd(
        String memberId
    ){
        producersRepository.findProducerIdByMemberId(memberId);
        return null;
    }
}
