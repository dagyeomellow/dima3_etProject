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
public class ProductionsDTO {
    private Long productionNum;
    private String producerId;
    private LocalDate prodDate;
    private Double prodElectricity;
    private int prodDateMonth;
}
