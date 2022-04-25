package edu.clevertec.check.repository.impl;

import edu.clevertec.check.dto.Product;
import edu.clevertec.check.repository.ProductRepo;
import edu.clevertec.check.spring.service.mapper.ProductRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ProductRepoImpl implements ProductRepo<Integer, Product> {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ProductRowMapper productRowMapper;

    @Override
    @SneakyThrows
    public Optional<Product> findById(Integer id) {
        String sql = "SELECT id, name, cost, promotional " +
                "FROM product " +
                "WHERE id = :id;";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id), productRowMapper));
    }

    @Override
    public Collection<Product> findAll(Integer pageSize) {
        String sql = "select id, name, cost, promotional from product " +
                "limit :pageSize;";
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource("pageSize", pageSize),
                BeanPropertyRowMapper.newInstance(Product.class));
    }

    @Override
    public Collection<Product> findAll(Integer pageSize, Integer size) {
        String sql = "select id, name, cost, promotional from product " + "OFFSET: size " +
                "limit :pageSize;";
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource("pageSize", pageSize),
                BeanPropertyRowMapper.newInstance(Product.class));
    }

    @Override
    @SneakyThrows
    public Product save(Product product) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate());
        simpleJdbcInsert.withTableName("product").usingGeneratedKeyColumns("id");

        Map<String, Object> map = new HashMap<>();
        map.put("name", product.getName());
        map.put("cost", product.getCost());
        map.put("promotional", product.getPromotional());

        final Number number = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(map));

        if (number.intValue() == 0) {
            return null;
        } else {
            product.setId(number.intValue());
            return product;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Product> update(Product product) {
        String sql = "UPDATE product " +
                "SET name = :name, cost = :cost, promitional = :promitional  " +
                "WHERE id = :id ;";

        Map<String, Object> map = new HashMap<>();
        map.put("id", product.getId());
        map.put("name", product.getName());
        map.put("cost", product.getCost());
        map.put("promotional", product.getPromotional());

        final int update = namedParameterJdbcTemplate.update(sql, map);
        if (update == 0) {
            return null;
        } else {
            return Optional.ofNullable(product);
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(Integer id) {
        String sql = "DELETE FROM product " +
                "WHERE id = :id ;";
        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id)) != 0;
    }
}
