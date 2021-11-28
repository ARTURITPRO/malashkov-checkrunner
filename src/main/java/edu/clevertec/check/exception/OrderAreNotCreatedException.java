package edu.clevertec.check.exception;

public class OrderAreNotCreatedException extends GroceryException {

    @Override
    public String getMessage() {
        return "Yor are trying process order which are not created yet, method addOrder() should be first";
    }
}
