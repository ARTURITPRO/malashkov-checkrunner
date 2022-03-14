package edu.clevertec.check.repository.impl;

import edu.clevertec.check.dto.Product;
import edu.clevertec.check.dto.Sweets;
import edu.clevertec.check.repository.ProductRepo;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import edu.clevertec.check.jdbc.util.JdbcManager;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
public class ProductRepoImpl implements ProductRepo<Integer, Product> {


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


    @SneakyThrows
    public static Product findInDatabase(int id) {
        Connection connection = JdbcManager.get();
        return findInDatabase(connection, id);
    }

    @SneakyThrows
    private static Product findInDatabase(Connection connection, int id) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id_product, name, cost, promotional " +
                        "FROM myProducts " +
                        "WHERE id = ?;");

        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        edu.clevertec.check.dto.Sweets sv = null;

        if (resultSet.next()) {

          //  PRODUCTS.put(2, new Meat(2, "Meat", 5.01d, false));
            PRODUCTS.put
                    (resultSet.getObject("id_product", Integer.class),
                            new Sweets(id,
                                    resultSet.getObject("name", String.class),
                                    resultSet.getObject("cost", Double.class),
                                    resultSet.getObject("promotional", Boolean.class)));
            log.info("The entity was found in the database: {}");
        }

        return null;
    }

}
