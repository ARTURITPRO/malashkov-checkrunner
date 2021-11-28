package edu.clevertec.check.dto;

import java.util.Objects;

public abstract class Product implements Comparable<Product> {

    private final int id;
    private final String name;
    private double cost;
    private boolean isPromotional;

    public Product(int id, String name, double cost, boolean isPromotional) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.isPromotional = isPromotional;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public boolean isPromotional() {
        return isPromotional;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPromotional(boolean promotional) {
        isPromotional = promotional;
    }

    public int compareTo(Product o) {
        return getId() > o.getId() ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, isPromotional);
    }
}
