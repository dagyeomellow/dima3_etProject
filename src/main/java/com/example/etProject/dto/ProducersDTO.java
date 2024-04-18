package com.example.etProject.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProducersDTO {

    private String producerId;
    private String memberId;
    private Double locationX;
    private Double locationY;
    private Double installedCapacity;
    private Boolean isProduce;

    
}
