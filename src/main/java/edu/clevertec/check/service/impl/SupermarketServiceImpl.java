package edu.clevertec.check.service.impl;

import edu.clevertec.check.dto.DiscountCard;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.InvalidCardTypeException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;
import edu.clevertec.check.repository.impl.DiscountCardRepoImpl;
import edu.clevertec.check.service.ProductService;
import edu.clevertec.check.service.SupermarketService;
import edu.clevertec.check.util.FileOutput;
import edu.clevertec.check.util.FileUpLoad;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SupermarketServiceImpl implements SupermarketService {

    private final ProductService<Integer, Product> productService;

    //    public void setTotalCost(double totalCost) {
//        this.totalCost = totalCost;
//    }

    private String name = "Storage \"Dionis17\" ";
    private String phoneNumber ="+375(29)937-99-92";
    private Map<Product, Integer> orderMap;
    private DiscountCard card;
    private StringBuilder ReceiptInfoReceivedFromOrder;
    private double totalCost;

    public Map<Product, Integer> getOrderMap() {
        return orderMap;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getTotalCost() {
        return totalCost;
    }


    public SupermarketServiceImpl addOrder(String[] args) throws NoSuchProductException,
            InvalidCardNumberException, InvalidCardTypeException {

        orderMap = new HashMap<>();
        String[] splittedArgs;
        Product foundProduct;

        //добовляем в map продукты и их кол-во, key - product, value - кол-во
        for (String arg : args) {
            splittedArgs = arg.split("-");
            if (!arg.toLowerCase().contains("card")) {
                foundProduct = findProductInAssortmentById(Integer.parseInt(splittedArgs[0]));
                int amountOfProduct = Integer.parseInt(splittedArgs[1]);
                orderMap.put(foundProduct,
                        orderMap.containsKey(foundProduct) ? orderMap.get(foundProduct) + amountOfProduct
                                : amountOfProduct);
            } else {
                if (splittedArgs[0].equalsIgnoreCase("card")) {
                    if (Integer.parseInt(splittedArgs[1]) > 50) {
                        DiscountCardRepoImpl dc = new DiscountCardRepoImpl();
                        this.card = dc.findById(Integer.parseInt(splittedArgs[1]));
                        this.card.setDiscount(50);
                    } else {
                        DiscountCardRepoImpl dc = new DiscountCardRepoImpl();
                        this.card = dc.findById(Integer.parseInt(splittedArgs[1]));
                    }
                } else throw new InvalidCardTypeException();
            }
        }
        return this;
    }

    public SupermarketServiceImpl addOrder(String a) throws NoSuchProductException,
            InvalidCardNumberException, InvalidCardTypeException {

        String[] args = a.split(" ");

        orderMap = new HashMap<>();
        String[] splittedArgs;
        Product foundProduct;

        //добовляем в map продукты и их кол-во, key - product, value - кол-во
        for (String arg : args) {
            splittedArgs = arg.split("-");
            if (!arg.toLowerCase().contains("card")) {
                foundProduct = findProductInAssortmentById(Integer.parseInt(splittedArgs[0]));
                int amountOfProduct = Integer.parseInt(splittedArgs[1]);
                orderMap.put(foundProduct,
                        orderMap.containsKey(foundProduct) ? orderMap.get(foundProduct) + amountOfProduct
                                : amountOfProduct);
            } else {
                if (splittedArgs[0].equalsIgnoreCase("card")) {
                    if (Integer.parseInt(splittedArgs[1]) > 50) {
                        DiscountCardRepoImpl dc = new DiscountCardRepoImpl();
                        this.card = dc.findById(Integer.parseInt(splittedArgs[1]));
                        this.card.setDiscount(50);
                    } else {
                        DiscountCardRepoImpl dc = new DiscountCardRepoImpl();
                        this.card = dc.findById(Integer.parseInt(splittedArgs[1]));
                    }
                } else throw new InvalidCardTypeException();
            }
        }
        return this;
    }

    public SupermarketServiceImpl addOrderFromFile(File file) throws NoSuchProductException, InvalidCardNumberException,
            InvalidCardTypeException {
        this.addOrder(FileUpLoad.loadStringsFromFile(file));
        return this;
    }

    //обработка заказа (подсчет цены, учитывание скидок и формирование информации для чека)
    public SupermarketServiceImpl processOrder() throws OrderAreNotCreatedException {
        if (orderMap == null)
            throw new OrderAreNotCreatedException();

        ReceiptInfoReceivedFromOrder = new StringBuilder();
        double totalCostOfPromotionalProduct;
        Product product;
        int amount;

        for (Map.Entry<Product, Integer> entry : orderMap.entrySet()) {

            // Округление конечной стоимости товара
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            product = entry.getKey();
            amount = entry.getValue();
            /*--------------------------------------------------------------------------------------------------------------------*/
            //Если продукт не акционный
            if (!product.getPromotional()) {
                ReceiptInfoReceivedFromOrder.append(String.format("%-5s %-17s %-10s %-10s\n",
                        amount, product.getName(), "$" + product.getCost(), "$" + decimalFormat.format(product.getCost() * amount)));
            }
            /*--------------------------------------------------------------------------------------------------------------------*/
            // Если продукт акционный, и его количество меньше 5
            if (product.getPromotional() && amount < 5) {
                ReceiptInfoReceivedFromOrder.append(String.format("%-5s %-17s %-10s %-10s\n",
                        amount, product.getName(), "$" + product.getCost(), "$" + decimalFormat.format(product.getCost() * amount)));
            }
            /*--------------------------------------------------------------------------------------------------------------------*/
            //Если продукт  акционный и его количество 5 и выше
            if (product.getPromotional() && amount >= 5) {

                //Скидочный коэффициент на цену товара
                double discountСoefficient = 0.1d;
                double cost = product.getCost() - product.getCost() * discountСoefficient;
                String result_Cost = decimalFormat.format(cost);

                ReceiptInfoReceivedFromOrder.append(String.format(
                        "%-5s %-23s\n", "*", "The item " +
                                product.getName() + " is promotional"
                )).append(String.format("%-5s %-23s", "*", "Its amount is more than 5\n"));

                totalCostOfPromotionalProduct = product.getCost() * amount;
                totalCostOfPromotionalProduct -= totalCostOfPromotionalProduct / 100 * 10;


                String result = decimalFormat.format(totalCostOfPromotionalProduct);
                ReceiptInfoReceivedFromOrder.append(String.format(
                        "%-5s %-23s \n", "*", "You get a 10% discount "));

                totalCost += totalCostOfPromotionalProduct;
                ReceiptInfoReceivedFromOrder.append(String.format(
                        "%-5s %-23s \n", "*", "The cost " + product.getName() + " will be:" + "$" + result_Cost
                ));
                ReceiptInfoReceivedFromOrder.append(String.format("%-5s %-23s\n", "*",
                        "Instead of " + "$" + product.getCost()));
                ReceiptInfoReceivedFromOrder.append(String.format("%-5s %-17s %-10s %-10s\n",
                        amount, product.getName(), "$" + result_Cost, "$" + result));

            } else {
                totalCost += product.getCost() * amount;

            }
        }
        /*----------------------------------------------------------------------------------------------------------------------*/
        if (card != null) {
            ReceiptInfoReceivedFromOrder.append("----------------------------------------\n");
            ReceiptInfoReceivedFromOrder.append("#\t  ").append("Discount card")
                    .append("\n#\t  has been provided\n")
                    .append("#\t  Included " + card.getDiscount() + "% discount\n");

            totalCost -= totalCost / 100 * card.getDiscount();


        }
        ReceiptInfoReceivedFromOrder.append("----------------------------------------\n");
        ReceiptInfoReceivedFromOrder.append(String.format("%-5s %-27s %1s%.2f", "####", "Total cost:", "$", totalCost));
        return this;
    }

    private Product findProductInAssortmentById(int id) throws NoSuchProductException {
        return Optional.of(productService.findById(id)
                .orElseThrow(() -> new NoSuchProductException()))
                .get();
    }
//
//    private Product findProductInAssortmentById(int id) throws NoSuchProductException {
//
//        return Optional.of(productService.findAll()
//                .stream()
//                .filter(e -> e.getId() == id)
//                .findAny()
//                .orElseThrow(() -> new NoSuchProductException()))
//                .get();
//
//    }

    public void printCheckToConsole() {
        FileOutput.printReceiptToConsole(this, ReceiptInfoReceivedFromOrder);
    }

    public void printCheckToFile(File file) {
        FileOutput.printReceiptToFile(file, this, ReceiptInfoReceivedFromOrder);
    }

    public void printCheckToPdf() {
        FileOutput.printReceiptToPdf(this, ReceiptInfoReceivedFromOrder);
    }

    @Override
    public String toString() {
        return "SupermarketServiceImpl{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupermarketServiceImpl that = (SupermarketServiceImpl) o;
        return
                Objects.equals(name, that.name) &&
                        Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, orderMap, card, ReceiptInfoReceivedFromOrder, totalCost);
    }
}
