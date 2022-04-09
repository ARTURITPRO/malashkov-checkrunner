package edu.clevertec.check.util;

import edu.clevertec.check.service.impl.SupermarketServiceImpl;


import java.io.IOException;

public interface CashReceiptPrinter {

    void print(SupermarketServiceImpl cashReceipt) throws IOException;

}
