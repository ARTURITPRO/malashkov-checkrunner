package edu.clevertec.check.jdbc.repository.Impl;

import edu.clevertec.check.entity.DiscountCard;
import edu.clevertec.check.jdbc.repository.DiscountCardRepository;
import edu.clevertec.check.jdbc.util.JdbcManager;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public class DiscountCardRepositoryImpl implements DiscountCardRepository {


    @Override
    @SneakyThrows
    public DiscountCard save(DiscountCard discountCard) {
        Connection connection = JdbcManager.get();
        return save(connection, discountCard);
    }

    @SneakyThrows
    private DiscountCard save(Connection connection, DiscountCard discountCard) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO discount_cards (discount_percentage) " +
                        "VALUES (?);", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setObject(1, discountCard.getDiscount_percentage());

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        discountCard.setId(generatedKeys.getObject("id", Integer.class));
        log.info("The entity is saved in the database: {}", discountCard);

        return discountCard;
    }

    @Override
    @SneakyThrows
    public DiscountCard findById(int id) {
        Connection connection = JdbcManager.get();
        return findById(connection, id);
    }

    @SneakyThrows
    private DiscountCard findById(Connection connection, int id) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, discount_percentage " +
                        "FROM discount_cards " +
                        "WHERE id = ?;"
        );

        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        DiscountCard discountCard = null;
        if (resultSet.next()) {
            discountCard = DiscountCard.builder()
                    .id(resultSet.getObject("id", Integer.class))
                    .discount_percentage(resultSet.getObject("discount_percentage", Integer.class))
                    .build();
            log.info("The entity was found in the database: {}", discountCard);
        }
        return discountCard;
    }

    @Override
    @SneakyThrows
    public boolean delete(int id) {
        Connection connection = JdbcManager.get();
        return delete(connection, id);
    }

    @SneakyThrows
    private boolean delete(Connection connection, int id) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM discount_cards " +
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
    public DiscountCard update(DiscountCard discountCard) {
        Connection connection = JdbcManager.get();
        return update(connection, discountCard);
    }

    @SneakyThrows
    private DiscountCard update(Connection connection, DiscountCard discountCardInput) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE discount_cards " +
                        "SET discount_percentage = ? " +
                        "WHERE id = ?" +
                        "RETURNING id, discount_percentage ;"
        );
        preparedStatement.setObject(1, discountCardInput.getDiscount_percentage());
        preparedStatement.setObject(2, discountCardInput.getId());


        ResultSet resultSet = preparedStatement.executeQuery();
        DiscountCard discountCardResult = null;

        if (resultSet.next()) {
            discountCardResult = DiscountCard.builder()
                    .id(discountCardInput.getId())
                    .discount_percentage(discountCardInput.getDiscount_percentage())
                    .build();
            log.info("The entity has been updated in the database: {}", discountCardResult);
        }
        return discountCardResult;
    }

}
