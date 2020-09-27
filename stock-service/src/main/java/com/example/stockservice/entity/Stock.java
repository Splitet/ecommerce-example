package com.example.stockservice.entity;

import com.kloia.eventapis.spring.model.JpaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "STOCK")
public class Stock extends JpaEntity {

    private String stockName;

    private long remainingStock;

    @Enumerated(EnumType.STRING)
    private StockState state;

}
