package edu.clevertec.check.repository.impl;

import edu.clevertec.check.dto.Fish;
import edu.clevertec.check.dto.Meat;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.dto.Sweets;
import edu.clevertec.check.repository.ProductRepo;
import lombok.SneakyThrows;

import edu.clevertec.check.dto.Product;
import edu.clevertec.check.service.impl.SupermarketServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductRepoImpl implements ProductRepo<Integer, Product> {

    private static final Map<Integer, Product> PRODUCTS = new HashMap<>();

    static {
        PRODUCTS.put(2, new Meat(2, "Meat", 5.01d, false));
        PRODUCTS.put(4, new Fish(4, "Fish Perch", 1.30d, false));
        PRODUCTS.put(1, new Fish(1, "Fish Sturgeon", 1.80d, false));
        PRODUCTS.put(3, new Sweets(3, "KitKat", 2.80d, true));
        PRODUCTS.put(5, new Sweets(5, "Snickers", 3.50d, true));
        PRODUCTS.put(6, new Sweets(6, "Bounty", 2.30d, true));
        PRODUCTS.put(7, new Sweets(7, "Tic-tac", 1.70d, true));
        PRODUCTS.put(8, new Sweets(8, "NUTS", 1.40d, true));
    }



    @Override
    public Collection<Product> findAll() {
        return PRODUCTS.values();
    }

    @SneakyThrows
    @Override
    public Product save(Product item) {
        int a =  PRODUCTS.size();
        ++a;
        PRODUCTS.put(a,item);
        return item;
    }



    @SneakyThrows
    @Override
    public Optional<Product> findById(Integer id) {
        return Optional.of(PRODUCTS.get(id));
    }

    @Override
    public Optional<Product> update(Product entity) {
        return Optional.of(entity);
    }

    @SneakyThrows
    @Override
    public boolean delete(Integer id) {
        if(PRODUCTS.containsKey(id)){
        PRODUCTS.remove(id);
        return true;
        }
        return false;
    }
}
