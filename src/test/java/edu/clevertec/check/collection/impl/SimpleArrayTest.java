package edu.clevertec.check.collection.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

class SimpleArrayTest {
    List<String> myArray;
    List<String> realArray;

    @BeforeEach
    public void setUp() {
        myArray = new SimpleArray<>();
        for (int a = 0; a < 11; a++) {
            myArray.add(" " + a + " ");
        }
        realArray = new ArrayList<>();
        for (int a = 0; a < 11; a++) {
            realArray.add(" " + a + " ");
        }
    }

    @Test
    void size() {
        Assertions.assertEquals(myArray.size(), realArray.size());
    }

    @Test
    void add() {
        Assertions.assertEquals(myArray.add(" " + 12 + " "), realArray.add(" " + 12 + " "));
    }

    @Test
    void get() {
        Assertions.assertEquals(myArray.get(6), realArray.get(6));
    }

    @Test
    void set() {

        Assertions.assertEquals(myArray.set(6, "null"), realArray.set(6, "null"));

    }

    @Test
    void testAdd() {
        Assertions.assertEquals(myArray.set(5, "null"), realArray.set(5, "null"));
    }

}