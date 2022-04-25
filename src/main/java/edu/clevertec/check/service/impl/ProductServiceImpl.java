package edu.clevertec.check.service.impl;

import edu.clevertec.check.annotation.Caches;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.repository.ProductRepo;
import edu.clevertec.check.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService<Integer, Product> {

    private final ProductRepo<Integer, Product> productRepo;

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
