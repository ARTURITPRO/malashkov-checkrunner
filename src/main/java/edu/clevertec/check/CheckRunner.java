package edu.clevertec.check;

import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.InvalidCardTypeException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;
import edu.clevertec.check.service.SupermarketService;
import edu.clevertec.check.service.impl.SupermarketServiceImpl;
import edu.clevertec.check.dto.Fish;
import edu.clevertec.check.dto.Meat;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.dto.Sweets;

import java.io.File;
import java.util.*;

public class CheckRunner {
    public static void main(String[] args) {
        //создаю  продукты
        Set<Product> productTreeSet = new TreeSet<>();
        productTreeSet.add(new Meat(2, "Meat", 5.01d, false));
        productTreeSet.add(new Fish(4, "Fish Perch", 1.30d, false));
        productTreeSet.add(new Fish(1, "Fish Sturgeon", 1.80d, false));
        productTreeSet.add(new Sweets(3, "KitKat", 2.80d, true));
        productTreeSet.add(new Sweets(5, "Snickers", 3.50d, true));
        productTreeSet.add(new Sweets(6, "Bounty", 2.30d, true));
        productTreeSet.add(new Sweets(7, "Tic-tac", 1.70d, true));
        productTreeSet.add(new Sweets(8, "NUTS", 1.40d, true));
        //продукты отсортированы по id

        //магазин берет в конструктор ассортимент продуктов
        SupermarketService dionis17 = new SupermarketServiceImpl("Storage \"Dionis17\" ",
                "+375(29)937-99-92", productTreeSet);


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

        } catch (NoSuchProductException | InvalidCardTypeException
                | InvalidCardNumberException | OrderAreNotCreatedException e) {
            e.printStackTrace();
        }

    }
}
