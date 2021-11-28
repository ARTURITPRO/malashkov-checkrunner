package edu.clevertec.check.service.impl;

import edu.clevertec.check.dto.Fish;
import edu.clevertec.check.dto.Meat;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.dto.Sweets;
import edu.clevertec.check.exception.GroceryException;
import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;
import edu.clevertec.check.service.SupermarketService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SupermarketServiceImplTest {

    private SupermarketService dionis17;

    @Rule
    public ExpectedException thrownEx = ExpectedException.none();

    @Before
    public void setUp() {
        Set<Product> productTreeSet = new TreeSet<>();
        productTreeSet.add(new Fish(1, "Fish Sturgeon", 1.80d, false));
        productTreeSet.add(new Meat(2, "Meat", 5.01d, false));
        productTreeSet.add(new Sweets(3, "KitKat", 2.80d, true));
        productTreeSet.add(new Fish(4, "Fish Perch", 1.30d, false));




        dionis17 = new SupermarketServiceImpl("Storage \"Dionis17\" ",
                "+375(29)937-99-92", productTreeSet);
    }

    @Test
    public void whenPassedInvalidIdOfProduct() throws Exception {
        thrownEx.expect(NoSuchProductException.class);
        dionis17.addOrder(new String[]{"9-1"});
    }

    @Test
    public void whenPassedCardNumberWithInvalidLength() throws Exception {
        thrownEx.expect(InvalidCardNumberException.class);
        dionis17.addOrder(new String[]{"2-1", "3-3", "maestrocard-623587"});
    }

    @Test
    public void whenOrderProcessingWithoutCreation() throws Exception {
        thrownEx.expect(OrderAreNotCreatedException.class);
        dionis17.processOrder();
    }

    @Test
    public void assertionOrdersMapCost() throws GroceryException {
        dionis17.addOrder(new String[]{"1-2", "2-3", "3-5", "4-1", "maestrocard-623587456214"})
                .processOrder();

        Map<Product, Integer> orderMap = new HashMap<>();
        orderMap.put(new Fish(1, "Fish Sturgeon", 1.80, false),2);
        orderMap.put(new Meat(2, "Meat", 5.01d, false),3);
        orderMap.put(new Sweets(3, "KitKat", 2.80d, true),5);
        orderMap.put(new Fish(4, "Fish Perch", 1.30d, false),1);

        Assert.assertEquals(orderMap, dionis17.getOrderMap());
    }

    @Test
    public void assertionNameOfGroceryStore() {
        Assert.assertEquals(dionis17.getName(), "Storage \"Dionis17\" ");
    }

    @Test
    public void assertionNumberOfGroceryStore() {
        Assert.assertEquals(dionis17.getPhoneNumber(), "+375(29)937-99-92");
    }

    @Test
    public void assertionTotalCost() throws GroceryException {
        dionis17.addOrder(new String[]{"1-2", "2-3", "3-5", "4-1", "maestrocard-623587456214"})
                .processOrder();
        Assert.assertEquals("31,55", String.format("%.2f", dionis17.getTotalCost()));
    }

}
