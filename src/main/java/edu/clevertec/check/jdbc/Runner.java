package edu.clevertec.check.jdbc;
import java.math.BigDecimal;
import edu.clevertec.check.entity.DiscountCard;
import edu.clevertec.check.entity.Product;
import edu.clevertec.check.jdbc.repository.DiscountCardRepository;
import edu.clevertec.check.jdbc.repository.Impl.DiscountCardRepositoryImpl;
import edu.clevertec.check.jdbc.repository.Impl.ProductRepositoryImpl;
import edu.clevertec.check.repository.ProductRepository;



public class Runner {

    private static final ProductRepository productRepository = new ProductRepositoryImpl();
    private static final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();

    public static void main(String[] args) {

        Product product = Product.builder().name("Moshroom").price(BigDecimal.valueOf(12)).quantity(22).in_stock(true).build();

        productRepository.save(product);
//        productRepository.findById(28);
//        product.setQuantity(567);
//        product.setPrice(BigDecimal.valueOf(87));
//        productRepository.update(product);
//        productRepository.delete(product.getId());
//
//        DiscountCard discountCard = DiscountCard.builder()
//                .discount_percentage(15)
//                .build();
//        discountCardRepository.save(discountCard);
//        discountCardRepository.findById(5);
//        discountCard.setDiscount_percentage(99);
//        discountCardRepository.update(discountCard);
//        discountCardRepository.delete(discountCard.getId());
    }
}
