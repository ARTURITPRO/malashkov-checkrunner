package edu.clevertec.check.repository;

import edu.clevertec.check.jdbc.entity.Product;

public interface ProductRepository {

    Product save(Product product);

    Product findById(int id);

    boolean delete(int id);

    Product update(Product product);
}
