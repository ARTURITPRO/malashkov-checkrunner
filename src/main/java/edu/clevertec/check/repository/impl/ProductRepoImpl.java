package edu.clevertec.check.repository.impl;

import edu.clevertec.check.dto.Product;
import edu.clevertec.check.util.ConnectionManager;
import edu.clevertec.check.repository.ProductRepo;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Repository
@Slf4j
public class ProductRepoImpl implements ProductRepo<Integer, Product> {

    @SneakyThrows
    @Override
    public Collection<Product> findAll(Integer pageSize) {
        Connection connection = ConnectionManager.get();
        PreparedStatement stmt = connection.prepareStatement(
                "select * from product ORDER BY id " + "LIMIT ?;" );

        stmt.setObject(1, pageSize);
        ResultSet resultSet = stmt.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product(
                             resultSet.getObject("id", Integer.class),
                    resultSet.getObject("name", String.class),
                    resultSet.getObject("cost", Double.class),
                    resultSet.getObject("promotional", Boolean.class)
            );
            log.info("The entity was found in the database: {}", product);
            products.add(product);
        }
        return products;
    }

    @SneakyThrows
    @Override
    public Collection<Product> findAll(Integer pageSize, Integer size) {
        Connection connection = ConnectionManager.get();
        PreparedStatement stmt = connection.prepareStatement(
                "select * from product ORDER BY id " +"OFFSET ? "+"  LIMIT ?;" );
        final Integer CONST = 1;
        Integer i = pageSize * (size-CONST);
        stmt.setObject(1, i);
        stmt.setObject(2, pageSize);
        ResultSet resultSet = stmt.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("name", String.class),
                    resultSet.getObject("cost", Double.class),
                    resultSet.getObject("promotional", Boolean.class)
            );
            log.info("The entity was found in the database: {}", product);
            products.add(product);
        }
        return products;
    }

    @SneakyThrows
    @Override
    public Product save(Product product) {
        Connection connection = ConnectionManager.get();
        return save(connection, product);
    }

    @SneakyThrows
    private Product save(Connection connection, Product product) {

        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO product (name, cost, promotional) " +
                        "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setObject(1, product.getName());
        preparedStatement.setObject(2, product.getCost());
        preparedStatement.setObject(3, product.getPromotional());

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        product.setId(generatedKeys.getObject("id", Integer.class));

        log.info("The product is saved in the database: {}", product);
        return product;
    }

    @SneakyThrows
    @Override
    public Optional<Product> findById(Integer id) {
        Connection connection = ConnectionManager.get();
        return Optional.ofNullable(findById(connection, id));
    }

    @SneakyThrows
    private Product findById(Connection connection, int id) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name, cost, promotional " +
                        "FROM product " +
                        "WHERE id = ?");

        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Product product = null;

        if (resultSet.next()) {
            product = new Product(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("name", String.class),
                    resultSet.getObject("cost", Double.class),
                    resultSet.getObject("promotional", Boolean.class)
            );
            log.info("The entity was found in the database: {}", product);
        }
        return product;
    }

    @Override
    public Optional<Product> update(Product product) {
        Connection connection = ConnectionManager.get();
        return Optional.ofNullable(update(connection, product));
    }

    @SneakyThrows
    private Product update(Connection connection, Product product) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE product " +
                        "SET name = ?, cost = ?, promotional = ? " +
                        "WHERE id = ? " +
                        "RETURNING id, name,  cost, promotional  ;"
        );
        // id, name, cost, promotional
        preparedStatement.setObject(4, product.getId());
        preparedStatement.setObject(1, product.getName());
        preparedStatement.setObject(2, product.getCost());
        preparedStatement.setObject(3, product.getPromotional());
        ResultSet resultSet = preparedStatement.executeQuery();
        Product productResult = null;
        if (resultSet.next()) {
            productResult = new Product(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("name", String.class),
                    resultSet.getObject("cost", Double.class),
                    resultSet.getObject("promotional", Boolean.class)
            );
            log.info("The entity has been updated in the database: {}", productResult);
        }
        return productResult;
    }


    @SneakyThrows
    @Override
    public boolean delete(Integer id) {
        Connection connection = ConnectionManager.get();
        return delete(connection, id);
    }

    @SneakyThrows
    private boolean delete(Connection connection, int id) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM product " +
                        "WHERE id = ?;"
        );

        preparedStatement.setObject(1, id);

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            log.info("The entity was deleted in the database: id: {}", id);
            return true;
        }
        return false;
    }
}
