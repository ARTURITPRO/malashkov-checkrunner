package edu.clevertec.check.jdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {

    private int id;
    private String name;
    private BigDecimal price;
    private boolean sale;
    private int quantity;
    private boolean in_stock;

}


