package edu.clevertec.check.repository.impl;

import edu.clevertec.check.dto.DiscountCard;
import edu.clevertec.check.repository.DiscountCardRepo;
import edu.clevertec.check.spring.service.mapper.DiscountCardRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DiscountCardRepoImpl implements DiscountCardRepo {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final DiscountCardRowMapper discountCardRowMapper;

    @Override
    @SneakyThrows
    public DiscountCard findById(int id) {
        String sql = "SELECT id, discount, number " +
                "FROM discountcard " +
                "WHERE id = :id;";
        return namedParameterJdbcTemplate.
                queryForObject(sql, new MapSqlParameterSource("id", id), discountCardRowMapper);
    }

    @Override
    public List<DiscountCard> findAll( Integer pageSize) {
        String sql = "select * from discountCard ORDER BY id " + "LIMIT: pageSize;";

        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(DiscountCard.class));
    }

    @Override
    public List<DiscountCard> findAll( Integer pageSize, Integer size) {
        String sql = "select * from discountCard ORDER BY id " +"OFFSET: size "+ "limit: pageSize;";

        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(DiscountCard.class));
    }

    @Override
    public List<DiscountCard> findAll() {
        String sql = "SELECT id, discount, number " +
                "FROM discountcard;";

        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(DiscountCard.class));
    }

    @Override
    @SneakyThrows
    public DiscountCard save(DiscountCard discountCard) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate());
        simpleJdbcInsert.withTableName("discountcard").usingGeneratedKeyColumns("id");

        Map<String, Object> map = new HashMap<>();
        map.put("discount", discountCard.getDiscount());
        map.put("number", discountCard.getNumber());

        final Number number = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(map));

        if (number.intValue() == 0) {
            return null;
        } else {
            discountCard.setId(number.intValue());
            return discountCard;
        }
    }

    @Override
    @SneakyThrows
    public  Optional <DiscountCard> update(DiscountCard discountCard) {
        String sql = "UPDATE discountcard " +
                "SET discount = :discount, number =: number" +
                "WHERE id = :id;";

        Map<String, Object> map = new HashMap<>();
        map.put("id", discountCard.getId());
        map.put("discount", discountCard.getDiscount());
        map.put("number", discountCard.getNumber());
        final int update = namedParameterJdbcTemplate.update(sql, map);

        if (update == 0){
            return null;
        } else {
            return Optional.ofNullable( discountCard);
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(int id) {
        String sql = "DELETE FROM discount_cards " +
                "WHERE id = :id;";
        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id)) != 0;
    }
}
