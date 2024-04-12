package com.example.etProject.entity;

import com.example.etProject.dto.ProducersDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

@Entity
@Table(name="PRODUCERS")
public class ProducersEntity {

    @Id
    @Column(name="PRODUCER_ID")
    private String producerId;
    
    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private MembersEntity membersEntity;

    @Column(name="LOCATION_X")
    private Double locationX;
    @Column(name="LOCATION_Y")
    private Double locationY;
    @Column(name="INSTALLED_CAPACITY")
    private Double installedCapacity;

    public static ProducersEntity toEntity(ProducersDTO producersDTO, MembersEntity membersEntity){
        ProducersEntity producersEntity = ProducersEntity.builder()
            .producerId(producersDTO.getProducerId())
            .membersEntity(membersEntity)
            .installedCapacity(producersDTO.getInstalledCapacity())
            .locationX(producersDTO.getLocationX())
            .locationY(producersDTO.getLocationY())
            .build();
        return producersEntity;
    }
}