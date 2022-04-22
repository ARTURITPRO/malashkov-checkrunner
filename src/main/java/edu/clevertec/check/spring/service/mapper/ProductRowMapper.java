package edu.clevertec.check.spring.service.mapper;

import edu.clevertec.check.dto.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ProductRowMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .cost(rs.getDouble("cost"))
                .promotional(rs.getBoolean("promotional"))
                .build();
    }

}