package edu.clevertec.check.dto;

import edu.clevertec.check.exception.InvalidCardNumberException;

public enum DiscountCard {

    MAESTROCARD(3), MASTERCARD(6);

    int discount;
    String number;

    DiscountCard(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws InvalidCardNumberException {
        if (!number.matches("\\d{6}")) throw new InvalidCardNumberException();
        this.number = number;
    }


    @Override
    public String toString() {
        return this.name() + " " + getNumber() + " ";
    }
}
