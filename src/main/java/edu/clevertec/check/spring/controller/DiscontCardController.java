package edu.clevertec.check.spring.controller;

import edu.clevertec.check.dto.DiscountCard;
import edu.clevertec.check.repository.DiscountCardRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discountCards")
public class DiscontCardController {

    private final DiscountCardRepo cardRepo;
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
    protected DiscountCard findById(@PathVariable(value = "id") int id) {
        return cardRepo.findById(id);
    }

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    protected Collection<DiscountCard> findAll(@RequestParam(value = "pagesize", required = false, defaultValue = "20") Integer pageSize) {
        return cardRepo.findAll(pageSize);
    }

    @SneakyThrows
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    protected DiscountCard save(@RequestBody DiscountCard discountCard) {
        return cardRepo.save(discountCard);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    protected boolean delete(@PathVariable(value = "id") int id) {
        return cardRepo.delete(id);
    }

    @SneakyThrows
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    protected DiscountCard update(@RequestBody DiscountCard discountCard) {
        return cardRepo.update(discountCard).get();
    }
}
