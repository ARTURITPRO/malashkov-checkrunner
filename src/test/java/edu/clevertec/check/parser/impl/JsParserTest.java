package edu.clevertec.check.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.clevertec.check.dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsParserTest {
    private ObjectMapper objectMapper;
    private JsParser jsonParser;
    private Product product;


    @BeforeEach
    void setUP() {
        objectMapper = new ObjectMapper();
        jsonParser = new JsParser();
        product = new Product(1, "Fish", 4.8, true);
    }

    @Test
    void testParserProduct() {
        String objectMapperResult = "";
        try {
            objectMapperResult = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String jsonParserResult = jsonParser.parseToString(product);
        assertEquals(objectMapperResult, jsonParserResult);
        System.out.println(objectMapperResult);
        System.out.println(jsonParserResult);
    }


}