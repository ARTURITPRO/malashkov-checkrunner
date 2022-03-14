package edu.clevertec.check.jdbc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountCard {

    private int id;
    private int discount_percentage;

}
