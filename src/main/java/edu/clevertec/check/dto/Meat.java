package edu.clevertec.check.dto;

public class Meat extends Product {

    public Meat(int id, String name, double cost, boolean isPromotional) {
        super(id, name, cost, isPromotional);
    }

    @Override
    public String toString() {
        return "Meat id = " + getId() +
                " name = " + getName() +
                " cost = " + getCost();
    }
}
