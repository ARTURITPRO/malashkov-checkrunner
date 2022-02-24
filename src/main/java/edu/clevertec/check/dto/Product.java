package edu.clevertec.check.dto;

import java.util.Objects;

public class Product implements Entity, Comparable<Product> {

    private final int id;
    private final String name;
    private double cost;
    private boolean promotional;

    public Product(int id, String name, double cost, boolean promotional) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.promotional = promotional;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public boolean isPromotional() {
        return promotional;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPromotional(boolean promotional) {
        this.promotional = promotional;
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
        return Objects.hash(id, name, cost, promotional);
    }
}
