package edu.clevertec.check.service.impl;

import edu.clevertec.check.annotation.Caches;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.repository.ProductRepo;
import edu.clevertec.check.repository.impl.ProductRepoImpl;
import edu.clevertec.check.service.ProductService;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.Optional;

public class ProductServiceImpl implements ProductService<Integer, Product> {

    private final ProductRepo<Integer, Product> productRepo = new ProductRepoImpl();

    @Override
    public Collection<Product> findAll(Integer pageSize, Integer size) {
        return productRepo.findAll(pageSize, size);
    }

    @Override
    public Collection<Product> findAll(Integer pageSize) {
        return productRepo.findAll(pageSize);
    }

    @Caches
    @SneakyThrows
    @Override
    public Product save(Product item) {
        return productRepo.save(item);
    }

    @Caches
    @SneakyThrows
    @Override
    public Optional<Product> findById(Integer id) {
        return productRepo.findById(id);
    }

    @Caches
    @SneakyThrows
    @Override
    public Optional<Product> update(Product entity) {
        return productRepo.update(entity);
    }

    @Caches
    @SneakyThrows
    @Override
    public boolean delete(Integer id) {
        return productRepo.delete(id);
    }
}
