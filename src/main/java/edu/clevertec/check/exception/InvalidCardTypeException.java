package edu.clevertec.check.exception;

public class InvalidCardTypeException extends GroceryException {

    @Override
    public String getMessage() {
        return "Type of edu.clevertec.check.diskontcard should be maestrocard or mastercard";
    }
}
