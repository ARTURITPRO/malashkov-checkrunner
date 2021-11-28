package edu.clevertec.check.exception;

public class NoSuchProductException extends GroceryException {
    @Override
    public String getMessage() {
        return "There is no product with provided ID";
    }
}
