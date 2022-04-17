package edu.clevertec.check.service;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.InvalidCardTypeException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;

import java.io.File;
import java.util.Map;

public interface SupermarketService {

    Map<Product, Integer> getOrderMap();

    String getName();

    String getPhoneNumber();

    double getTotalCost();

    SupermarketService addOrder(String[] args) throws NoSuchProductException, InvalidCardNumberException, InvalidCardTypeException;

    SupermarketService addOrder(String args) throws NoSuchProductException, InvalidCardNumberException, InvalidCardTypeException;

    SupermarketService addOrderFromFile(File file) throws NoSuchProductException, InvalidCardNumberException, InvalidCardTypeException;

    SupermarketService processOrder() throws OrderAreNotCreatedException;

    void printCheckToConsole();

    void printCheckToFile(File file);

}
