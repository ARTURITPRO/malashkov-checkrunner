package edu.clevertec.check.jdbc.repository;


import edu.clevertec.check.jdbc.entity.DiscountCard;

public interface DiscountCardRepository {

    DiscountCard save(DiscountCard discountCard);

    DiscountCard findById(int id);

    boolean delete(int id);

    DiscountCard update(DiscountCard discountCard);

}
