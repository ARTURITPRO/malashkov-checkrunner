package edu.clevertec.check.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.service.ProductService;
import edu.clevertec.check.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService<Integer, Product> productService;
    private final ObjectMapper objectMapper;
    private Gson gsonMapper = new Gson();
    private static final String PAGE_SIZE = "20";
    private static final String PAGE_NUMBER = "1";
    private final ApplicationContext applicationContext;

    @PostConstruct
    public void main() {
        for (int i = 0; i < applicationContext.getBeanDefinitionNames().length; i++) {
            System.out.println("!!! " + applicationContext.getBeanDefinitionNames()[i]);
        }
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    protected Product findById(@PathVariable(value = "id") int id) {
        return productService.findById(id).get();
    }

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    protected Collection<Product> findAll(@RequestParam(value = "pagesize", defaultValue = PAGE_SIZE) Integer pageSize,
                                          @RequestParam(value = "pagenubber", defaultValue = PAGE_NUMBER) Integer pageNumber) {
        return productService.findAll(pageSize);
    }

    @SneakyThrows
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    protected Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable(value = "id") int id) {
        return productService.delete(id); // {"is_deleted": true}
    }

    @SneakyThrows
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    protected Optional<Product> update(@RequestBody Product product) {
        return productService.update(product);
    }
}
