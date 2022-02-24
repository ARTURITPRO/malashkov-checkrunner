package edu.clevertec.check;

import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.InvalidCardTypeException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;
import edu.clevertec.check.service.impl.SupermarketServiceImpl;
import edu.clevertec.check.pdf.CashReceiptPdfFilePrinter;
import edu.clevertec.check.pdf.CashReceiptPrinter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class CheckRunner {

    public static void main(String[] args) {

        //магазин берет в конструктор ассортимент продуктов
        SupermarketServiceImpl dionis17 = new SupermarketServiceImpl("Storage \"Dionis17\" ",
                "+375(29)937-99-92");


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
            e.printStackTrace();
        }

    }
}
