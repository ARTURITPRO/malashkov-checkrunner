package edu.clevertec.check.dto;

public class Fish extends Product {

    public Fish(int id, String name, double cost, boolean isPromotional) {
        super(id, name, cost, isPromotional);
    }

    @Override
    public String toString() {
        return "Fish id = " + getId() +
                " name = " + getName() +
                " cost = " + getCost();
    }
}
