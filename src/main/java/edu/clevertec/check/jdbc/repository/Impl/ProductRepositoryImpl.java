package edu.clevertec.check.jdbc.repository.Impl;

import edu.clevertec.check.jdbc.entity.Product;
import edu.clevertec.check.jdbc.repository.ProductRepository;
import edu.clevertec.check.jdbc.util.ConnectionManager;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {


    @Override
    @SneakyThrows
    public Product save(Product product) {
        Connection connection = ConnectionManager.get();
        return save(connection, product);
    }

    @SneakyThrows
    private Product save(Connection connection, Product product) {

        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO products (name, price, quantity, in_stock) " +
                        "VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setObject(1, product.getName());
        preparedStatement.setObject(2, product.getPrice());
        preparedStatement.setObject(3, product.getQuantity());
        preparedStatement.setObject(4, product.isIn_stock());

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        product.setId(generatedKeys.getObject("id", Integer.class));

        log.info("The product is saved in the database: {}", product);
        return product;

    }

    @Override
    @SneakyThrows
    public Product findById(int id) {
        Connection connection = ConnectionManager.get();
        return findById(connection, id);
    }

    @SneakyThrows
    private Product findById(Connection connection, int id) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name, price, quantity, in_stock " +
                        "FROM products " +
                        "WHERE id = ?;");

        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Product product = null;

        if (resultSet.next()) {
            product = Product.builder()
                    .id(resultSet.getObject("id", Integer.class))
                    .name(resultSet.getObject("name", String.class))
                    .price(resultSet.getObject("price", BigDecimal.class))
                    .quantity(resultSet.getObject("quantity", Integer.class))
                    .in_stock(resultSet.getObject("in_stock", Boolean.class))
                    .build();
            log.info("The entity was found in the database: {}", product);
        }
        return product;
    }

    @Override
    @SneakyThrows
    public boolean delete(int id) {
        Connection connection = ConnectionManager.get();
        return delete(connection, id);
    }

    @SneakyThrows
    private boolean delete(Connection connection, int id) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM products " +
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

    @Override
    @SneakyThrows
    public Product update(Product product) {
        Connection connection = ConnectionManager.get();
        return update(connection, product);
    }

    @SneakyThrows
    private Product update(Connection connection, Product product) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE products " +
                        "SET name = ?, price = ?, sale = ?, quantity = ?, in_stock = ? " +
                        "WHERE id = ? " +
                        "RETURNING id, name, price, sale, quantity , in_stock ;"
        );

        preparedStatement.setObject(1, product.getName());
        preparedStatement.setObject(2, product.getPrice());
        preparedStatement.setObject(3, product.isSale());
        preparedStatement.setObject(4, product.getQuantity());
        preparedStatement.setObject(5, product.isIn_stock());
        preparedStatement.setObject(6, product.getId());

        ResultSet resultSet = preparedStatement.executeQuery();
        Product productResult = null;
        if (resultSet.next()) {
            productResult = Product.builder()
                    .id(resultSet.getObject("id", Integer.class))
                    .name(resultSet.getObject("name", String.class))
                    .price(resultSet.getObject("price", BigDecimal.class))
                    .quantity(resultSet.getObject("quantity", Integer.class))
                    .in_stock(resultSet.getObject("in_stock", Boolean.class))
                    .build();
            log.info("The entity has been updated in the database: {}", productResult);
        }
        return productResult;
    }

}
