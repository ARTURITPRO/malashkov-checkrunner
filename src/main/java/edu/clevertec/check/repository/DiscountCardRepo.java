package edu.clevertec.check.repository;

import edu.clevertec.check.dto.DiscountCard;

import java.util.Collection;
import java.util.Optional;

public interface DiscountCardRepo {

    Collection<DiscountCard > findAll();
    Collection<DiscountCard > findAll(Integer pageSize);
    Collection<DiscountCard > findAll(Integer pageSize, Integer size);
    DiscountCard save(DiscountCard discountCard);

    DiscountCard findById(int id);

    boolean delete(int id);

    Optional<DiscountCard> update(DiscountCard discountCard);
}
