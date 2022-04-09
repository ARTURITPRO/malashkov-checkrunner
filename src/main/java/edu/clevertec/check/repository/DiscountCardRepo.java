package edu.clevertec.check.repository;

import edu.clevertec.check.dto.DiscountCard;
import lombok.SneakyThrows;

public interface DiscountCardRepo {
    DiscountCard save(DiscountCard discountCard);

    DiscountCard findById(int id);

    boolean delete(int id);

    DiscountCard update(DiscountCard discountCard, int idCard);
}
