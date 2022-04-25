package edu.clevertec.check.spring.service.mapper;

import edu.clevertec.check.dto.DiscountCard;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class DiscountCardRowMapper implements RowMapper<DiscountCard> {

    public DiscountCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DiscountCard.builder()
                .id(rs.getInt("id"))
                .discount(rs.getInt("discount"))
                .number(rs.getInt("number"))
                .build();
    }

}
