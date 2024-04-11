package com.example.etProject.entity;

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

import java.util.Random;

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
    @JoinColumn(name="MEMBER_ID")
    private MembersEntity membersEntity;
    @Column(name="LOCATION_X")
    private Double locationX;
    @Column(name="LOCATION_Y")
    private Double locationY;
    @Column(name="INSTALLED_CAPACITY")
    private Double installedCapacity;
}