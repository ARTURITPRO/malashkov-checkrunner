package edu.clevertec.check.dto;
import lombok.Builder;

import java.util.Objects;
@Builder
public class Product implements Entity, Comparable<Product> {
    public int id;
    public String name;
    public double cost;
    public boolean promotional;

    public Product() {
    }

    public Product(int id, String name, double cost, boolean promotional) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.promotional = promotional;
    }
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean getPromotional() {
        return promotional;
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

    @Override
    public String toString() {
        return "{id = " + id + "; name = " + name + "; cost = " + cost + "; promotional = " + promotional + " }";
    }
}
