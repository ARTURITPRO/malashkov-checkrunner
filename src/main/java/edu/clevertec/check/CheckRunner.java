package edu.clevertec.check;

import edu.clevertec.check.dto.Product;
import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.InvalidCardTypeException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;
import edu.clevertec.check.pdf.CashReceiptPdfFilePrinter;
import edu.clevertec.check.pdf.CashReceiptPrinter;
import edu.clevertec.check.repository.ProductRepo;
import edu.clevertec.check.repository.impl.ProductRepoImpl;
import edu.clevertec.check.service.ProductService;
import edu.clevertec.check.service.SupermarketService;
import edu.clevertec.check.service.impl.ProductServiceImpl;
import edu.clevertec.check.service.impl.SupermarketServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class CheckRunner {

    public static void main(String[] args) {

        //магазин берет в конструктор ассортимент продуктов
        ProductRepo<Integer, Product> productRepo = new ProductRepoImpl();
        ProductService<Integer, Product> productService = new ProductServiceImpl(productRepo);
        SupermarketService dionis17 = new SupermarketServiceImpl(productService);
        File file = new File(args[0].trim());
        try {
            if (file.isFile())
                dionis17.addOrderFromFile(file)
                        .processOrder()
                        .printCheckToFile(new File("src/main/resources/receipt.txt"));
            else
                dionis17.addOrder(args)
                        .processOrder()
                        .printCheckToConsole();
            CashReceiptPrinter pdfFilePrinter = new CashReceiptPdfFilePrinter();
            pdfFilePrinter.print(dionis17);
        } catch (NoSuchProductException | InvalidCardTypeException | InvalidCardNumberException |
                OrderAreNotCreatedException | IOException e) {
            log.error("error", e);
        }



    }
}
