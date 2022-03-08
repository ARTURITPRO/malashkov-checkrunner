package edu.clevertec.check.collection.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedList;

class SimpleLinkedTest {
    SimpleLinked<String> myLink;
    LinkedList<String> realLink;

    @BeforeEach
    void setUp() {

        myLink = new SimpleLinked<>();
        myLink.add("111");
        myLink.add("222");
        myLink.add("333");
        myLink.add("444");

        realLink = new LinkedList<>();
        realLink.add("111");
        realLink.add("222");
        realLink.add("333");
        realLink.add("444");
    }

    @Test
    void addLast() {
        String s = "555";
        myLink.addLast(s);
        realLink.addLast(s);
        if (myLink.size() == realLink.size()) {
            Assertions.assertEquals(myLink.getLastElement(), realLink.getLast());
        }
    }

    @Test
    void addFirst() {
        String s = "000";
        myLink.addFirst(s);
        realLink.addFirst(s);
        if (myLink.size() == realLink.size())
            Assertions.assertEquals(myLink.getFirstElement(), realLink.getFirst());
    }

    @Test
    void getFirstElement() {
        Assertions.assertEquals(myLink.getFirstElement(), realLink.getFirst());
    }

    @Test
    void getLastElement() {
        Assertions.assertEquals(myLink.getLastElement(), realLink.getLast());
    }

    @Test
    void size() {
        Assertions.assertEquals(myLink.size(), realLink.size());
    }

    @Test
    void isEmpty() {
        Assertions.assertEquals(myLink.isEmpty(), realLink.isEmpty());
    }

    @Test
    void contains() {
        Assertions.assertEquals(myLink.contains("111"), realLink.contains("111"));
    }


    @Test
    void add() {
        Assertions.assertEquals(myLink.add("111"), realLink.add("111"));
        Assertions.assertEquals(myLink.getLastElement(), realLink.getLast());
    }

    @Test
    void remove() {
        Assertions.assertEquals(myLink.remove("111"), realLink.remove("111"));
    }

    @Test
    void get() {
        Assertions.assertEquals(myLink.get(2), realLink.get(2));
    }

    @Test
    void set() {
        Assertions.assertEquals(myLink.set(2, "777"), "777");
        Assertions.assertEquals(myLink.get(1), "777");
    }

    @Test
    void testRemove() {
        Assertions.assertEquals(myLink.remove(2), "222");
        Assertions.assertEquals(myLink.get(2), "444");
    }

    @Test
    void indexOf() {
        Assertions.assertEquals(myLink.indexOf("222"), 2);
    }

    @Test
    void testAdd() {
        myLink.add(4, "555");
        Assertions.assertEquals(myLink.get(4), "555");

    }
}