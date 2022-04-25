package edu.clevertec.check.spring.service;

import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.InvalidCardTypeException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;

import java.io.IOException;

public interface CheckPdfService {

    byte[] getReceiptInPDF(String a) throws InvalidCardNumberException, NoSuchProductException,
            InvalidCardTypeException, OrderAreNotCreatedException, IOException;

}
