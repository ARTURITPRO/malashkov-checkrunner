package edu.clevertec.check.repository.impl;


import edu.clevertec.check.dto.DiscountCard;
import edu.clevertec.check.repository.DiscountCardRepo;
import edu.clevertec.check.util.ConnectionManager;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static edu.clevertec.check.dto.DiscountCard.MAESTROCARD;
import static edu.clevertec.check.dto.DiscountCard.MASTERCARD;

@Slf4j
public class DiscountCardRepoImpl implements DiscountCardRepo {

    @Override
    @SneakyThrows
    public DiscountCard save(DiscountCard discountCard) {
        Connection connection = ConnectionManager.get();
        return save(connection, discountCard);
    }

    @SneakyThrows
    private DiscountCard save(Connection connection, DiscountCard discountCard) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO discountCard (sale) " +
                        "VALUES (?);", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setObject(1, discountCard.getDiscount());

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        log.info("The entity is saved in the database: {}", discountCard);

        return discountCard;
    }

    @Override
    @SneakyThrows
    public DiscountCard findById(int id) {
        Connection connection = ConnectionManager.get();
        return findById(connection, id);
    }

    @SneakyThrows
    private DiscountCard findById(Connection connection, int id) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, sale " +
                        "FROM discountCard " +
                        "WHERE id = ?;"
        );

        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        DiscountCard discountCard = null;
        if (resultSet.next()) {
            if (resultSet.getObject("sale", Integer.class) < 6) {
                log.info("The entity was found in the database: {}", MAESTROCARD);
                return MAESTROCARD;
            }
            if (resultSet.getObject("sale", Integer.class) >= 6) {
                log.info("The entity was found in the database: {}", MASTERCARD);
                return MASTERCARD;
            } else {
                log.info("The entity not found in the database with id = {}", id);
            }
        }
        return discountCard;
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
                "DELETE FROM discountCard " +
                        "WHERE id = ?;"
        );
        preparedStatement.setObject(1, id);
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            log.info("The entity was deleted in the database: id: {}", id);
            return true;
        }
        return false;
    }

    @Override
    @SneakyThrows
    public DiscountCard update(DiscountCard discountCard, int idCard) {
        Connection connection = ConnectionManager.get();
        return update(connection, discountCard, idCard);
    }

    @SneakyThrows
    private DiscountCard update(Connection connection, DiscountCard discountCard, int idCard) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE discountCard " +
                        "SET sale = ? " +
                        "WHERE id = ? " +
                        "RETURNING id, sale; "
        );
        preparedStatement.setObject(1, discountCard.getDiscount());
        preparedStatement.setObject(2, idCard);


        ResultSet resultSet = preparedStatement.executeQuery();
        DiscountCard discountCardResult = null;

        if (resultSet.next()) {
            discountCardResult = discountCard;
            log.info("The entity has been updated in the database: {}", discountCardResult);
        }
        return discountCardResult;
    }
}
