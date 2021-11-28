package edu.clevertec.check.dto;

public class Sweets extends Product {

    public Sweets(int id, String name, double cost, boolean isPromotional) {
        super(id, name, cost, isPromotional);
    }

    @Override
    public String toString() {
        return "Sweets id = " + getId() +
                " name = " + getName() +
                " cost = " + getCost();
    }
}
