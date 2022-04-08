package edu.clevertec.check.dto;

import edu.clevertec.check.exception.InvalidCardNumberException;

public class DiscountCard  {


    private int id;
    private int discount;
    private int number;

    DiscountCard() {
    }

    public DiscountCard(int id, int discount, int number) {
        this.id = id;
        this.discount = discount;
        this.number = number;

    }

    public  void setId(int id) {
        this.id = id;
    }
    public  int getId() {
        return id;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) throws InvalidCardNumberException {
        if ( number < 0 ) throw new InvalidCardNumberException();
        this.number = number;
    }

    @Override
    public String toString() {
        return "{id = " + id + "; discount = " + discount + "; number = " + number + " }";
    }
}
