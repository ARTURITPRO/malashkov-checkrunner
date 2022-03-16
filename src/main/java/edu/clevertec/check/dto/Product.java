package edu.clevertec.check.dto;

import java.util.Objects;
public class Product implements Entity, Comparable<Product> {

    private  int id;
    private final String name;
    private Double cost;
    private boolean promotional;

    public Product(int id, String name, Double cost, boolean promotional) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.promotional = promotional;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId (int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Double getCost() {
        return cost;
    }

    public boolean isPromotional() {
        return promotional;
    }

    public void setCost(Double cost ) {
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

    @Override
    public String toString() {
        return "{id_product = "+getId() + "; name = "+getName() + "; cost = " + getCost()   + "; promotional = "+ isPromotional()+" }";
    }
}
