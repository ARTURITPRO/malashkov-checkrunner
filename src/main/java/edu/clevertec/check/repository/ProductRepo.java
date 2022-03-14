package edu.clevertec.check.repository;

import edu.clevertec.check.dto.Product;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepo<K, T> {

    Collection<Product> findAll();
    
    T save(T entity);

    Optional<T> findById(K id);

    Optional<T> update(T entity);

    boolean delete(K id);
}


