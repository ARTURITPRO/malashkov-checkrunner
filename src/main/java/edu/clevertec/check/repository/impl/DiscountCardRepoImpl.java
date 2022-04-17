package edu.clevertec.check.repository.impl;

import edu.clevertec.check.dto.DiscountCard;
import edu.clevertec.check.repository.DiscountCardRepo;
import edu.clevertec.check.util.ConnectionManager;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DiscountCardRepoImpl implements DiscountCardRepo {

    @SneakyThrows
    @Override
    public Collection<DiscountCard> findAll() {
        Connection connection = ConnectionManager.get();
        Statement stmt = connection.createStatement();
        List<DiscountCard> discountCards = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery("select * from discountCard");
        while (resultSet.next()) {
            DiscountCard discountCard = new DiscountCard(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("discount", Integer.class),
                    resultSet.getObject("number", Integer.class)
            );
            log.info("The entity was found in the database: {}", discountCard);
            discountCards.add(discountCard);
        }
        return discountCards;
    }

    @SneakyThrows
    @Override
    public Collection<DiscountCard> findAll(Integer pageSize) {
        Connection connection = ConnectionManager.get();
        PreparedStatement stmt = connection.prepareStatement(
                "select * from discountCard ORDER BY id " + "LIMIT ?;" );

        stmt.setObject(1, pageSize);
        ResultSet resultSet = stmt.executeQuery();
        List<DiscountCard> discountCards = new ArrayList<>();
        while (resultSet.next()) {
            DiscountCard discountCard = new DiscountCard(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("discount", Integer.class),
                    resultSet.getObject("number", Integer.class)
            );
            log.info("The entity was found in the database: {}", discountCard);
            discountCards.add(discountCard);
        }
        return discountCards;
    }

    @SneakyThrows
    @Override
    public Collection<DiscountCard> findAll(Integer pageSize, Integer size) {
        Connection connection = ConnectionManager.get();
        PreparedStatement stmt = connection.prepareStatement(
                "select * from discountCard ORDER BY id " +"OFFSET ? "+"  LIMIT ?;" );
        final Integer CONST = 1;
        Integer i = pageSize * (size-CONST);
        stmt.setObject(1, i);
        stmt.setObject(2, pageSize);
        ResultSet resultSet = stmt.executeQuery();
        List<DiscountCard> discountCards = new ArrayList<>();
        while (resultSet.next()) {
            DiscountCard discountCard = new DiscountCard(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("discount", Integer.class),
                    resultSet.getObject("number", Integer.class)
            );
            log.info("The entity was found in the database: {}", discountCard);
            discountCards.add(discountCard);
        }
        return discountCards;
    }

    @Override
    @SneakyThrows
    public DiscountCard save(DiscountCard discountCard) {
        Connection connection = ConnectionManager.get();
        return save(connection, discountCard);
    }

    @SneakyThrows
    private DiscountCard save(Connection connection, DiscountCard discountCard) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO discountCard (discount, number) " +
                        "VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setObject(1, discountCard.getDiscount());
        preparedStatement.setObject(2, discountCard.getNumber());

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        discountCard.setId(generatedKeys.getObject("id", Integer.class));
        log.info("The discountCard is saved in the database: {}", discountCard);

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
                "SELECT id, discount, number " +
                        "FROM discountCard " +
                        "WHERE id = ?");

        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        DiscountCard discountCard = null;

        if (resultSet.next()) {
            discountCard = new DiscountCard(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("discount", Integer.class),
                    resultSet.getObject("number", Integer.class)
            );
            log.info("The entity was found in the database: {}", discountCard);
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
    public Optional<DiscountCard> update(DiscountCard discountCard) {
        Connection connection = ConnectionManager.get();
        return Optional.ofNullable(update(connection, discountCard));
    }

    @SneakyThrows
    private DiscountCard update(Connection connection, DiscountCard discountCard) {
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE discountCard " +
                        "SET discount = ?, number = ?" +
                        "WHERE id = ? " +
                        "RETURNING id, discount,  number;"
        );

        preparedStatement.setObject(1, discountCard.getDiscount());
        preparedStatement.setObject(2, discountCard.getNumber());
        preparedStatement.setObject(3, discountCard.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        DiscountCard discountCard1 = null;
        if (resultSet.next()) {
            discountCard1 = new DiscountCard(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("discount", Integer.class),
                    resultSet.getObject("number", Integer.class)
            );
            log.info("The entity has been updated in the database: {}", discountCard1);
        }
        return discountCard1;
    }
}
